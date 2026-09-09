package com.chatrobot.opendoor

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityNodeInfo

// 【文字点击 + 底栏点格；手势必须丢到主线程。导航禁止按屏幕比例乱点】
object NodeClicker {
    private val mainHandler = Handler(Looper.getMainLooper())

    fun clickText(
        service: AccessibilityService,
        root: AccessibilityNodeInfo,
        target: String,
        preferLargest: Boolean = false
    ): Boolean {
        if (target.isBlank()) return false
        val hits = mutableListOf<AccessibilityNodeInfo>()
        collectContaining(root, target, hits)
        root.findAccessibilityNodeInfosByText(target)?.let { hits.addAll(it) }
        val screen = Rect()
        root.getBoundsInScreen(screen)
        val screenArea = (screen.width() * screen.height()).coerceAtLeast(1)
        val usable = hits.distinctBy { boundsKey(it) }
            .filter { area(it) in 64 until (screenArea * 0.45).toInt() }
        if (usable.isEmpty()) return false
        val ordered = if (preferLargest) {
            usable.sortedByDescending { area(it) }
        } else {
            usable.sortedBy { area(it) }
        }
        for (node in ordered) {
            val rect = Rect()
            node.getBoundsInScreen(rect)
            if (enqueueTap(service, rect.exactCenterX(), rect.exactCenterY())) return true
            if (clickNodeOrParent(node)) return true
        }
        return false
    }

    // 【点屏幕上半部的文案，用来点「智能门锁」卡片，避开底栏房东】
    fun clickTextAbove(
        service: AccessibilityService,
        root: AccessibilityNodeInfo,
        target: String,
        maxYRatio: Float = 0.62f,
        minXRatio: Float = 0f,
        preferLargest: Boolean = true
    ): Boolean {
        if (target.isBlank()) return false
        val dm = service.resources.displayMetrics
        val maxBottom = dm.heightPixels * maxYRatio
        val minCenterX = dm.widthPixels * minXRatio
        val hits = mutableListOf<AccessibilityNodeInfo>()
        collectContaining(root, target, hits)
        root.findAccessibilityNodeInfosByText(target)?.let { hits.addAll(it) }
        val usable = hits.distinctBy { boundsKey(it) }.filter { node ->
            val rect = Rect()
            node.getBoundsInScreen(rect)
            rect.bottom <= maxBottom &&
                rect.exactCenterX() >= minCenterX &&
                rect.width() > 8 &&
                rect.height() > 8
        }
        if (usable.isEmpty()) return false
        val ordered = if (preferLargest) {
            usable.sortedByDescending { area(it) }
        } else {
            usable.sortedBy { area(it) }
        }
        for (node in ordered) {
            val rect = Rect()
            node.getBoundsInScreen(rect)
            if (enqueueTap(service, rect.exactCenterX(), rect.exactCenterY())) return true
            if (clickNodeOrParent(node)) return true
        }
        return false
    }

    // 【只点底部一格 Tab，不点整条底栏、不点砸金蛋】
    fun clickBottomTab(
        service: AccessibilityService,
        root: AccessibilityNodeInfo,
        target: String
    ): Boolean {
        if (target.isBlank()) return false
        val dm = service.resources.displayMetrics
        val screenH = dm.heightPixels
        val screenW = dm.widthPixels
        val minTop = screenH * 0.82f
        val maxWidth = screenW * 0.38f
        val maxHeight = 120f * dm.density
        val hits = mutableListOf<AccessibilityNodeInfo>()
        collectExact(root, target, hits)
        root.findAccessibilityNodeInfosByText(target)?.forEach { node ->
            val text = normalizeScreen(node.text?.toString().orEmpty())
            val desc = normalizeScreen(node.contentDescription?.toString().orEmpty())
            if (text == target || desc == target) hits.add(node)
        }
        val usable = hits.distinctBy { boundsKey(it) }.filter { node ->
            val rect = Rect()
            node.getBoundsInScreen(rect)
            rect.top >= minTop &&
                rect.width() in 8..maxWidth.toInt() &&
                rect.height() in 8..maxHeight.toInt()
        }
        for (node in usable.sortedBy { area(it) }) {
            if (clickTabSizedParent(node, maxWidth, maxHeight)) return true
            val rect = Rect()
            node.getBoundsInScreen(rect)
            val y = (rect.exactCenterY() - 8f * dm.density).coerceAtLeast(rect.top.toFloat() + 2f)
            if (enqueueTap(service, rect.exactCenterX(), y)) return true
        }
        return false
    }

