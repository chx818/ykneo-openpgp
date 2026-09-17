@echo off
rem ===========================================================================
rem  Remove the 9 probes from the card (frees the space back).
rem  Run this after the diagnosis.
rem ===========================================================================
setlocal
set READER=Virtual PCD
set GP=gp.exe

for %%N in (00 01 02 03 04 05 06 07 08) do (
  %GP% -r "%READER%" --delete A00000061700FF%%N01
  %GP% -r "%READER%" --delete A00000061700FF%%N
)

%GP% -r "%READER%" -l
pause
