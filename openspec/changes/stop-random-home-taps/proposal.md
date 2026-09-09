## Why

1.4.0 在首页找不到「我的」节点后，按屏幕 93%/96% 反复手势点击。该位置是「砸金蛋」浮窗和系统手势条，不是底栏「我的」，造成手机乱点且仍停在首页。

## What Changes

- 导航阶段禁止按屏幕比例乱点。
- 只点底部一格大小的「我的」节点；点不中就等待，最多用一次按导航栏 inset 计算的底栏第 4 格点击。
- 当前窗口不是贝壳时不点击。

## Capabilities

### Modified Capabilities
- `beike-ui-automation`: 首页只点底栏「我的」，不得扫射坐标。

## Impact

- `app/src/main/java/com/chatrobot/opendoor/NodeClicker.kt`
- `app/src/main/java/com/chatrobot/opendoor/UnlockCoordinator.kt`
- `docs/一键开门.md`
