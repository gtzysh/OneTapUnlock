## Context

截图显示「蓝牙开门」在大圆钮内部。用户反馈没有点到该按钮。

## Goals / Non-Goals

**Goals:** 点中圆钮；成功后 HOME 回桌面并 Toast「开门成功」。

**Non-Goals:** 不 force-stop 贝壳；失败时不强制回桌面。

## Decisions

1. 对含目标文案的节点取屏幕区域中心，`dispatchGesture` 点击；ACTION_CLICK 作兜底。
2. 成功/已点击无失败文案 → `GLOBAL_ACTION_HOME` + Toast。
3. 失败只 Toast，留在贝壳方便重试。

## Risks / Trade-offs

- [手势被 ColorOS 拦] → 配置 canPerformGestures；引导里已要求无障碍。
- [贝壳成功 Toast 读不到无障碍树] → 已点到圆钮且无失败文案即视为成功。
