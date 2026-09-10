## Context

用户桌面是 ColorOS 彩圆图标。现用深色薄荷绿锁对比度低。已预览 A/B/C 与翠绿圆底白锁。用户要求全部入库，并在 App 里自选。

## Goals / Non-Goals

**Goals:** 五套图标都进包；设置页点选即换桌面图标；默认用翠绿圆底白锁。

**Non-Goals:** 不支持自选相册图片；不改开门流程；不做桌面小组件。

## Decisions

1. **切换方式**：`activity-alias` 指向 `MainActivity`。`MainActivity` 去掉 `LAUNCHER`，由 5 个别名分别带 `MAIN/LAUNCHER` 和对应 icon。同一时间只启用一个 alias。
2. **默认**：`green_white` 启用，其余 `android:enabled="false"`。
3. **设置页**：文案卡片上方增加「桌面图标」一行五格预览，当前项描边高亮。点选即 `PackageManager.setComponentEnabledSetting`，并写入 `UnlockPrefs`。
4. **图标资源**：A/B/C/`green_white` 用自适应图标（纯色 background + 矢量前景），避免每套铺 5 密度 PNG。`dark` 沿用现有 mipmap。
5. **OPPO**：切换后 Toast 提示「已更换，若桌面没变请回桌面或稍等」。不要求重启手机。
6. **快捷方式**：长按「设置」仍指向 `SettingsActivity`，不受 alias 影响。

## Risks / Trade-offs

- [ColorOS 缓存旧图标] → Toast 说明；覆盖安装后若仍旧，需划掉图标再从应用列表拖回。
- [禁用当前 alias 瞬间桌面图标消失] → 先启用新的，再禁用旧的。
