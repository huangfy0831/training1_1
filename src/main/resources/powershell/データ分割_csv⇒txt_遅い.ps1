Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + "�쐬�J�n")
Write-Host("")

$fileUnit = 10000
$sqlFile = ""

# Excel���N������
$excel = New-Object -ComObject Excel.Application

# �t�@�C������f�[�^�����擾����
$Current = Split-Path $myInvocation.MyCommand.path
$excel.Workbooks.Open("$Current\test.csv") | %{
    # �V�[�g��ǂݍ���
    $_.Worksheets | %{
        for($i=0; $i -lt $_.UsedRange.Cells.Count;$i++) {
            if ($i % $fileUnit -eq 0) {
                $sqlFile = "act_log" + ($i / $fileUnit + 1) + ".sql"
                Write-Output "insert into act_log(seq_number,mst_customer_code,timestamp,user_name,device_ip,operation_target,operation_name,resource_name,relate_resource1,relate_resource2,action_log,action_kind) value" | Out-File -FilePath $Current\$sqlFile -Append
            }

            # �f�[�^������
            Write-Output $_.UsedRange.Cells[$i + 1].Text | Out-File -FilePath $Current\$sqlFile -Append
        }
    }
}

# �I��
Write-Host("")
Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + " �쐬����")