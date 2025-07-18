# CI/CD 工作流程文档

本文档详细说明了项目的 GitHub Actions 自动化流程。

## 🔧 最新修复

### 测试结果发布问题修复
- **问题**: `EnricoMi/publish-unit-test-result-action@v2` 因权限不足导致 403 错误
- **解决方案**: 
  - 添加了必要的工作流权限 (`checks: write`, `pull-requests: write`)
  - 创建了自定义测试结果解析脚本 (`scripts/parse-test-results.sh` 和 `scripts/parse-test-results.ps1`)
  - 使用 GitHub Step Summary 显示测试结果，避免权限问题
  - 将测试摘要作为 artifact 上传，便于查看

### 改进的测试报告
- 自动解析 JUnit XML 测试结果
- 统计测试总数、通过数、失败数、跳过数
- 解析 Lint 结果，显示错误、警告和信息数量
- 生成 Markdown 格式的测试摘要
- 在 GitHub Actions 的 Job Summary 中显示结果

## 🚀 触发条件

工作流程在以下情况下自动触发：

### 自动触发
- **Push 到主分支**: `main`, `develop`
- **Pull Request**: 针对 `main` 分支的 PR

### 手动触发
- **workflow_dispatch**: 可以手动选择构建类型
  - `debug`: 调试版本
  - `staging`: 预发布版本  
  - `release`: 正式发布版本

## 📋 工作流程阶段

### 1. 测试阶段 (test)
- **环境**: Ubuntu Latest
- **JDK**: OpenJDK 17 (Temurin)
- **步骤**:
  - 检出代码
  - 设置 Java 环境
  - 配置 Gradle
  - 运行单元测试 (`testDebugUnitTest`)
  - 运行 Lint 检查 (`lintDebug`)
  - 生成测试摘要
  - 上传测试报告和 Lint 报告
  - 在 Job Summary 中显示结果

### 2. 构建阶段 (build)
- **环境**: Ubuntu Latest
- **依赖**: 测试阶段完成
- **步骤**:
  - 检出代码
  - 设置 Java 环境
  - 配置 Gradle
  - 获取版本信息
  - 解码签名密钥 (如果可用)
  - 构建 APK (Debug/Staging/Release)
  - 检查 Release APK 是否存在
  - 上传 APK artifacts

### 3. 安全扫描阶段 (security-scan)
- **环境**: Ubuntu Latest
- **依赖**: 构建阶段完成
- **步骤**:
  - 检出代码
  - 下载构建的 APK
  - 使用 Trivy 进行漏洞扫描
  - 上传 SARIF 结果到 GitHub Security

### 4. 部署阶段 (deploy)
- **环境**: Ubuntu Latest
- **条件**: Release APK 成功构建
- **步骤**:
  - 检出代码
  - 下载 Release APK
  - 重命名 APK 文件
  - 生成动态 Changelog
  - 创建 GitHub Release
  - 上传 APK 到 Release

### 5. 通知阶段 (notify)
- **环境**: Ubuntu Latest
- **条件**: 工作流程结束时
- **步骤**:
  - 发送构建结果通知

## 🏗️ 构建变体

### Debug
- **用途**: 开发和调试
- **特点**: 
  - 启用调试功能
  - 包含调试符号
  - 不进行代码混淆
- **触发**: 所有 push 和 PR

### Staging
- **用途**: 预发布测试
- **特点**:
  - 接近生产环境配置
  - 部分优化
  - 有限的调试信息
- **触发**: 手动选择或特定分支

### Release
- **用途**: 正式发布
- **特点**:
  - 完全优化
  - 代码混淆和压缩
  - 需要签名密钥
- **触发**: 手动选择或 main 分支 push

## 🔐 所需的 GitHub Secrets

### 必需的 Secrets
```
KEYSTORE_FILE          # Base64 编码的签名密钥文件
KEYSTORE_PASSWORD      # 密钥库密码
KEY_ALIAS             # 密钥别名
KEY_PASSWORD          # 密钥密码
```

### 可选的 Secrets
```
SLACK_WEBHOOK_URL     # Slack 通知 (如果启用)
DISCORD_WEBHOOK_URL   # Discord 通知 (如果启用)
```

## 📦 产物管理

