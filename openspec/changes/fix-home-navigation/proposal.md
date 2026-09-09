## Why

自动化停在贝壳首页。底部是「首页 / 我家 / 消息 / 我的」。旧逻辑一点「我的」就认为成功，实际没进「租约」页，后续步骤在首页找不到按钮就停住。

## What Changes

- 认出首页后反复点底部「我的」（点不中再点「我家」）。
- 必须看到「租约」或「智能门锁」才进入下一步。
- 看到「智能门锁」再点进去，看到门锁页再点圆钮。

## Capabilities

### Modified Capabilities
- `beike-ui-automation`: 按页面是否出现目标文案推进，不提前跳步。

## Impact

- UnlockCoordinator、NodeClicker、文档、APK。
