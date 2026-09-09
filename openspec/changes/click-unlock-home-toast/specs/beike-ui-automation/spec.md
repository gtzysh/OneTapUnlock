## MODIFIED Requirements

### Requirement: 按官方路径点击开门
开门流程 MUST 依次尝试点击可配置文案。最后一步「蓝牙开门」MUST 优先对文字所在区域做手势点击，以便点中圆形按钮。

#### Scenario: 点中圆钮蓝牙开门
- **WHEN** 智能门锁页出现「蓝牙开门」
- **THEN** 服务 MUST 在该文案对应区域中心发出点击手势

### Requirement: 成功后回桌面提示
当已点到开门按钮且未读到失败文案时，系统 MUST 退出到系统桌面，并 Toast「开门成功」。

#### Scenario: 成功回桌面
- **WHEN** 开门按钮已点击且确认窗口内无失败关键词
- **THEN** 系统 MUST 执行回到桌面，并显示「开门成功」
