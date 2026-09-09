## Why

点进租约页后，底栏也有「地图找房」，被误判成贝壳首页，于是继续点底栏第 4 格。租约是 5 个图标，第 4 格按 4 格计算会点到最右边的「房东」，而不是「智能门锁」卡片。

## What Changes

- 首页判定不再单独使用「地图找房」。
- 屏幕上有「智能门锁」时，点屏幕上半部的卡片，禁止再点底栏。
- 若已误入「房东」页，点回底栏「租约」。

## Capabilities

### Modified Capabilities
- `beike-ui-automation`: 租约页点智能门锁卡片，不得点房东。

## Impact

- UnlockCoordinator、NodeClicker、文档、APK。
