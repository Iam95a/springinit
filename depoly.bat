::@echo off
::gradle clean war
call gradlecleanwar.bat
echo start shutdown tomcat
call E:\tomcat\tomcat8\bin\shutdown.bat
echo call success
rd /s /Q e:\tomcat\tomcat8\webapps
echo delete success
md E:\tomcat\tomcat8\webapps
echo mdsuccess
move E:\git\springinit\target\libs\springinit-1.0-SNAPSHOT.war E:\tomcat\tomcat8\webapps\
echo move success
call E:\tomcat\tomcat8\bin\startup.bat

