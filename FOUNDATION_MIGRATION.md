# Foundation UI 迁移指南

## 概述

本文档描述了从 Material Design 3 到 Foundation UI 的迁移策略。Foundation UI 是一个基于 Compose Foundation 的轻量级设计系统，旨在提供更好的性能和更灵活的定制能力。

## 迁移策略

### 第一阶段：基础设施 ✅ 已完成
- [x] 添加 Compose Foundation 依赖
- [x] 创建 Foundation 主题系统（颜色、字体、形状）
- [x] 实现基础组件（Button、Card、Text）
- [x] 创建示例屏幕验证系统

### 第二阶段：核心组件 ✅ 已完成
- [x] 创建 ToolCard Foundation 版本
- [x] 添加导航和演示页面
- [x] 验证组件集成

### 第三阶段：高级组件 ✅ 已完成
- [x] TextField 组件（FoundationTextField、FoundationOutlinedTextField）
- [x] Switch 和 Checkbox 组件
- [x] IconButton 系列组件（标准、填充、轮廓、浮动操作按钮）
- [x] BottomNavigation 组件
- [x] TopAppBar 组件（标准和大型）
- [x] Dialog 组件（警告、底部弹出、确认对话框）

### 第四阶段：完整迁移（计划中）
- [ ] 迁移现有屏幕到 Foundation UI
- [ ] 性能优化和测试
- [ ] 文档完善

## 组件对比

| Material 3 组件 | Foundation UI 组件 | 状态 | 说明 |
|----------------|-------------------|------|------|
| `Button` | `FoundationButton` | ✅ 完成 | 轻量级按钮实现 |
| `Card` | `FoundationCard` | ✅ 完成 | 简化的卡片组件 |
| `Text` | `FoundationText` | ✅ 完成 | 基于 BasicText 的文本组件 |
| `TextField` | `FoundationTextField` | ✅ 完成 | 自定义输入框 |
| `OutlinedTextField` | `FoundationOutlinedTextField` | ✅ 完成 | 轮廓输入框 |
| `Switch` | `FoundationSwitch` | ✅ 完成 | 开关组件 |
| `Checkbox` | `FoundationCheckbox` | ✅ 完成 | 复选框组件 |
| `IconButton` | `FoundationIconButton` | ✅ 完成 | 图标按钮 |
| `FilledIconButton` | `FoundationFilledIconButton` | ✅ 完成 | 填充图标按钮 |
| `OutlinedIconButton` | `FoundationOutlinedIconButton` | ✅ 完成 | 轮廓图标按钮 |
| `FloatingActionButton` | `FoundationFloatingActionButton` | ✅ 完成 | 浮动操作按钮 |
| `SmallFloatingActionButton` | `FoundationSmallFloatingActionButton` | ✅ 完成 | 小型浮动操作按钮 |
| `BottomNavigation` | `FoundationBottomNavigation` | ✅ 完成 | 底部导航栏 |
| `BottomNavigationItem` | `FoundationBottomNavigationItem` | ✅ 完成 | 底部导航项 |
| `TopAppBar` | `FoundationTopAppBar` | ✅ 完成 | 顶部应用栏 |
| `LargeTopAppBar` | `FoundationLargeTopAppBar` | ✅ 完成 | 大型顶部应用栏 |
| `AlertDialog` | `FoundationAlertDialog` | ✅ 完成 | 警告对话框 |
| `Dialog` | `FoundationDialog` | ✅ 完成 | 基础对话框 |
| `BottomSheetDialog` | `FoundationBottomSheetDialog` | ✅ 完成 | 底部弹出对话框 |
| `Scaffold` | `FoundationScaffold` | 🔄 计划中 | 页面脚手架 |
| `NavigationRail` | `FoundationNavigationRail` | 🔄 计划中 | 侧边导航栏 |
| `Snackbar` | `FoundationSnackbar` | 🔄 计划中 | 消息提示 |
| `Chip` | `FoundationChip` | 🔄 计划中 | 标签组件 |
| `Slider` | `FoundationSlider` | 🔄 计划中 | 滑块组件 |
| `ProgressIndicator` | `FoundationProgressIndicator` | 🔄 计划中 | 进度指示器 |

## 使用示例

### 基础组件

```kotlin
// 按钮
FoundationButton(
    onClick = { /* 处理点击 */ }
) {
    FoundationText(
        text = "点击我",
        color = FoundationTheme.colors.onPrimary
    )
}

// 卡片
FoundationCard(
    modifier = Modifier.fillMaxWidth(),
    elevation = 4.dp,
    onClick = { /* 处理点击 */ }
) {
    Column(modifier = Modifier.padding(16.dp)) {
        FoundationText(
            text = "卡片标题",
            style = FoundationTheme.typography.titleMedium
        )
        FoundationText(
            text = "卡片内容",
            style = FoundationTheme.typography.bodyMedium
        )
    }
}
```

