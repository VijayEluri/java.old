REM @echo off

pushd %~dp0..
set basePath=%CD%

setlocal  enabledelayedexpansion
set LIB_PATH=lib
set MY_JARS=.
@REM FOR /R %LIB_PATH% %%I in (*.jar) DO set MY_JARS=!MY_JARS!;%LIB_PATH%\%%~nxI
FOR /R %LIB_PATH% %%I in (*.jar) DO set MY_JARS=!MY_JARS!;%%~fI
set prog=%1
shift

javaw -cp %MY_JARS%;build  %prog% %*

endlocal
popd

@echo on
