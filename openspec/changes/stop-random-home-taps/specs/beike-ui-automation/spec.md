## MODIFIED Requirements

### Requirement: 首页不得扫射点击
系统在贝壳首页寻找「我的」时，MUST 只点击底部导航上的「我的」一格，MUST NOT 按屏幕百分比在 93% 或 96% 高度反复手势点击。

#### Scenario: 找不到节点就停手
- **WHEN** 无障碍树里暂时没有可用的「我的」节点
- **THEN** 系统 MUST NOT 在页面中部或系统手势条上发点击，可最多尝试有限次底栏第 4 格后等待超时
