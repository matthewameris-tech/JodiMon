@echo off
setlocal EnableDelayedExpansion

echo ====================================================
echo  Compiling JodiMon - Turn-Based Battle Game...
echo ====================================================

if not exist bin mkdir bin

set "JAVAC_BIN=javac"
if exist "%JAVA_HOME%\bin\javac.exe" (
    set "JAVAC_BIN=%JAVA_HOME%\bin\javac.exe"
) else if exist "C:\Program Files\Java\jdk-21\bin\javac.exe" (
    set "JAVAC_BIN=C:\Program Files\Java\jdk-21\bin\javac.exe"
)

"%JAVAC_BIN%" -d bin -sourcepath src src\com\monsterbattle\Main.java src\com\monsterbattle\GameTest.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilation successful! Class files are in 'bin/'.
    echo Run 'run.bat' to start the game!
) else (
    echo.
    echo Compilation failed! Please check your JDK installation.
)
