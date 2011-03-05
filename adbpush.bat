@echo off
:: Encoding of this file is Shift-JIS
:: 渡された複数のファイルを全て固定のディレクトリに送るパターン
:: ショートカットをデスクトップに置けばD&Dで送れます
:: 起動時パラメータでdestを設定できるようにうまく書き換えるとどこにでも送れるようになります
:: --------------------------------------------------------------------------------------
setlocal
set /a count=0
:: Android端末で一時的に保存するディレクトリ
set tempdir=/sdcard/temp/
:: Android端末の最終送り先ディレクトリ
set dest=/sdcard/download/
:: ファイル名変換リスト
set list0=temp_list.txt
set list=%~d0%~p0%list0%
::
echo Shift-JIS> %list%
echo dest %dest%>> %list%
:loop
if exist %1 goto fileexist
echo %1がありません
goto next
:fileexist
set /a count+=1
echo %1を転送
adb push %1 %tempdir%file%count%
echo file%count% %~n1%~x1>> %list%
:next
shift
if exist %1 goto loop
if %count%==0 goto end
echo ファイル名を転送
adb push %list% %tempdir%
adb shell am broadcast -a jp.ind.knhnnh.adbpushex.KICK_MOVE -d file://%tempdir%%list0%
:end
endlocal
pause
