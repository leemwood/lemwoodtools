#!/bin/bash

# 柠枺工具箱 - 本地测试脚本
# 用于在本地运行测试，检测潜在问题

echo "🧪 开始运行柠枺工具箱测试..."

# 检查Gradle是否可用
if [ ! -f "./gradlew" ]; then
    echo "❌ 错误: gradlew 文件不存在"
    exit 1
fi

# 使gradlew可执行
chmod +x ./gradlew

echo "📋 运行Lint检查..."
./gradlew lintDebug

if [ $? -eq 0 ]; then
    echo "✅ Lint检查通过"
else
    echo "⚠️  Lint检查发现问题，请查看报告"
fi

echo ""
echo "🧪 运行单元测试..."
./gradlew testDebugUnitTest

if [ $? -eq 0 ]; then
    echo "✅ 单元测试通过"
else
    echo "❌ 单元测试失败"
    exit 1
fi

echo ""
echo "🔍 检查测试覆盖率..."
./gradlew jacocoTestReport

echo ""
echo "📊 测试报告位置:"
echo "  - Lint报告: app/build/reports/lint-results-debug.html"
echo "  - 测试报告: app/build/reports/tests/testDebugUnitTest/index.html"
echo "  - 覆盖率报告: app/build/reports/jacoco/jacocoTestReport/html/index.html"

echo ""
echo "🎉 所有测试完成!"