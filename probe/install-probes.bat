@echo off
rem ===========================================================================
rem  Install the 9 crypto probes one by one.
rem  A probe that installs OK   => the card supports that feature.
rem  A probe that fails (6F00)  => the card does NOT support it.
rem
rem  Usage: put this file next to the extracted probe-pN-*.cap files, run it.
rem  (kept pure ASCII on purpose: Chinese text shows as garbage in cmd.exe)
rem ===========================================================================
setlocal
set READER=Virtual PCD
set GP=gp.exe

dir /b probe-p*.cap
echo.

for %%F in (probe-p0-null probe-p1-rsa1024crt probe-p2-rsa2048crt probe-p3-rsa2048 probe-p4-des3sm probe-p5-digests probe-p6-rng probe-p7-rsa2048gen probe-p8-ykneolike) do (
  echo.
  echo ================== %%F ==================
  %GP% -r "%READER%" --install %%F.cap
)

echo.
echo ================== installed applets / packages ==================
%GP% -r "%READER%" -l
pause