    fun tapPercent(service: AccessibilityService, xRatio: Float, yRatio: Float): Boolean {
        val dm = service.resources.displayMetrics
        val x = dm.widthPixels * xRatio
        val y = dm.heightPixels * yRatio
        return enqueueTap(service, x, y)
    }

    fun collectVisibleText(root: AccessibilityNodeInfo, limit: Int = 4000): String {
        val sb = StringBuilder()
        walkText(root, sb, 0, limit)
        return sb.toString()
    }

    fun normalizeScreen(raw: String): String {
        return raw.replace("\n", "").replace(" ", "").trim()
    }

    private fun collectExact(
        node: AccessibilityNodeInfo,
        target: String,
        out: MutableList<AccessibilityNodeInfo>
    ) {
        val text = normalizeScreen(node.text?.toString().orEmpty())
        val desc = normalizeScreen(node.contentDescription?.toString().orEmpty())
        if (text == target || desc == target) {
            out.add(node)
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            collectExact(child, target, out)
        }
    }

    private fun clickTabSizedParent(
        node: AccessibilityNodeInfo,
        maxWidth: Float,
        maxHeight: Float
    ): Boolean {
        var current: AccessibilityNodeInfo? = node
        var depth = 0
        while (current != null && depth < 8) {
            val rect = Rect()
            current.getBoundsInScreen(rect)
            val tabSized = rect.width() <= maxWidth && rect.height() <= maxHeight * 1.5f
            if (tabSized && current.isClickable &&
                current.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            ) {
                return true
            }
            current = current.parent
            depth++
        }
        return false
    }

    private fun enqueueTap(service: AccessibilityService, x: Float, y: Float): Boolean {
        val path = Path().apply {
            moveTo(x, y)
            lineTo(x + 4f, y + 2f)
        }
        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, 140))
            .build()
        mainHandler.post {
            service.dispatchGesture(gesture, null, null)
        }
        return true
    }

    private fun collectContaining(
        node: AccessibilityNodeInfo,
        target: String,
        out: MutableList<AccessibilityNodeInfo>
    ) {
        val text = normalizeScreen(node.text?.toString().orEmpty())
        val desc = normalizeScreen(node.contentDescription?.toString().orEmpty())
        if (text.contains(target) || desc.contains(target)) {
            out.add(node)
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            collectContaining(child, target, out)
        }
    }

    private fun walkText(node: AccessibilityNodeInfo, sb: StringBuilder, depth: Int, limit: Int) {
        if (sb.length >= limit || depth > 18) return
        normalizeScreen(node.text?.toString().orEmpty()).takeIf { it.isNotEmpty() }?.let { sb.append(it).append(' ') }
        normalizeScreen(node.contentDescription?.toString().orEmpty()).takeIf { it.isNotEmpty() }?.let {
            sb.append(it).append(' ')
        }
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            walkText(child, sb, depth + 1, limit)
        }
    }

    private fun clickNodeOrParent(node: AccessibilityNodeInfo): Boolean {
        var current: AccessibilityNodeInfo? = node
        var depth = 0
        while (current != null && depth < 8) {
            if (current.isClickable && current.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                return true
            }
            current = current.parent
            depth++
        }
        return false
    }

    private fun area(node: AccessibilityNodeInfo): Int {
        val rect = Rect()
        node.getBoundsInScreen(rect)
        return rect.width().coerceAtLeast(0) * rect.height().coerceAtLeast(0)
    }

    private fun boundsKey(node: AccessibilityNodeInfo): String {
        val rect = Rect()
        node.getBoundsInScreen(rect)
        return "${rect.left},${rect.top},${rect.right},${rect.bottom}"
    }
}
