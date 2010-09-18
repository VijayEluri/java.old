REM @echo off
pushd %~dp0

set APACHE_PATH=d:\Java\Apache
set APACHE_JAR=%APACHE_PATH%\Log4J\log4j-1.2.15.jar;

move dir.txt dir_bak.txt
java -cp %APACHE_JAR%;.  org.fanhongtao.tools.dir.Dir C:\ D:\ E:\ F:\ > dir.txt

popd
pause

