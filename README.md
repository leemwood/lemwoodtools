# LemwoodTools - 柠檬木工具箱

一个基于Android WebView和Vue.js的多功能工具应用。

## 功能特性

- 📱 **现代化界面**: 使用Material Design 3设计语言
- 🧮 **计算器**: 支持基本数学运算
- 📢 **公告系统**: 从GitHub获取最新公告
- 🔄 **自动更新**: 检查GitHub Releases获取最新版本
- 🌐 **多语言支持**: 支持中文和英文
- 📱 **响应式设计**: 适配不同屏幕尺寸

## 技术栈

- **Android**: Kotlin + WebView
- **前端**: Vue.js 3
- **网络**: OkHttp + Gson
- **UI**: Material Design 3

## 项目结构

```
app/
├── src/main/
│   ├── java/cn/lemwoodtools/
│   │   ├── MainActivity.kt          # 主活动
│   │   ├── WebAppInterface.kt       # JS-Android接口
│   │   └── utils/
│   │       ├── GitHubApiClient.kt   # GitHub API客户端
│   │       ├── AnnouncementManager.kt # 公告管理
│   │       ├── VersionChecker.kt    # 版本检查
│   │       └── DataClasses.kt       # 数据类
│   ├── res/
│   │   ├── layout/                  # 布局文件
│   │   ├── values/                  # 资源文件
│   │   └── mipmap-*/               # 应用图标
│   └── assets/vue/
│       └── index.html              # Vue.js前端应用
```

## 构建说明

1. 确保安装了Android Studio和Android SDK
2. 克隆项目到本地
3. 在Android Studio中打开项目
4. 等待Gradle同步完成
5. 连接Android设备或启动模拟器
6. 点击运行按钮构建并安装应用

## 配置说明

### GitHub集成
应用会从以下GitHub仓库获取数据：
- 仓库: `lemwoodtools/lemwoodtools`
- 公告文件: `announcements.json`
- 版本检查: GitHub Releases

### 权限说明
- `INTERNET`: 用于网络请求
- `ACCESS_NETWORK_STATE`: 检查网络状态

## 开发说明

### 添加新功能
1. 在Vue.js前端添加新的页面/组件
2. 如需Android原生功能，在`WebAppInterface.kt`中添加方法
3. 更新相应的字符串资源文件

### 多语言支持
- 中文: `res/values-zh/strings.xml`
- 英文: `res/values-en/strings.xml`
- 默认: `res/values/strings.xml`

## 许可证

MIT License

## 作者

LemwoodTools Team