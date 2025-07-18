#!/bin/bash

# 柠枺工具箱 - 本地构建脚本
# 用于快速构建和测试应用

set -e

echo "🍋 柠枺工具箱 - 本地构建脚本"
echo "================================"

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "❌ 错误: 未找到Java环境，请安装JDK 17或更高版本"
    exit 1
fi

# 检查Android SDK
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  警告: 未设置ANDROID_HOME环境变量"
fi

# 清理项目
echo "🧹 清理项目..."
./gradlew clean

# 运行代码检查
echo "🔍 运行代码检查..."
./gradlew lintDebug

# 运行单元测试
echo "🧪 运行单元测试..."
./gradlew testDebugUnitTest

# 构建Debug版本
echo "🔨 构建Debug版本..."
./gradlew assembleDebug

# 检查APK是否生成成功
DEBUG_APK="app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$DEBUG_APK" ]; then
    echo "✅ Debug APK构建成功: $DEBUG_APK"
    
    # 显示APK信息
    APK_SIZE=$(du -h "$DEBUG_APK" | cut -f1)
    echo "📦 APK大小: $APK_SIZE"
else
    echo "❌ Debug APK构建失败"
    exit 1
fi

# 询问是否构建Release版本
read -p "🤔 是否构建Release版本? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🔨 构建Release版本..."
    
    # 检查签名配置
    if [ -f "app/release-key.keystore" ] && [ ! -z "$SIGNING_KEY_ALIAS" ]; then
        ./gradlew assembleRelease
        
        RELEASE_APK="app/build/outputs/apk/release/app-release.apk"
        if [ -f "$RELEASE_APK" ]; then
            echo "✅ Release APK构建成功: $RELEASE_APK"
            APK_SIZE=$(du -h "$RELEASE_APK" | cut -f1)
            echo "📦 APK大小: $APK_SIZE"
        else
            echo "❌ Release APK构建失败"
        fi
    else
        echo "⚠️  警告: 未找到签名配置，跳过Release构建"
        echo "请参考 .github/SECRETS.md 配置签名"
    fi
fi

echo ""
echo "🎉 构建完成!"
echo ""
echo "📱 安装到设备:"
echo "   adb install $DEBUG_APK"
echo ""
echo "📊 查看构建报告:"
echo "   - Lint报告: app/build/reports/lint-results-debug.html"
echo "   - 测试报告: app/build/reports/tests/testDebugUnitTest/index.html"