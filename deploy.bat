:: @echo off

set servertomcatdir="D:\Programs\Tomcat\apache-tomcat-7.0.29"

del %servertomcatdir%\webapps\wsiadamy-webapp-war.war /f /q
rmdir %servertomcatdir%\webapps\wsiadamy-webapp-war\ /S /Q

xcopy wsiadamy-webapp\wsiadamy-webapp-war\target\wsiadamy-webapp-war.war %servertomcatdir%\webapps\wsiadamy-webapp-war.war /s /h /y

set currdir=%cd%
cd %servertomcatdir%\bin
start catalina jpda start
cd %currdir%

set currdir=
set servertomcatdir=
:: @echo on
