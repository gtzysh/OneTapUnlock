## Why

点完蓝牙开门后，当前只会 Toast 一句，用户仍停在贝壳页面，不知道有没有成功。需要回到本应用展示明确结果，并把引导/结果/设置页做成统一的深色卡片界面。

## What Changes

- 点击「蓝牙开门」后等待贝壳页面反馈，再把本应用拉回前台。
- 结果分为：开门成功、已点击但未确认、失败，展示标题、说明和时间。
- 美化主界面、引导页、设置页（渐变背景、卡片、主/次按钮）。
- 版本升至 1.1.0。

## Capabilities

### New Capabilities

- `unlock-result`: 开门结束后返回本应用并展示成功/未确认/失败信息。

### Modified Capabilities

- `one-tap-unlock`: 桌面点图标仍立即开门；若本次 Intent 带结果 extras 则只展示结果、不再开一次门。
- `oppo-permission-guide`: 引导页使用新视觉，权限条目不变。

## Impact

- `MainActivity`、`UnlockCoordinator`、`UnlockAccessibilityService`、布局与 `docs/一键开门.md`。
- 需要「后台弹出界面」才能从贝壳回到本应用。
