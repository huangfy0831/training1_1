Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + " �X�P�W���[�����s�J�n")
Write-Host("")

# �X�P�W���[����ێ�����ϐ�
$schedule = New-Object "object[,]" 1000,2
$i = 0
$j = 0

# Excel���N������
$excel = New-Object -ComObject Excel.Application

# �G�N�Z���t�@�C������X�P�W���[���ꗗ���擾����
$Current = Split-Path $myInvocation.MyCommand.path
$excel.Workbooks.Open("$Current\�X�P�W���[��.xlsx") | %{
    # �V�[�g��ǂݍ���
    $_.Worksheets | %{
        # �s��ǂݍ���
        $_.UsedRange.Rows | %{
            # ���ǂݍ���
            $_.Columns | %{
                $schedule[$i,$j] = $_.Text
                if ($j -eq 0) {
                    $j++
                } else {
                    $j = 0
                    $i++
                }
            }
        }
    }
}

# 1�b�Â��[�v���ăX�P�W���[���ɂ��A.gz�t�@�C���ƃR���g���[���t�@�C����MOVE
$row = 0
$time = 0
while ($schedule[$row, 0] -ne $null) {
    Sleep 1
    $time++
    if (($schedule[$row,0] - $time) -lt 0) {
        Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + " �ړ�" + $schedule[$row,1] + " : " + $schedule[$row,0] + "�b��")
        Move-Item ("D:\if\infile\" + $schedule[$row,1] + "\bk\*.*") ("D:\if\infile\" + $schedule[$row,1] + "\")
        $row++
    }
}

# Excel���I������
$excel.Quit()

# �I��
Write-Host("")
Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + " �X�P�W���[�����s�����A�o�b�`�͂܂����s���ł�")