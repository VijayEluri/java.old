REM @echo off

pushd %~dp0..
set basePath=%CD%
cd build

setlocal  enabledelayedexpansion
set LIB_PATH=..\lib
set MY_JARS=.
FOR /R %LIB_PATH% %%I in (*.jar) DO set MY_JARS=!MY_JARS!;%LIB_PATH%\%%~nxI
javaw -cp %MY_JARS%  %1

endlocal
popd

@echo on
