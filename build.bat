@echo off
setlocal enabledelayedexpansion

REM 柠枺工具箱 - Windows本地构建脚本
REM 用于快速构建和测试应用

echo 🍋 柠枺工具箱 - 本地构建脚本
echo ================================

REM 检查Java环境
java -version >nul 2>&1
if errorlevel 1 (
    echo ❌ 错误: 未找到Java环境，请安装JDK 17或更高版本
    pause
    exit /b 1
)

REM 检查Android SDK
if "%ANDROID_HOME%"=="" (
    echo ⚠️  警告: 未设置ANDROID_HOME环境变量
)

REM 清理项目
echo 🧹 清理项目...
call gradlew.bat clean
if errorlevel 1 goto :error

REM 运行代码检查
echo 🔍 运行代码检查...
call gradlew.bat lintDebug
if errorlevel 1 goto :error

REM 运行单元测试
echo 🧪 运行单元测试...
call gradlew.bat testDebugUnitTest
if errorlevel 1 goto :error

REM 构建Debug版本
echo 🔨 构建Debug版本...
call gradlew.bat assembleDebug
if errorlevel 1 goto :error

REM 检查APK是否生成成功
set DEBUG_APK=app\build\outputs\apk\debug\app-debug.apk
if exist "%DEBUG_APK%" (
    echo ✅ Debug APK构建成功: %DEBUG_APK%
    
    REM 显示APK信息
    for %%A in ("%DEBUG_APK%") do (
        set APK_SIZE=%%~zA
        set /a APK_SIZE_MB=!APK_SIZE!/1024/1024
        echo 📦 APK大小: !APK_SIZE_MB! MB
    )
) else (
    echo ❌ Debug APK构建失败
    goto :error
)

REM 询问是否构建Release版本
set /p REPLY="🤔 是否构建Release版本? (y/N): "
if /i "%REPLY%"=="y" (
    echo 🔨 构建Release版本...
    
    REM 检查签名配置
    if exist "app\release-key.keystore" (
        if not "%SIGNING_KEY_ALIAS%"=="" (
            call gradlew.bat assembleRelease
            if errorlevel 1 goto :error
            
            set RELEASE_APK=app\build\outputs\apk\release\app-release.apk
            if exist "!RELEASE_APK!" (
                echo ✅ Release APK构建成功: !RELEASE_APK!
                for %%A in ("!RELEASE_APK!") do (
                    set APK_SIZE=%%~zA
                    set /a APK_SIZE_MB=!APK_SIZE!/1024/1024
                    echo 📦 APK大小: !APK_SIZE_MB! MB
                )
            ) else (
                echo ❌ Release APK构建失败
            )
        ) else (
            echo ⚠️  警告: 未设置签名环境变量，跳过Release构建
            echo 请参考 .github\SECRETS.md 配置签名
        )
    ) else (
        echo ⚠️  警告: 未找到签名文件，跳过Release构建
        echo 请参考 .github\SECRETS.md 配置签名
    )
)

echo.
echo 🎉 构建完成!
echo.
echo 📱 安装到设备:
echo    adb install %DEBUG_APK%
echo.
echo 📊 查看构建报告:
echo    - Lint报告: app\build\reports\lint-results-debug.html
echo    - 测试报告: app\build\reports\tests\testDebugUnitTest\index.html
echo.
pause
exit /b 0

:error
echo.
echo ❌ 构建过程中出现错误!
pause
exit /b 1