## Context

用户手动点击后出现「开门中...」；自动点击仍未点中圆钮。

## Goals / Non-Goals

**Goals:** 进门锁页后按位置点圆钮；看到「开门中」再等结束，回桌面提示开门成功。

**Non-Goals:** 不解析贝壳 H5 内部 JS。

## Decisions

1. 去掉 XML `packageNames`，开门过程中任意窗口事件都扫描当前界面。
2. `dispatchGesture` 必须 `Handler.post`，避免在 onAccessibilityEvent 里被系统丢掉。
3. 门锁页（出现「临时密码」/「蓝牙开门」/「开门中」）只点圆钮坐标，不点标题。
4. 圆钮约在屏幕水平居中、高度 33%～44%；多点尝试直到出现「开门中」。
5. 「开门中」消失且无失败文案 → 成功。

## Risks / Trade-offs

- [不同机型圆钮位置略偏] → 多个 Y 比例重试。
- [开门中读不到] → 多点几次后仍失败并 Toast。
