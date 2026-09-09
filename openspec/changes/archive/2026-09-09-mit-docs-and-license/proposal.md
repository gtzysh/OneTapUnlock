## Why

仓库已公开到 GitHub，但 README 和说明仍写本机路径，Gradle Wrapper 也指向本机 zip，别人无法按文档编译。同时缺少 MIT 许可证与源码声明，开源意图不完整。

## What Changes

- 新增根目录 `LICENSE`（MIT），版权人 `gaotongzhuang`，年份 2026。
- 全部 Kotlin 源文件文件头增加 `// 【开源声明】` 一行。
- 重写 `README.md`：去掉本机路径，作为 GitHub 首页说明（功能、限制、环境、打包、安装、使用摘要、许可证）。
- 更新 `docs/一键开门.md`：去掉本机路径，完善编译打包与使用规范。
- 将 `gradle/wrapper/gradle-wrapper.properties` 的发行包地址改为官方 Gradle 8.9 URL，使克隆后可构建。
- 不升版本号（仍为 1.4.5）；不改开门逻辑。

## Capabilities

### New Capabilities

- `open-source-packaging`: MIT 许可证与源码声明；面向克隆者的打包与使用文档，且不包含本机绝对路径。

### Modified Capabilities

- （无运行时需求变更）

## Impact

- 文档：`README.md`、`docs/一键开门.md`
- 法律：新增 `LICENSE`
- 源码声明：`app/src/main/java/com/chatrobot/opendoor/*.kt`（10 个文件）
- 构建：`gradle/wrapper/gradle-wrapper.properties`
- 不影响无障碍点击流程、包名、版本号。
