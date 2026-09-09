## Context

当前 `drawable/ic_app` 是深底绿锁线框，在 OPPO 桌面偏简陋。

## Goals / Non-Goals

**Goals:** 桌面图标更精致，圆形/圆角裁切后锁芯仍完整。

**Non-Goals:** 不改开门逻辑。

## Decisions

1. 使用自适应图标：背景深绿、前景开锁。
2. 旧 `ic_app` 可保留给通知等，启动器用新 mipmap。
