## ADDED Requirements

### Requirement: 预置五套启动图标
应用 MUST 内置五套启动图标：`dark`、`white_green`、`white_line`、`white_mint`、`green_white`。

#### Scenario: 设置页能看到全部图标
- **WHEN** 用户打开设置页
- **THEN** 系统 MUST 展示上述五套图标预览供选择

### Requirement: 设置页切换桌面图标
用户在设置页点选一套图标后，系统 MUST 启用对应 `activity-alias`，并禁用其余 launcher alias，使桌面图标变为所选套。此操作 MUST NOT 启动开门流程。

#### Scenario: 点选翠绿圆底白锁
- **WHEN** 用户在设置页点选 `green_white`
- **THEN** 桌面启动器图标 MUST 切换为翠绿圆底白锁，且 MUST NOT 开始开门

#### Scenario: 切换后仍可从桌面开门
- **WHEN** 用户已切换图标并回到桌面点击本应用
- **THEN** 系统 MUST 仍按现有规则启动开门流程

### Requirement: 记住所选图标
所选图标 ID MUST 持久化。再次打开设置页时 MUST 高亮当前选中项。

#### Scenario: 重进设置看到上次选择
- **WHEN** 用户选择 `white_line` 后离开再进入设置
- **THEN** `white_line` MUST 显示为当前选中
