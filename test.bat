@echo off
chcp 65001 >nul
REM LemwoodTools - Windows Test Script
REM Run tests to detect potential issues

echo Starting LemwoodTools tests...

REM Check if gradlew.bat exists
if not exist "gradlew.bat" (
    echo Error: gradlew.bat file not found
    exit /b 1
)

echo Running Lint check...
call gradlew.bat lintDebug

if %ERRORLEVEL% equ 0 (
    echo Lint check passed
) else (
    echo Lint check found issues, please check the report
)

echo.
echo Running unit tests...
call gradlew.bat testDebugUnitTest

if %ERRORLEVEL% equ 0 (
    echo Unit tests passed
) else (
    echo Unit tests failed
    exit /b 1
)

echo.
echo Checking test coverage...
call gradlew.bat jacocoTestReport

echo.
echo Test report locations:
echo   - Lint report: app\build\reports\lint-results-debug.html
echo   - Test report: app\build\reports\tests\testDebugUnitTest\index.html
echo   - Coverage report: app\build\reports\jacoco\jacocoTestReport\html\index.html

echo.
echo All tests completed!
pause