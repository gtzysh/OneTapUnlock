package com.chatrobot.opendoor

// 【开门结束后回传给界面的结果】
enum class UnlockOutcome {
    SUCCESS,
    CLICKED,
    FAILED
}

data class UnlockResult(
    val outcome: UnlockOutcome,
    val title: String,
    val detail: String,
    val timeLabel: String
)
