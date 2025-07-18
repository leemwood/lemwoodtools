# 快速开始指南

欢迎来到LemwoodTools项目！这个指南将帮助你快速设置开发环境并开始贡献代码。

## 🚀 5分钟快速开始

### 1. 环境准备
```bash
# 确保你已安装以下工具：
# - Android Studio (最新稳定版)
# - JDK 17+
# - Git
```

### 2. 克隆项目
```bash
git clone <repository-url>
cd lemwoodtools
```

### 3. 导入Android Studio
1. 打开Android Studio
2. 选择 "Open an existing Android Studio project"
3. 选择项目根目录
4. 等待Gradle同步完成

### 4. 运行项目
```bash
# 方式1: 使用Android Studio
# 点击绿色的运行按钮

# 方式2: 使用命令行
./gradlew installDebug
```

### 5. 验证环境
```bash
# 运行代码质量检查
./gradlew codeQuality

# 运行测试
./gradlew test
```

## 🛠️ 开发工作流

### 日常开发
1. **创建功能分支**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **开发代码**
   - 遵循Kotlin编码规范
   - 编写单元测试
   - 运行本地检查

3. **提交前检查**
   ```bash
   # 运行所有质量检查
   ./gradlew codeQuality
   
   # 确保测试通过
   ./gradlew test
   ```

4. **提交代码**
   ```bash
   git add .
   git commit -m "feat: add your feature description"
   git push origin feature/your-feature-name
   ```

5. **创建Pull Request**
   - 在GitHub上创建PR
   - 等待CI/CD检查通过
   - 请求代码审查

### 代码质量工具

#### 🔍 静态分析
```bash
# Kotlin代码检查
./gradlew detekt

# Android Lint检查
./gradlew lintDebug

# 依赖安全扫描
./gradlew dependencyCheckAnalyze
```

#### 🧪 测试
```bash
# 单元测试
./gradlew testDebugUnitTest

# 测试覆盖率
./gradlew testDebugUnitTestCoverage
```

## 📋 开发规范

### 代码风格
- 使用4个空格缩进
- 最大行长度120字符
- 遵循Kotlin官方编码约定
- 使用有意义的变量和函数名

### 提交信息规范
```
type(scope): description

# 类型:
# feat: 新功能
# fix: 修复bug
# docs: 文档更新
# style: 代码格式化
# refactor: 重构
# test: 测试相关
# chore: 构建过程或辅助工具的变动

# 示例:
feat(calculator): add basic arithmetic operations
fix(ui): resolve layout issue on small screens
docs(readme): update installation instructions
```

### 分支命名规范
```
feature/feature-name    # 新功能
bugfix/bug-description  # 修复bug
hotfix/urgent-fix      # 紧急修复
docs/documentation     # 文档更新
```

## 🔧 常用命令

### 构建相关
```bash
# 清理项目
./gradlew clean

# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本
./gradlew assembleRelease

# 安装到设备
./gradlew installDebug
```

### 质量检查
```bash
# 一键质量检查
./gradlew codeQuality

# 单独运行各项检查
./gradlew detekt
./gradlew lintDebug
./gradlew dependencyCheckAnalyze
./gradlew testDebugUnitTest
```

### 报告查看
```bash
# Detekt报告
open app/build/reports/detekt/detekt.html

# Lint报告
open app/build/reports/lint-results-debug.html

# 测试报告
open app/build/reports/tests/testDebugUnitTest/index.html

# 覆盖率报告
open app/build/reports/coverage/test/debug/index.html

# 安全扫描报告
open app/build/reports/dependency-check-report.html
```

## 🚨 常见问题

### Gradle构建失败
```bash
# 清理并重新构建
./gradlew clean build

# 检查Gradle版本
./gradlew wrapper --gradle-version 8.14.3

# 刷新依赖
./gradlew --refresh-dependencies
```

### 代码检查失败
```bash
# 查看详细错误信息
./gradlew detekt --info

# 自动修复格式问题
./gradlew detektFormat

# 查看Lint详细报告
./gradlew lintDebug --info
```

### 测试失败
```bash
# 运行特定测试
./gradlew test --tests "ClassName.testMethodName"

# 查看测试日志
./gradlew test --info

# 重新运行失败的测试
./gradlew test --rerun-tasks
```

## 📚 学习资源

### 官方文档
- [Android开发指南](https://developer.android.com/guide)
- [Kotlin语言指南](https://kotlinlang.org/docs/home.html)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)

### 项目文档
- [代码质量指南](CODE_QUALITY.md)
- [CI/CD说明](CICD.md)
- [脚本使用说明](scripts/README.md)

### 工具文档
- [Detekt规则](https://detekt.github.io/detekt/)
- [Android Lint检查](https://developer.android.com/studio/write/lint)
- [OWASP依赖检查](https://owasp.org/www-project-dependency-check/)

## 🤝 获取帮助

如果遇到问题：
1. 查看本文档和相关文档
2. 搜索GitHub Issues中的已知问题
3. 在团队群组中询问
4. 创建新的GitHub Issue

## 🎯 下一步

现在你已经准备好开始开发了！建议：

1. 浏览现有代码，了解项目结构
2. 查看开放的Issues，选择感兴趣的任务
3. 阅读[代码质量指南](CODE_QUALITY.md)了解最佳实践
4. 开始你的第一个贡献！

---

祝你开发愉快！🎉