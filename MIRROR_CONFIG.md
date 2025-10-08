# Gradle 镜像源配置总结

## 配置变更

本项目已配置使用腾讯云镜像源，以提高依赖下载速度和构建稳定性。

### 修改的文件

1. **settings.gradle** - 主项目设置文件
   - 添加了清华大学镜像源
   - 优先级：镜像源 > 官方源

2. **build.gradle (根目录)** - 项目构建配置
   - 配置了构建脚本仓库使用镜像源

3. **app/build.gradle** - 应用模块配置
   - 配置了依赖仓库使用镜像源

4. **gradle/wrapper/gradle-wrapper.properties** - Gradle包装器配置
   - 修改了Gradle分发URL使用清华大学镜像

5. **gradle.properties** - Gradle属性配置
   - 启用了并行构建、构建缓存和守护进程
   - 配置了网络超时时间

6. **gradle/init.gradle** - 全局初始化脚本
   - 为所有项目配置了镜像源

## 镜像源地址

- **Google仓库**: https://mirrors.cloud.tencent.com/google/
- **Maven Central仓库**: https://mirrors.cloud.tencent.com/maven-central/
- **Gradle插件仓库**: https://mirrors.cloud.tencent.com/gradle/gradle-plugin-portal/
- **Gradle分发**: https://mirrors.cloud.tencent.com/gradle/gradle-8.14.2-bin.zip

## 构建命令

```bash
# 清理项目
./gradlew clean

# 构建项目
./gradlew build

# 运行测试
./gradlew test

# 构建APK
./gradlew assembleDebug
```

## 注意事项

1. 如果镜像源不可用，构建系统会自动回退到官方源
2. 首次构建可能需要下载依赖，后续构建会利用缓存加速
3. 如果遇到网络问题，可以尝试切换到其他镜像源

## 其他可用镜像源

如果腾讯云镜像不可用，可以考虑：
- 阿里云: https://maven.aliyun.com/repository/
- 华为云: https://repo.huaweicloud.com/repository/
- 清华大学: https://mirrors.tuna.tsinghua.edu.cn/