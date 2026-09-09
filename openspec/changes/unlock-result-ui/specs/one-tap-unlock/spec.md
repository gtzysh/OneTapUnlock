## MODIFIED Requirements

### Requirement: 点击应用图标立即开门
在无障碍已开启且已安装贝壳找房的前提下，用户从桌面启动本应用时，系统 MUST 立即开始开门流程，不得再要求用户在本应用内点击「开门」按钮。若本次 Intent 用于展示开门结果，则 MUST 展示结果页且 MUST NOT 开始新的开门流程。

#### Scenario: 冷启动执行开门
- **WHEN** 用户从桌面点击本应用图标且权限已就绪且 Intent 不含结果 extras
- **THEN** 本应用 MUST 启动开门流程并拉起贝壳找房

#### Scenario: 再次点击图标再次开门
- **WHEN** 本应用已在后台，用户再次从桌面点击图标且 Intent 不含结果 extras
- **THEN** 本应用 MUST 重新开始开门流程

#### Scenario: 从最近任务返回不误开
- **WHEN** 用户从系统最近任务划回本应用（带 `FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY`）
- **THEN** 本应用 MUST NOT 自动开始新的开门流程
