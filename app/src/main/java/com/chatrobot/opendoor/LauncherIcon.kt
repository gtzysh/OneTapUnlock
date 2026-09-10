package com.chatrobot.opendoor

// 【开源声明】本文件以 MIT 许可证发布。Copyright (c) 2026 gaotongzhuang
// 【五套桌面图标：设置页预览 + activity-alias 类名】
enum class LauncherIcon(
    val id: String,
    val aliasClass: String,
    val previewRes: Int,
    val titleRes: Int
) {
    DARK(
        id = "dark",
        aliasClass = "com.chatrobot.opendoor.LauncherDark",
        previewRes = R.drawable.ic_preview_dark,
        titleRes = R.string.icon_dark
    ),
    WHITE_GREEN(
        id = "white_green",
        aliasClass = "com.chatrobot.opendoor.LauncherWhiteGreen",
        previewRes = R.drawable.ic_preview_white_green,
        titleRes = R.string.icon_white_green
    ),
    WHITE_LINE(
        id = "white_line",
        aliasClass = "com.chatrobot.opendoor.LauncherWhiteLine",
        previewRes = R.drawable.ic_preview_white_line,
        titleRes = R.string.icon_white_line
    ),
    WHITE_MINT(
        id = "white_mint",
        aliasClass = "com.chatrobot.opendoor.LauncherWhiteMint",
        previewRes = R.drawable.ic_preview_white_mint,
        titleRes = R.string.icon_white_mint
    ),
    GREEN_WHITE(
        id = "green_white",
        aliasClass = "com.chatrobot.opendoor.LauncherGreenWhite",
        previewRes = R.drawable.ic_preview_green_white,
        titleRes = R.string.icon_green_white
    );

    companion object {
        const val DEFAULT_ID = "green_white"

        fun fromId(id: String?): LauncherIcon {
            return entries.firstOrNull { it.id == id } ?: GREEN_WHITE
        }
    }
}
