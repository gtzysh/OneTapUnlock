## Why

主站底栏「我的」和租约底栏「房东」都在屏幕右下角。点完我的/租约后若仍按 4 格坐标点右下，或树里还留着首页文案，就会点进房东页。

## What Changes

- 见到「房东」后永远不再点「我的」，也不再按 4 格点右下。
- 删除 `tapBottomTabSlot`。
- 进入租约底栏后只点「智能门锁」卡片。

## Capabilities

### Modified Capabilities
- `beike-ui-automation`: 租约底栏出现「房东」后禁止再点我的。

## Impact

- UnlockCoordinator、文档、APK。
