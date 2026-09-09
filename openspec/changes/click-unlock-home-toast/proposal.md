## Why

门锁页的「蓝牙开门」是大圆钮，文字节点往往不可点击，现有 ACTION_CLICK 点不中。用户希望点中圆钮并在开门成功后退出贝壳，回到手机桌面提示「开门成功」。

## What Changes

- 用无障碍手势点击文字所在区域的中心（圆钮），不再只依赖 isClickable。
- 匹配「蓝牙开门」改为包含匹配，避免换行/空格导致漏点。
- 开门成功（含已点到按钮且无失败提示）后：回到系统桌面，再弹出 Toast「开门成功」。失败则留在贝壳并提示失败。
- 无障碍配置开启 `canPerformGestures`。

## Capabilities

### New Capabilities
- （无新 capability 名，复用既有）

### Modified Capabilities
- `beike-ui-automation`: 最后一步用手势点击圆钮。
- `unlock-result`: 成功后回桌面 Toast，不再拉起本应用结果页。

## Impact

- `NodeClicker`、`UnlockCoordinator`、`UnlockAccessibilityService`、无障碍 XML、文档、APK。
