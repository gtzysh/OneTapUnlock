## Why

门锁页「蓝牙开门」是 H5/自定义圆钮，无障碍树经常没有可点节点；且内容变化事件可能来自 WebView 包名，服务当前只收贝壳包名所以点不到。手动点后会出现「开门中...」，自动流程应等到该状态再判定成功。

## What Changes

- 无障碍不再限定只收 `com.lianjia.beike` 事件。
- 识别门锁页后，按屏幕比例点击圆钮位置（主线程投递手势）。
- 见到「开门中」视为点中；结束后回桌面 Toast「开门成功」。
- 点不中圆钮时不要误点标题「智能门锁」。

## Capabilities

### Modified Capabilities
- `beike-ui-automation`: 门锁页坐标点击 + 「开门中」确认。

## Impact

- NodeClicker、UnlockCoordinator、UnlockAccessibilityService、accessibility XML、文档、APK。
