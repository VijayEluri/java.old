REM @echo off
pushd %~dp0..\build

set SWT_PATH="d:\Java\Eclipse\SWT&JFace"
set SWT_JAR=%SWT_PATH%\org.eclipse.swt.win32.win32.x86_3.4.1.v3449c.jar;

set APACHE_PATH=d:\Java\Apache
set APACHE_JAR=%APACHE_PATH%\Log4J\log4j-1.2.15.jar;

javaw -cp %SWT_JAR%;%APACHE_JAR%  %1

popd
@echo on
