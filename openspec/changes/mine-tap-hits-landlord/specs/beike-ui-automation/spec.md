## MODIFIED Requirements

### Requirement: 见到房东后不再点我的
当底栏出现「房东」时，系统 MUST NOT 再点击「我的」或屏幕右下 4 格坐标。系统 MUST 点击「智能门锁」或从房东页点回「租约」。

#### Scenario: 租约五图标
- **WHEN** 底栏同时可见「租约」和「房东」
- **THEN** 系统 MUST 把当前页当作租约流程，MUST NOT 再点「我的」
