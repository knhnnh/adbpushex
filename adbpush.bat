@echo off
:: Encoding of this file is Shift-JIS
:: �n���ꂽ�����̃t�@�C����S�ČŒ�̃f�B���N�g���ɑ���p�^�[��
:: �V���[�g�J�b�g���f�X�N�g�b�v�ɒu����D&D�ő���܂�
:: �N�����p�����[�^��dest��ݒ�ł���悤�ɂ��܂�����������Ƃǂ��ɂł������悤�ɂȂ�܂�
:: --------------------------------------------------------------------------------------
setlocal
set /a count=0
:: Android�[���ňꎞ�I�ɕۑ�����f�B���N�g��
set tempdir=/sdcard/temp/
:: Android�[���̍ŏI�����f�B���N�g��
set dest=/sdcard/download/
:: �t�@�C�����ϊ����X�g
set list0=temp_list.txt
set list=%~d0%~p0%list0%
::
echo Shift-JIS> %list%
echo dest %dest%>> %list%
:loop
if exist %1 goto fileexist
echo %1������܂���
goto next
:fileexist
set /a count+=1
echo %1��]��
adb push %1 %tempdir%file%count%
echo file%count% %~n1%~x1>> %list%
:next
shift
if exist %1 goto loop
if %count%==0 goto end
echo �t�@�C������]��
adb push %list% %tempdir%
adb shell am broadcast -a jp.ind.knhnnh.adbpushex.KICK_MOVE -d file://%tempdir%%list0%
:end
endlocal
pause
