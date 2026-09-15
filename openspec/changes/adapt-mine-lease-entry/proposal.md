## Why

贝壳「我的」页再次改版：底栏变成「首页 / 我家 / AI / 消息 / 我的」，「租约」被挤到横向图标区（专属服务、租约、我的委托…），位置更靠下。现有逻辑只点屏幕高度 75% 以上的「租约」，且点过「我的」后若读不到该文案就空等超时。

## What Changes

- 用「我的」页特征文案认出新布局（如「专属服务」+「我的委托」，或「购房计划书」）。
- 在底栏上方点击「租约」；无障碍点不中时，仅在已认出「我的」页时按第二格大约位置补点一次。
- 点过「我的」后若已在「我的」页，仍继续点「租约」，不得空等。
- 门锁圆钮、底栏「我的」点击方式不变；禁止再按 4 格底栏扫射。
- 版本升至 1.5.1。

## Capabilities

### New Capabilities

- `beike-ui-automation`: 按贝壳当前页文案推进「我的 → 租约 → 智能门锁 → 蓝牙开门」；本变更补上 2026-09 我的页改版后的租约入口。

### Modified Capabilities

- （无。主规格目录尚无 `beike-ui-automation`。）

## Impact

- `app/src/main/java/com/chatrobot/opendoor/UnlockCoordinator.kt`
- 新增可单测的页面判断（无 Android 依赖）
- `app/src/main/java/com/chatrobot/opendoor/NodeClicker.kt`（放宽「租约」点击的 Y 范围）
- `docs/一键开门.md`、`README.md`、版本号
