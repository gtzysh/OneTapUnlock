## MODIFIED Requirements

### Requirement: 门锁页必须点中圆钮
当界面已是智能门锁页时，系统 MUST 按屏幕位置点击「蓝牙开门」圆钮，MUST NOT 把标题「智能门锁」当成开门按钮。

#### Scenario: 坐标点击直到开门中
- **WHEN** 页面出现「临时密码」或「蓝牙开门」
- **THEN** 服务 MUST 向圆钮区域发出手势点击，直到出现「开门中」或超时失败

### Requirement: 开门中之后才报成功
系统 MUST 在看到「开门中」之后等待流程结束，再回到桌面并 Toast「开门成功」。

#### Scenario: 加载结束回桌面
- **WHEN** 已出现「开门中」且随后该文案消失、且无失败关键词
- **THEN** 系统 MUST 回到桌面并提示开门成功
