@echo off
:: Encoding of this file is Shift-JIS
:: adb push�Ɠ��������t�@�C���Ƒ����f�B���N�g�����w�肷��p�^�[��
:: --------------------------------------------------------------------------------------
setlocal
:: Android�[���ňꎞ�I�ɕۑ�����f�B���N�g��
set tempdir=/sdcard/temp/
:: �t�@�C�����ϊ����X�g
set list0=temp_list.txt
set list=%~d0%~p0%list0%
::
echo Shift-JIS> %list%
echo dest %2>> %list%
::
if exist %1 goto fileexist
echo %1������܂���
goto end
:fileexist
echo %1��]��
adb push %1 %tempdir%file1
echo file1 %~n1%~x1>> %list%
echo �t�@�C������]��
adb push %list% %tempdir%
adb shell am broadcast -a jp.ind.knhnnh.adbpushex.KICK_MOVE -d file://%tempdir%%list0%
:end
endlocal
