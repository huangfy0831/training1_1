Write-Host("���s�J�n")
Write-Host("")

# �f�B���N�g�����R�s�[
$path = "C:\Users\cac31916\Desktop\Embulk_Thread�m�F\�\�[�X\test\"
Copy-Item ($path + "*") $path.Replace("\test","\testNew") -Force

# ���[�v���ăt�@�C���S�̂��擾����
$list = Get-ChildItem -Path ($path + "*\*.xml")
foreach($item in $list) {
    # �t�@�C����Ǎ�
    $filename = $item.FullName
    $file = New-Object System.IO.StreamReader($fileName, [System.Text.Encoding]::GetEncoding("utf-8"))

    # testNew�t�H���_�ɐV�����t�@�C�������
    $newFilename = $item.FullName.Replace("\test","\testNew")

    # �V�����t�@�C���ɏ�������
    New-Item $newFilename -ItemType file
    $newFile = New-Object System.IO.StreamWriter($newFilename, $false, [System.Text.Encoding]::GetEncoding("utf-8"))

    while (($line = $file.ReadLine()) -ne $null) {
        # ��̓I�ȕύX���W�b�N
        if ($line.Contains("<include") -and $line.Contains("*.class")) {
            # ���e�ύX
            $newFile.WriteLine($line.Replace("*.class",".class"))
            # ��s�ǉ�
            $newFile.WriteLine($line.Replace("*.class","Flow*.class"))
        } else {
            $newFile.WriteLine($line)
        }
    }
    $file.Close()
    $newFile.Close()
}

# �I��
Write-Host("")
Write-Host("���s�I��")