## Why

成功后虽然会按 Home，但系统 Toast 在 OPPO 后台经常不显示。用户希望回到桌面后，用和贝壳「开门中」一样的黑底圆角条提示「开门成功」。

## What Changes

- 开门成功：退出贝壳回到桌面。
- 桌面正中弹出黑底白字「开门成功」条，约 1.8 秒后消失。
- 不再依赖系统 Toast 作为成功提示。

## Capabilities

### Modified Capabilities
- `beike-ui-automation`: 成功后回桌面并显示开门成功浮层。

## Impact

- SuccessHudActivity、无障碍服务、清单、文档、APK。
