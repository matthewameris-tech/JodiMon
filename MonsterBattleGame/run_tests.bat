@echo off
setlocal EnableDelayedExpansion

set "JAVA_BIN=java"
if exist "%JAVA_HOME%\bin\java.exe" (
    set "JAVA_BIN=%JAVA_HOME%\bin\java.exe"
) else if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
    set "JAVA_BIN=C:\Program Files\Java\jdk-21\bin\java.exe"
)

if not exist "bin\com\monsterbattle\GameTest.class" (
    call build.bat
)

"%JAVA_BIN%" -cp bin com.monsterbattle.GameTest
