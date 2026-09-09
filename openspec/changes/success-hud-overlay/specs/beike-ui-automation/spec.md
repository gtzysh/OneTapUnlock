## MODIFIED Requirements

### Requirement: 成功后回桌面并提示
当开门流程判定成功时，系统 MUST 退出贝壳回到桌面，并 MUST 以类似「开门中」的黑底圆角条显示「开门成功」。

#### Scenario: 桌面看到开门成功
- **WHEN** 贝壳已出现开门中且流程结束且无失败文案
- **THEN** 系统 MUST 回到桌面，并弹出「开门成功」浮层
