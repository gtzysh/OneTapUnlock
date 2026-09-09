## MODIFIED Requirements

### Requirement: 从首页进入门锁
当当前是贝壳首页时，系统 MUST 点击底部「我的」（必要时「我家」），MUST NOT 在未出现「租约」或「智能门锁」时进入开门点击。

#### Scenario: 首页继续往下
- **WHEN** 界面为贝壳首页（可见「地图找房」或「二手房」）
- **THEN** 服务 MUST 点击底部「我的」，直到出现「租约」或「智能门锁」
