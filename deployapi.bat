:: @echo off

set servertomcatdir="D:\Programs\Tomcat\apache-tomcat-7.0.29"

del %servertomcatdir%\webapps\wsiadamy-api-war.war /f /q
rmdir %servertomcatdir%\webapps\wsiadamy-api-war\ /S /Q

xcopy wsiadamy-api\wsiadamy-api-war\target\wsiadamy-api-war.war %servertomcatdir%\webapps\wsiadamy-api-war.war /s /h /y

set currdir=%cd%
cd %servertomcatdir%\bin
start catalina jpda start
cd %currdir%

set currdir=
set servertomcatdir=
:: @echo on
