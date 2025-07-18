# 代码质量与维护性指南

## 🎯 概述

本文档提供了提高LemwoodTools项目代码质量和可维护性的全面指南。我们已经集成了多种工具和最佳实践来确保代码的高质量。

## 🛠️ 集成的代码质量工具

### 1. **Detekt** - 静态代码分析
- **用途**: Kotlin代码的静态分析，检测代码异味、复杂性问题和潜在bug
- **配置文件**: `detekt.yml`
- **运行命令**: `./gradlew detekt`
- **报告位置**: `app/build/reports/detekt/`

### 2. **Android Lint** - Android特定检查
- **用途**: Android特定的代码检查，包括性能、安全性和兼容性问题
- **运行命令**: `./gradlew lintDebug` 或 `./gradlew lintRelease`
- **报告位置**: `app/build/reports/lint-results-*.html`

### 3. **OWASP Dependency Check** - 依赖安全扫描
- **用途**: 检查项目依赖中的已知安全漏洞
- **配置文件**: `dependency-check-suppressions.xml`
- **运行命令**: `./gradlew dependencyCheckAnalyze`
- **报告位置**: `app/build/reports/dependency-check-report.*`

### 4. **Dependabot** - 自动依赖更新
- **用途**: 自动检测并创建PR来更新过时的依赖
- **配置文件**: `.github/dependabot.yml`
- **频率**: 每周一次

## 🚀 GitHub Actions工作流

### 主要工作流
1. **Android CI/CD** (`.github/workflows/android.yml`)
   - 构建、测试、部署
   - 修复了测试结果发布问题
   - 支持多种构建类型（Debug、Staging、Release）

2. **代码质量检查** (`.github/workflows/code-quality.yml`)
   - 运行所有静态分析工具
   - 依赖安全扫描
   - 上传SARIF报告到GitHub Security

### 最近修复的问题
- ✅ **Gradle缓存冲突**: 统一使用`gradle/actions/setup-gradle@v4`
- ✅ **测试结果发布**: 替换有问题的action，使用自定义脚本
- ✅ **权限问题**: 添加了必要的GitHub Actions权限

## 📋 代码质量检查清单

### 提交前检查
```bash
# 运行所有代码质量检查
./gradlew codeQuality

# 或者分别运行
./gradlew detekt
./gradlew lintDebug
./gradlew dependencyCheckAnalyze
```

### 代码审查要点
- [ ] 代码遵循Kotlin编码规范
- [ ] 没有代码异味（长方法、大类等）
- [ ] 适当的错误处理
- [ ] 单元测试覆盖率
- [ ] 文档和注释的完整性
- [ ] 性能考虑
- [ ] 安全最佳实践

## 🎨 代码风格和格式化

### EditorConfig
- **配置文件**: `.editorconfig`
- **用途**: 确保不同编辑器间的一致性
- **覆盖**: 缩进、行尾、字符编码等

### Kotlin代码风格
- 使用4个空格缩进
- 最大行长度: 120字符
- 遵循官方Kotlin编码约定
- 使用有意义的变量和函数名

## 🔒 安全最佳实践

### 1. **依赖管理**
- 定期更新依赖到最新稳定版本
- 使用Dependabot自动检测更新
- 运行依赖安全扫描

### 2. **代码安全**
- 不在代码中硬编码敏感信息
- 使用环境变量或安全存储
- 验证所有用户输入
- 使用HTTPS进行网络通信

### 3. **构建安全**
- 签名配置通过环境变量管理
- 在CI/CD中使用GitHub Secrets
- 启用ProGuard/R8混淆

## 📊 性能优化

### 1. **构建性能**
- 启用Gradle并行构建
- 使用构建缓存
- 配置合适的JVM参数

### 2. **应用性能**
- 使用Compose性能最佳实践
- 启用R8代码收缩和混淆
- 优化APK大小（启用资源收缩）

### 3. **CI/CD性能**
- 使用Gradle缓存
- 并行运行作业
- 优化Docker镜像（如果使用）

## 🧪 测试策略

### 1. **单元测试**
- 目标覆盖率: 80%+
- 测试业务逻辑和工具函数
- 使用JUnit 4/5和Mockito

### 2. **UI测试**
- 使用Compose测试工具
- 测试关键用户流程
- 自动化回归测试

### 3. **集成测试**
- 测试组件间交互
- 数据库和网络操作测试

## 📈 监控和度量

### 1. **代码质量度量**
- 代码复杂度
- 代码重复率
- 技术债务
- 测试覆盖率

### 2. **构建度量**
- 构建时间
- 测试执行时间
- 部署频率
- 失败率

### 3. **安全度量**
- 漏洞数量和严重程度
- 依赖更新频率
- 安全扫描结果

## 🔧 开发工具配置

### Android Studio设置
1. 启用代码检查
2. 配置代码格式化规则
3. 安装推荐插件：
   - Detekt
   - SonarLint
   - GitToolBox

### Git Hooks（推荐）
```bash
# 安装pre-commit hook
echo "#!/bin/sh\n./gradlew detekt lintDebug" > .git/hooks/pre-commit
chmod +x .git/hooks/pre-commit
```

## 📚 学习资源

### Kotlin最佳实践
- [Kotlin官方编码约定](https://kotlinlang.org/docs/coding-conventions.html)
- [Android Kotlin风格指南](https://developer.android.com/kotlin/style-guide)

### Android开发
- [Android开发最佳实践](https://developer.android.com/guide)
- [Jetpack Compose指南](https://developer.android.com/jetpack/compose)

### 安全开发
- [OWASP移动安全](https://owasp.org/www-project-mobile-security/)
- [Android安全最佳实践](https://developer.android.com/topic/security/best-practices)

## 🚨 故障排除

### 常见问题

#### 1. Gradle构建失败
```bash
# 清理并重新构建
./gradlew clean build

# 检查Gradle版本兼容性
./gradlew wrapper --gradle-version 8.14.3
```

#### 2. Detekt检查失败
```bash
# 查看详细报告
./gradlew detekt --info

# 自动修复部分问题
./gradlew detektFormat
```

#### 3. 依赖冲突
```bash
# 查看依赖树
./gradlew app:dependencies

# 强制使用特定版本
implementation('com.example:library:1.0.0') {
    force = true
}
```

## 🔄 持续改进

### 定期任务
- [ ] 每月审查代码质量报告
- [ ] 每季度更新工具和配置
- [ ] 年度技术债务清理
- [ ] 定期团队代码审查培训

### 度量和目标
- 代码覆盖率 > 80%
- Detekt问题 < 10个
- 构建时间 < 5分钟
- 零高危安全漏洞

## 📞 获取帮助

如果遇到问题或需要改进建议：
1. 查看相关工具的官方文档
2. 检查GitHub Issues中的已知问题
3. 在团队中讨论最佳实践
4. 考虑社区最佳实践和经验分享

---

*本指南会随着项目发展和工具更新而持续改进。建议定期审查和更新。*