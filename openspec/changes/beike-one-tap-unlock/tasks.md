## 1. 工程脚手架

- [x] 1.1 创建 Gradle 单模块 Android 工程（Kotlin、minSdk 26、targetSdk 34）
- [x] 1.2 配置清单：启动 Activity、无障碍服务、queries 贝壳包名、设置快捷方式

## 2. 一键触发

- [x] 2.1 实现 MainActivity：桌面启动立即开门；最近任务返回不误开；缺权限进引导
- [x] 2.2 实现设置页与「设置」Launcher Shortcut，修改点击文案

## 3. 无障碍开门

- [x] 3.1 实现 UnlockCoordinator 步骤状态机与超时
- [x] 3.2 实现 UnlockAccessibilityService：仅贝壳包名、按文案点击可点击节点

## 4. 引导与文档

- [x] 4.1 实现 OPPO 权限引导页
- [x] 4.2 编写 `docs/一键开门.md`（安装、无障碍、ColorOS 保活、使用方式）
