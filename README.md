# 柠枺工具箱 (LemwoodTools)

一个基于Android平台的多功能工具箱应用，使用现代化的技术栈构建。

## 📱 应用信息

- **应用名称**: 柠枺工具箱
- **包名**: `cn.lemwood`
- **最低支持版本**: Android 7.1 (API 25)
- **目标版本**: Android 14 (API 34)
- **UI框架**: Material Design 3 + Jetpack Compose

## 🚀 功能模块

- **计算器**: 基础数学计算功能
- **单位转换**: 长度、重量、温度等单位转换
- **二维码**: 二维码生成和扫描
- **文本工具**: 文本处理和格式化工具

## 🛠 技术栈

- **开发语言**: Kotlin
- **UI框架**: Jetpack Compose
- **设计系统**: Material Design 3
- **架构模式**: MVVM (准备)
- **导航**: Navigation Compose
- **构建工具**: Gradle
- **最低JDK版本**: JDK 17

## 📁 项目结构

```
lemwoodtools/
├── app/                          # 主应用模块
│   ├── src/main/
│   │   ├── java/cn/lemwood/      # Kotlin源码
│   │   │   ├── MainActivity.kt   # 主Activity
│   │   │   ├── LemwoodToolsApp.kt # 应用主界面
│   │   │   └── ui/theme/         # UI主题
│   │   └── res/                  # 资源文件
│   ├── build.gradle              # 应用构建配置
│   └── proguard-rules.pro        # 代码混淆和性能优化规则
├── .github/
│   ├── workflows/
│   │   ├── android.yml           # CI/CD工作流
│   │   └── code-quality.yml      # 代码质量检查工作流
│   ├── dependabot.yml            # 依赖自动更新配置
│   └── SECRETS.md                # Secrets配置说明
├── scripts/
│   ├── parse-test-results.sh     # 测试结果解析脚本(Linux/macOS)
│   ├── parse-test-results.ps1    # 测试结果解析脚本(Windows)
│   ├── test-parsing.sh           # 脚本测试工具
│   └── README.md                 # 脚本使用说明
├── build.gradle                  # 项目构建配置
├── settings.gradle               # 项目设置
├── gradle.properties             # Gradle属性和性能优化
├── detekt.yml                    # Detekt静态分析配置
├── dependency-check-suppressions.xml # 依赖安全扫描抑制规则
├── .editorconfig                 # 编辑器配置
├── build.sh                      # Linux/macOS构建脚本
├── build.bat                     # Windows构建脚本
├── CODE_QUALITY.md               # 代码质量和维护性指南
├── CICD.md                       # CI/CD详细说明
└── README.md                     # 项目说明
```

## 🔧 开发环境配置

### 必需环境
1. **Android Studio**: 最新稳定版
2. **JDK**: 17或更高版本
3. **Android SDK**: API 25-34
4. **Gradle**: 8.0+

### 环境变量
```bash
# Android SDK路径
export ANDROID_HOME=/path/to/android/sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

## 🏗 构建和运行

### 快速开始
```bash
# 克隆项目
git clone <repository-url>
cd lemwoodtools

# Linux/macOS
chmod +x build.sh
./build.sh

# Windows
build.bat
```

### 手动构建
```bash
# 清理项目
./gradlew clean

# 运行测试
./gradlew test

# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本 (需要签名配置)
./gradlew assembleRelease
```

### 安装到设备
```bash
# 安装Debug版本
adb install app/build/outputs/apk/debug/app-debug.apk

