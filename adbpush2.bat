@echo off
:: Encoding of this file is Shift-JIS
:: adb pushと同じく元ファイルと送り先ディレクトリを指定するパターン
:: --------------------------------------------------------------------------------------
setlocal
:: Android端末で一時的に保存するディレクトリ
set tempdir=/sdcard/temp/
:: ファイル名変換リスト
set list0=temp_list.txt
set list=%~d0%~p0%list0%
::
echo Shift-JIS> %list%
echo dest %2>> %list%
::
if exist %1 goto fileexist
echo %1がありません
goto end
:fileexist
echo %1を転送
adb push %1 %tempdir%file1
echo file1 %~n1%~x1>> %list%
echo ファイル名を転送
adb push %list% %tempdir%
adb shell am broadcast -a jp.ind.knhnnh.adbpushex.KICK_MOVE -d file://%tempdir%%list0%
:end
endlocal
