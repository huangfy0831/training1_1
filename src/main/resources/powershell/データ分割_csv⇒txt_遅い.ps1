Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + "作成開始")
Write-Host("")

$fileUnit = 10000
$sqlFile = ""

# Excelを起動する
$excel = New-Object -ComObject Excel.Application

# ファイルからデータ情報を取得する
$Current = Split-Path $myInvocation.MyCommand.path
$excel.Workbooks.Open("$Current\test.csv") | %{
    # シートを読み込み
    $_.Worksheets | %{
        for($i=0; $i -lt $_.UsedRange.Cells.Count;$i++) {
            if ($i % $fileUnit -eq 0) {
                $sqlFile = "act_log" + ($i / $fileUnit + 1) + ".sql"
                Write-Output "insert into act_log(seq_number,mst_customer_code,timestamp,user_name,device_ip,operation_target,operation_name,resource_name,relate_resource1,relate_resource2,action_log,action_kind) value" | Out-File -FilePath $Current\$sqlFile -Append
            }

            # データ書込み
            Write-Output $_.UsedRange.Cells[$i + 1].Text | Out-File -FilePath $Current\$sqlFile -Append
        }
    }
}

# 終了
Write-Host("")
Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + " 作成完了")