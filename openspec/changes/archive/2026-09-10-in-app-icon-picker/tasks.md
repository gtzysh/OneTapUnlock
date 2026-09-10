## 1. 五套图标资源

- [x] 1.1 新增 A/B/C/`green_white` 自适应图标（色值 background + 矢量锁）
- [x] 1.2 保留现有 `dark` mipmap 作为一套可选项

## 2. 桌面切换

- [x] 2.1 Manifest：MainActivity 去掉 LAUNCHER，增加 5 个 activity-alias
- [x] 2.2 `LauncherIconSwitcher`：先启用新 alias，再禁用其余
- [x] 2.3 `UnlockPrefs` 保存 `icon_id`

## 3. 设置页

- [x] 3.1 设置页增加五格图标选择，点选即切换并 Toast
- [x] 3.2 更新 `docs/一键开门.md`，版本 1.5.0
