## Context

用户选择 MIT。文档仍含 `F:\OneTapUnlock`、`F:\chatRobot\dist\...`；Wrapper 指向 `file:///F:/OneTapUnlock/gradle/gradle-8.9-bin.zip`，该 zip 已被 gitignore，克隆后无法构建。

## Goals / Non-Goals

**Goals:**

- 仓库可被他人按文档克隆、编译 debug APK、安装并完成首次权限配置。
- MIT 文本完整；Kotlin 源码有中文开源声明。
- 文档不含本机绝对路径。

**Non-Goals:**

- 不改开门状态机、UI、包名、版本号。
- 不配置正式签名 / Play 发布。
- 不在 XML / Gradle 脚本逐文件加许可证头（由根目录 `LICENSE` 覆盖）。
- 不在应用内做「关于 / 许可证」页面。

## Decisions

1. **许可证范围**：根目录 `LICENSE` 为完整 MIT；每个 `.kt` 在现有 `// 【xxx】` 文件头上一行增加：
   `// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang`
2. **版权人**：与 git author 一致，用 `gaotongzhuang`，不用 GitHub 用户名。
3. **README 职责**：GitHub 首页；详细步骤仍在 `docs/一键开门.md`。
4. **打包**：JDK 17 + Android SDK 34；Windows 用 `gradlew.bat assembleDebug`；产物路径写仓库相对路径 `app/build/outputs/apk/debug/app-debug.apk`；说明 APK 不入库。
5. **Wrapper**：`distributionUrl=https://services.gradle.org/distributions/gradle-8.9-bin.zip`。本机已有的 zip 仍可被 Gradle 缓存复用，但不作为仓库依赖。
6. **使用规范**：保留 OPPO 首次权限、日常操作、做不到的事、卸载；补充无障碍乱点时先关服务、仅辅助官方 App、不绕过登录。

## Risks / Trade-offs

- [首次构建需下载 Gradle 8.9] → 文档写明需要网络；官方 URL 是可移植方案。
- [版权名与 GitHub 登录名不一致] → 以 git author 为准，README 可同时写仓库地址。
