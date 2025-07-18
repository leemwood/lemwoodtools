# GitHub Actions Secrets 配置说明

为了让CI/CD流水线正常工作，需要在GitHub仓库中配置以下Secrets：

## 必需的Secrets

### 1. 签名相关 (用于Release构建)
- `SIGNING_KEY_ALIAS`: 签名密钥别名
- `SIGNING_KEY_PASSWORD`: 签名密钥密码
- `SIGNING_STORE_PASSWORD`: 密钥库密码
- `SIGNING_KEYSTORE`: Base64编码的keystore文件内容

### 2. GitHub Token (用于创建Release)
- `GITHUB_TOKEN`: GitHub Personal Access Token (自动提供，无需手动配置)

## 配置步骤

### 1. 生成签名密钥
```bash
# 生成keystore文件
keytool -genkey -v -keystore release-key.keystore -alias lemwood-key -keyalg RSA -keysize 2048 -validity 10000

# 将keystore文件转换为Base64
# Windows PowerShell:
[Convert]::ToBase64String([IO.File]::ReadAllBytes("release-key.keystore"))

# Linux/macOS:
base64 -i release-key.keystore
```

### 2. 在GitHub仓库中添加Secrets
1. 进入GitHub仓库页面
2. 点击 Settings -> Secrets and variables -> Actions
3. 点击 "New repository secret"
4. 添加上述所有必需的secrets

### 3. 可选配置
如果不配置签名相关的secrets，CI仍然可以正常运行，但只会生成debug版本的APK。

## 注意事项
- 密钥库文件应该安全保存，不要提交到代码仓库
- 建议定期更新签名密钥
- 确保密钥库密码足够复杂且安全