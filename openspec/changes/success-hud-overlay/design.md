## Context

贝壳门锁页的「开门中...」是官方黑底圆角浮层。成功后用户要回到桌面，并用同款样式提示「开门成功」。

## Goals / Non-Goals

**Goals:** Home 回桌面；桌面显示同款「开门成功」浮层。

**Non-Goals:** 不改开门点击路径。

## Decisions

1. 成功判定后 `GLOBAL_ACTION_HOME`，再启动透明 Activity 画黑底条。
2. 浮层不抢桌面点击（不可触摸），约 1.8 秒结束。
3. OPPO 需已允许「后台弹出界面」，否则浮层可能被拦截。
