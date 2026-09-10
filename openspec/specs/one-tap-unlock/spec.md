# one-tap-unlock Specification

## Purpose
桌面点图标即开始开门；无论当前启用哪一套启动图标 alias，行为相同。
## Requirements
### Requirement: 点击应用图标立即开门
在无障碍已开启且已安装贝壳找房的前提下，用户从桌面启动本应用时，系统 MUST 立即开始开门流程。桌面图标无论当前启用哪一套 alias，行为 MUST 相同。

#### Scenario: 切换图标后冷启动仍开门
- **WHEN** 用户已换成非默认图标并从桌面点击
- **THEN** 本应用 MUST 立即开始开门流程