### 输入组件

```kotlin
// 文本输入框
var text by remember { mutableStateOf("") }
FoundationTextField(
    value = text,
    onValueChange = { text = it },
    placeholder = {
        FoundationText(text = "请输入...")
    },
    leadingIcon = {
        Icon(Icons.Default.Search, contentDescription = "搜索")
    }
)

// 开关
var checked by remember { mutableStateOf(false) }
FoundationSwitch(
    checked = checked,
    onCheckedChange = { checked = it }
)
```

### 导航组件

```kotlin
// 顶部应用栏
FoundationTopAppBar(
    title = "应用标题",
    navigationIcon = {
        FoundationIconButton(onClick = { /* 返回 */ }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "返回")
        }
    },
    actions = {
        FoundationTopAppBarAction(
            icon = Icons.Default.Settings,
            contentDescription = "设置",
            onClick = { /* 设置 */ }
        )
    }
)

// 底部导航栏
val bottomNavItems = listOf(
    BottomNavigationItem(
        icon = Icons.Default.Home,
        label = "首页",
        route = "home"
    ),
    BottomNavigationItem(
        icon = Icons.Default.Search,
        label = "搜索", 
        route = "search"
    )
)

FoundationBottomNavigationBar(
    items = bottomNavItems,
    selectedRoute = currentRoute,
    onItemClick = { route -> /* 导航到路由 */ }
)
```

### 对话框

```kotlin
// 确认对话框
FoundationConfirmDialog(
    onDismissRequest = { showDialog = false },
    title = "确认删除",
    message = "此操作不可撤销，确定要删除吗？",
    onConfirm = { /* 执行删除 */ },
    confirmText = "删除",
    cancelText = "取消",
    isDestructive = true
)

// 警告对话框
FoundationAlertDialog(
    onDismissRequest = { showDialog = false },
    title = "提示",
    text = "操作已完成",
    confirmButtonText = "确定",
    onConfirmClick = { showDialog = false }
)
```

## Foundation UI 的优势

### 1. 性能优化
- **更小的包体积**：移除 Material 3 的复杂主题系统
- **更快的渲染**：使用 BasicText 和简化的组件结构
- **减少重组**：优化的状态管理和组件设计

### 2. 更好的定制性
- **简化的主题系统**：更容易理解和修改
- **灵活的组件 API**：支持更多定制选项
- **一致的设计语言**：统一的视觉风格

### 3. 开发体验
- **更清晰的 API**：简化的组件接口
- **更好的文档**：详细的使用示例和说明
- **更容易维护**：模块化的组件结构

## 迁移注意事项

### 1. 主题切换
```kotlin
// 旧的 Material 3 主题
MaterialTheme {
    // 内容
}

// 新的 Foundation 主题
FoundationTheme {
    // 内容
}
```

### 2. 颜色访问
```kotlin
// 旧方式
MaterialTheme.colorScheme.primary

// 新方式
FoundationTheme.colors.primary
```

### 3. 字体样式
```kotlin
// 旧方式
MaterialTheme.typography.headlineMedium

// 新方式
FoundationTheme.typography.headlineMedium
```

### 4. 形状定义
```kotlin
// 旧方式
MaterialTheme.shapes.medium

// 新方式
FoundationTheme.shapes.medium
```

## 测试和验证

### 1. 组件演示
访问应用中的 "Foundation Demo" 页面查看所有组件的使用示例。

### 2. 性能测试
- 对比 Material 3 和 Foundation UI 的渲染性能
- 测量包体积变化
- 验证内存使用情况

### 3. 兼容性测试
- 确保在不同设备上的显示效果
- 验证深色模式支持
- 测试无障碍功能

## 下一步计划

1. **完成剩余组件**：Scaffold、NavigationRail、Snackbar 等
2. **迁移现有屏幕**：逐步将现有页面迁移到 Foundation UI
3. **性能优化**：进一步优化组件性能
4. **文档完善**：补充详细的 API 文档和最佳实践

## 反馈和支持

如果在迁移过程中遇到问题，请：
1. 查看组件演示页面的实现示例
2. 参考本文档的使用指南
3. 检查组件的源代码实现

Foundation UI 旨在提供更好的开发体验和应用性能，同时保持与现有代码的兼容性。