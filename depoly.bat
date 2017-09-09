::@echo off
::gradle clean war
::执行gradle clean war命令
call gradlecleanwar.bat
::执行tomcat关闭脚本
echo start shutdown tomcat
call "%CATALINA_HOME%\bin\shutdown.bat"
echo call success
echo "%CATALINA_HOME%\webapps"
::pause
::删除tomcat的webapps目录
rd /s /Q "%CATALINA_HOME%\webapps"
echo delete success
::pause
::创建tomcat的webapps目录
md "%CATALINA_HOME%\webapps"
echo mdsuccess
set "CURRENT_DIR=%cd%"
echo "%CURRENT_DIR%\target\libs\springinit-1.0-SNAPSHOT.war"
::将编译出的war包放到tomcat相关目录下
move "%CURRENT_DIR%\target\libs\springinit-1.0-SNAPSHOT.war" "%CATALINA_HOME%\webapps\"
::将war包重命名成ROOT.war
ren "%CATALINA_HOME%\webapps\springinit-1.0-SNAPSHOT.war" ROOT.war
echo move success
::pause
::启动tomcat
call "%CATALINA_HOME%\bin\startup.bat"

