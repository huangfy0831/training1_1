# �e�[�u���ꗗ���擾����
$filePath = "D:\if\infile\APL100\bk\control.txt"

# FileAccess:����stream�ɋ�����ǂݏ����A�N�Z�X
# FileShare:�t�@�C�����J������ɓ���t�@�C���ɑ΂��ċ����鑀��i���L�̗�͕ʂ̃v���Z�X�œǂݏ����ƍ폜�S��������j
$sr = [System.IO.StreamReader]::new([System.IO.FileStream]::new($filePath,[System.IO.FileMode]::Open,[System.IO.FileAccess]::Read,[System.IO.FileShare]::ReadWrite + [System.IO.FileShare]::Delete))
$line1 = $sr.ReadLine()
$remaining = $sr.ReadToEnd()
$sr.Close()

Write-Host("line1:" + $line1.Trim())
Write-Host("remaining:" + $remaining.Trim())
