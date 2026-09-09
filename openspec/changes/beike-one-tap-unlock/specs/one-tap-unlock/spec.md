## ADDED Requirements

### Requirement: 点击应用图标立即开门
在无障碍已开启且已安装贝壳找房的前提下，用户从桌面启动本应用时，系统 MUST 立即开始开门流程，不得再要求用户在本应用内点击「开门」按钮。

#### Scenario: 冷启动执行开门
- **WHEN** 用户从桌面点击本应用图标且权限已就绪
- **THEN** 本应用 MUST 启动开门流程并拉起贝壳找房

#### Scenario: 再次点击图标再次开门
- **WHEN** 本应用已在后台，用户再次从桌面点击图标
- **THEN** 本应用 MUST 重新开始开门流程

#### Scenario: 从最近任务返回不误开
- **WHEN** 用户从系统最近任务划回本应用（带 `FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY`）
- **THEN** 本应用 MUST NOT 自动开始新的开门流程

### Requirement: 权限不足时进入引导
无障碍未开启或未安装贝壳找房时，系统 MUST 显示引导或错误说明，且 MUST NOT 假装开门成功。

#### Scenario: 未开无障碍
- **WHEN** 用户点击图标但无障碍服务未开启
- **THEN** 系统 MUST 显示引导页并提供跳转系统无障碍设置的入口

#### Scenario: 未安装贝壳
- **WHEN** 用户点击图标但设备未安装 `com.lianjia.beike`
- **THEN** 系统 MUST 提示安装贝壳找房且 MUST NOT 启动点击流程