### 命名规则
- **Debug APK**: `debug-apk-{run_number}`
- **Staging APK**: `staging-apk-{run_number}`
- **Release APK**: `release-apk-{run_number}`
- **测试报告**: `test-reports-{run_number}`
- **Lint 报告**: `lint-reports-{run_number}`
- **测试摘要**: `test-summary-{run_number}`
- **安全扫描**: `security-scan-{run_number}`

### 保留策略
- **APK 文件**: 30 天
- **测试报告**: 7 天
- **安全扫描**: 14 天

## 🔍 故障排除

### 常见问题

#### 1. 测试结果发布失败
**症状**: 403 Forbidden 错误
**解决方案**: 
- 确保工作流程有正确的权限设置
- 使用自定义测试结果解析脚本
- 检查 `scripts/parse-test-results.sh` 是否可执行

#### 2. Release APK 构建失败
**症状**: 签名相关错误
**解决方案**:
- 检查 GitHub Secrets 是否正确设置
- 验证密钥库文件是否有效
- 确保密码和别名正确

#### 3. Artifact 下载失败
**症状**: Artifact 未找到或已过期
**解决方案**:
- 检查 artifact 命名是否一致
- 确认构建阶段成功完成
- 检查保留期限设置

### 调试技巧

#### 启用详细日志
```yaml
env:
  GRADLE_OPTS: -Dorg.gradle.logging.level=debug
```

#### 检查构建缓存
```yaml
- name: Debug Gradle cache
  run: |
    echo "Gradle cache location: $GRADLE_USER_HOME"
    ls -la $GRADLE_USER_HOME/caches/ || true
```

#### 验证环境变量
```yaml
- name: Debug environment
  run: |
    echo "Java version: $JAVA_HOME"
    echo "Gradle version: $(./gradlew --version)"
    echo "Build type: ${{ github.event.inputs.build_type }}"
```

## ⚡ 性能优化

### Gradle 优化
- 启用并行构建
- 使用构建缓存
- 配置 JVM 参数
- 启用增量编译

### GitHub Actions 优化
- 使用 Gradle Build Action 缓存
- 并行运行独立任务
- 优化 artifact 大小
- 合理设置保留期限

## 🛡️ 安全最佳实践

### Secrets 管理
- 定期轮换签名密钥
- 使用最小权限原则
- 避免在日志中暴露敏感信息
- 使用环境特定的配置

### 代码安全
- 自动安全扫描
- 依赖漏洞检查
- 代码混淆和压缩
- 移除调试信息

## 📊 监控和通知

### 构建状态
- GitHub Actions 状态徽章
- 实时构建日志
- 失败通知

### 测试报告
- 自动生成测试摘要
- 在 Job Summary 中显示
- 上传详细报告作为 artifact

### 安全报告
- Trivy 漏洞扫描
- SARIF 结果上传
- GitHub Security 集成

## 🔄 扩展功能

### 计划中的改进
- [ ] 自动化版本号管理
- [ ] 多环境部署支持
- [ ] 性能测试集成
- [ ] 自动化发布说明生成
- [ ] 集成测试支持

### 可选集成
- **代码覆盖率**: Codecov/Coveralls
- **代码质量**: SonarQube
- **性能监控**: Firebase Performance
- **崩溃报告**: Firebase Crashlytics

## 📚 相关文档

- [开发指南](DEVELOPMENT.md)
- [架构指南](ARCHITECTURE.md)
- [脚本说明](scripts/README.md)
- [GitHub Secrets 设置](scripts/setup-github-secrets.sh)

## 🆘 获取帮助

如果遇到问题：

1. **检查工作流程日志**: GitHub Actions 页面
2. **查看测试摘要**: Job Summary 或下载 artifact
3. **验证配置**: 检查 Secrets 和权限设置
4. **参考文档**: 查看相关文档和脚本
5. **提交 Issue**: 在 GitHub 仓库中报告问题

## 📝 维护建议

### 定期任务
- [ ] 更新依赖版本
- [ ] 检查安全漏洞
- [ ] 清理过期 artifacts
- [ ] 审查工作流程性能

### 版本升级
- [ ] 监控 GitHub Actions 更新
- [ ] 测试新功能和改进
- [ ] 更新文档和脚本
- [ ] 验证向后兼容性