## ADDED Requirements

### Requirement: MIT 许可证文件
仓库根目录 MUST 包含 MIT 许可证全文，版权行 MUST 为 `Copyright (c) 2026 gaotongzhuang`。

#### Scenario: 根目录可见许可证
- **WHEN** 克隆本仓库
- **THEN** 根目录 MUST 存在名为 `LICENSE` 的 MIT 文本文件

### Requirement: Kotlin 源码开源声明
`app/src/main/java/` 下每个 Kotlin 源文件 MUST 包含中文开源声明注释，格式为 `// 【开源声明】…`，并标明 MIT 与版权人。

#### Scenario: 打开任一 Kotlin 文件可见声明
- **WHEN** 阅读任意业务 Kotlin 源文件
- **THEN** 该文件 MUST 含 `// 【开源声明】` 且声明 MIT

### Requirement: 文档不含本机绝对路径
`README.md` 与 `docs/一键开门.md` MUST NOT 出现本机盘符路径（如 `F:\`）。安装包与工程位置 MUST 使用仓库相对路径或构建产物路径。

#### Scenario: 文档可在任意机器阅读
- **WHEN** 阅读 README 或使用说明
- **THEN** 文档 MUST NOT 依赖某台电脑的绝对路径

### Requirement: 可移植的 Debug 打包说明
文档 MUST 说明：JDK 17、Android SDK（compileSdk 34）、用项目自带 Gradle Wrapper 执行 `assembleDebug`、产物为 `app/build/outputs/apk/debug/app-debug.apk`、APK 不提交到 git。

#### Scenario: 按文档编译
- **WHEN** 贡献者按 README 或 `docs/一键开门.md` 的编译步骤操作
- **THEN** 步骤 MUST 可在未配置本机绝对路径的环境下完成 Debug 打包

### Requirement: Wrapper 使用官方发行地址
`gradle/wrapper/gradle-wrapper.properties` 的 `distributionUrl` MUST 指向 Gradle 官方发行包，MUST NOT 指向本机 `file://` 路径。

#### Scenario: 克隆后 Wrapper 可下载发行包
- **WHEN** 干净克隆后执行 `gradlew.bat assembleDebug`
- **THEN** Wrapper MUST 能从官方地址解析 Gradle 8.9，而不是本机 zip 路径

### Requirement: 使用规范覆盖首次与日常
`docs/一键开门.md` MUST 包含：第一次使用（无障碍、后台弹出、自启动、耗电）、日常点图标开门、失败处理、乱点时关闭无障碍、限制与卸载。

#### Scenario: 新用户能完成首次配置
- **WHEN** 用户安装 debug APK 后打开应用
- **THEN** 文档 MUST 给出无障碍与 ColorOS 相关权限的顺序说明
