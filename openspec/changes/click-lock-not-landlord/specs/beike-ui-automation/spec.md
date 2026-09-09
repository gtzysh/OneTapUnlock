## MODIFIED Requirements

### Requirement: 租约页点智能门锁
当界面可见「智能门锁」且尚未进入门锁页时，系统 MUST 点击上半屏的智能门锁卡片，MUST NOT 点击底栏「房东」。

#### Scenario: 租约页继续开门
- **WHEN** 当前为租约页（可见「智能门锁」或「切换租约」）
- **THEN** 系统 MUST 点击「智能门锁」卡片，MUST NOT 按 4 格底栏坐标点击
