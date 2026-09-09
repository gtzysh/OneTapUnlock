## ADDED Requirements

### Requirement: 开门结束后返回结果页
无障碍服务在开门流程结束后 MUST 将本应用带到前台，并展示结果标题与说明，不得只依赖一闪而过的 Toast。

#### Scenario: 读到成功文案
- **WHEN** 已点击开门按钮且贝壳界面出现成功关键词（如「开锁成功」）
- **THEN** 本应用 MUST 显示成功结果，并包含时间

#### Scenario: 已点击但未读到结果
- **WHEN** 已点击开门按钮且确认时间内未出现成功或失败关键词
- **THEN** 本应用 MUST 显示「已发出开门指令」及请查看门锁的说明

#### Scenario: 失败
- **WHEN** 流程超时，或贝壳界面出现失败关键词
- **THEN** 本应用 MUST 显示失败原因

### Requirement: 结果页不重复开门
携带结果 extras 的启动 MUST NOT 再次触发自动点击。

#### Scenario: 展示结果时不连点
- **WHEN** MainActivity 因结果 Intent 回到前台
- **THEN** 系统 MUST 只展示结果，MUST NOT 立刻再开一次门
