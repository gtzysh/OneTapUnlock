## ADDED Requirements

### Requirement: 展示 ColorOS 必开权限
引导页 MUST 列出在 OPPO / ColorOS 上使用本功能所需的权限，至少包括无障碍、后台弹出界面、自启动与耗电不冻结。

#### Scenario: 首次未授权看到清单
- **WHEN** 用户因无障碍未开启进入引导页
- **THEN** 页面 MUST 展示上述权限说明，并 MUST 提供打开系统无障碍设置的按钮

### Requirement: 设置与开门入口分离
日常开门 MUST 使用主启动器图标；设置 MUST 通过独立入口打开，避免每次开门先进入设置页。

#### Scenario: 长按设置快捷方式
- **WHEN** 用户长按本应用图标并选择「设置」
- **THEN** 系统 MUST 打开设置页且 MUST NOT 立即启动开门点击
