@echo off
rem ======================================================================
rem windows startup script
rem
rem author: mr.hou
rem date: 2020-07-02
rem ======================================================================
rem %1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",0)(window.close)&&exit
set APP_NAME=${project.build.finalName}
tasklist | find  "%APP_NAME%.exe"
if "%errorlevel%"=="1" (goto f) else (goto e)
:f
set PATH=..\jre\bin
set javaw=..\jre\bin\javaw.exe
set APP_JAR=${project.build.finalName}.jar
set LOG_IMPL_FILE=log.xml
set LOGGING_CONFIG=
if exist ../config/%LOG_IMPL_FILE% (
    set LOGGING_CONFIG=-Dlogging.config=../config/%LOGGING_CONFIG%
)
set CONFIG= -Dlogging.path=../logs %LOGGING_CONFIG% -Dspring.config.location=../config/
set APP_EXE="%path%\%APP_NAME%.exe"
if not exist  %APP_EXE% (
  copy %javaw% %APP_EXE%
)

echo To start the %APP_NAME%...
start   %path%\%APP_NAME%  -Xms512m -Xmx512m -server %CONFIG% -jar ../lib/%APP_JAR% && echo The %APP_NAME% has started || echo start failed
goto exit


:e
rem start ${project.chrome.path} --app="${project.index.url}"
echo %APP_NAME%.exe has started

:exit
pause
rem exit