# 安装Release版本
adb install app/build/outputs/apk/release/app-release.apk
```

## 🔄 CI/CD 配置

项目使用GitHub Actions实现自动化构建和部署：

### 工作流程
1. **测试阶段**: 代码检查(Lint) + 单元测试
2. **构建阶段**: 生成Debug和Release APK
3. **部署阶段**: 创建GitHub Release并上传APK

### 触发条件
- `main`分支和`develop`分支的Push事件
- `main`分支的Pull Request事件

### 签名配置
为了生成正式的Release版本，需要在GitHub仓库中配置以下Secrets：

- `SIGNING_KEY_ALIAS`: 签名密钥别名
- `SIGNING_KEY_PASSWORD`: 签名密钥密码
- `SIGNING_STORE_PASSWORD`: 密钥库密码
- `SIGNING_KEYSTORE`: Base64编码的keystore文件

详细配置说明请参考: [.github/SECRETS.md](.github/SECRETS.md)

### 构建状态
[![Android CI/CD](https://github.com/username/lemwoodtools/actions/workflows/android.yml/badge.svg)](https://github.com/username/lemwoodtools/actions/workflows/android.yml)

## 📊 代码质量与维护性

本项目集成了多种代码质量工具和最佳实践，确保代码的高质量和可维护性。

### 🔍 静态代码分析

#### Detekt - Kotlin静态分析
```bash
# 运行Detekt检查
./gradlew detekt

# 查看Detekt报告
open app/build/reports/detekt/detekt.html
```

#### Android Lint - Android特定检查
```bash
# 运行Lint检查
./gradlew lintDebug

# 查看Lint报告
open app/build/reports/lint-results-debug.html
```

#### OWASP依赖安全扫描
```bash
# 运行依赖安全检查
./gradlew dependencyCheckAnalyze

# 查看安全报告
open app/build/reports/dependency-check-report.html
```

### 🧪 测试与覆盖率

#### 单元测试
```bash
# 运行单元测试
./gradlew testDebugUnitTest

# 查看测试报告
open app/build/reports/tests/testDebugUnitTest/index.html
```

#### 代码覆盖率
```bash
# 生成测试覆盖率报告
./gradlew testDebugUnitTestCoverage

# 查看覆盖率报告
open app/build/reports/coverage/test/debug/index.html
```

### 🚀 一键代码质量检查
```bash
# 运行所有代码质量检查
./gradlew codeQuality
```

### 📈 质量度量目标
- **代码覆盖率**: > 80%
- **Detekt问题**: < 10个
- **Lint错误**: 0个
- **安全漏洞**: 0个高危漏洞

### 🛠️ 自动化工具

#### GitHub Actions工作流
- **代码质量检查**: `.github/workflows/code-quality.yml`
- **CI/CD流水线**: `.github/workflows/android.yml`
- **依赖更新**: Dependabot配置

#### 开发工具配置
- **EditorConfig**: `.editorconfig` - 统一代码格式
- **Detekt配置**: `detekt.yml` - 自定义检查规则
- **ProGuard规则**: `app/proguard-rules.pro` - 性能优化

### 📚 质量文档
- **详细指南**: [CODE_QUALITY.md](CODE_QUALITY.md)
- **CI/CD说明**: [CICD.md](CICD.md)
- **脚本文档**: [scripts/README.md](scripts/README.md)

## 🎨 设计规范

### 颜色主题
- **主色调**: 深绿色 (#2E7D32)
- **次要色**: 绿色 (#4CAF50)
- **背景色**: 浅绿色 (#E8F5E8)
- **错误色**: 红色 (#D32F2F)

### 字体规范
- 使用Material Design 3默认字体系统
- 支持多语言显示
- 响应式字体大小

## 🚀 发布流程

### 版本管理
1. 更新`app/build.gradle`中的`versionCode`和`versionName`
2. 提交代码到`main`分支
3. CI/CD自动构建并创建GitHub Release
4. 下载Release APK进行分发

### 版本号规则
- `versionCode`: 递增整数
- `versionName`: 语义化版本 (如: 1.0.0)

## 🤝 贡献指南

1. Fork项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

### 代码规范
- 遵循Kotlin编码规范
- 使用Compose最佳实践
- 添加必要的注释和文档
- 确保测试覆盖率

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系方式

- **项目维护者**: [Your Name]
- **邮箱**: [your.email@example.com]
- **问题反馈**: [GitHub Issues](https://github.com/username/lemwoodtools/issues)

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者和用户！

---

**注意**: 这是一个基础框架项目，各个功能模块的具体实现将在后续版本中逐步完善。