@echo off
echo Building ATV Launcher...
cd /d "%~dp0"
call gradlew.bat assembleDebug
pause
