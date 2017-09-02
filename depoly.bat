::@echo off
::gradle clean war
call gradlecleanwar.bat
echo start shutdown tomcat
call "%CATALINA_HOME%\bin\shutdown.bat"
echo call success
echo "%CATALINA_HOME%\webapps"
::pause
rd /s /Q "%CATALINA_HOME%\webapps"
echo delete success
::pause
md "%CATALINA_HOME%\webapps"
echo mdsuccess
set "CURRENT_DIR=%cd%"
echo "%CURRENT_DIR%\target\libs\springinit-1.0-SNAPSHOT.war"
move "%CURRENT_DIR%\target\libs\springinit-1.0-SNAPSHOT.war" "%CATALINA_HOME%\webapps\"
echo move success
::pause
call "%CATALINA_HOME%\bin\startup.bat"

