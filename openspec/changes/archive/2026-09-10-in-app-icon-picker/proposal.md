## Why

桌面图标需要和 ColorOS 一排彩圆图标匹配，但用户还想保留其它风格并自己选。只换一张默认图无法满足；需要在应用内切换桌面图标。

## What Changes

- 预置 5 套启动图标，全部生成进工程。
- 设置页增加图标选择：点选后立刻切换桌面图标（`activity-alias`）。
- 切换只改图标，不触发开门。
- 版本升至 1.5.0。
- 文档补充：OPPO 可能要回桌面或稍等才会刷新图标。

五套图标：

1. `dark`：现用深色底薄荷绿锁
2. `white_green`：白底系统绿锁（A）
3. `white_line`：白底细线锁（B）
4. `white_mint`：白底薄荷绿扁锁（C）
5. `green_white`：翠绿圆底白锁（对照桌面截图，默认推荐）

## Capabilities

### New Capabilities

- `launcher-icon-picker`: 设置页选择启动图标，系统桌面图标随之切换。

### Modified Capabilities

- `one-tap-unlock`: 设置入口增加图标选择；默认桌面图标改为翠绿圆底白锁。

## Impact

- `AndroidManifest.xml`（activity-alias）、`SettingsActivity`、`UnlockPrefs`、mipmap/drawable 图标、`docs/一键开门.md`、版本号。
- 开门点击逻辑不变。
