Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + " スケジュール実行開始")
Write-Host("")

# スケジュールを保持する変数
$schedule = New-Object "object[,]" 1000,2
$i = 0
$j = 0

# Excelを起動する
$excel = New-Object -ComObject Excel.Application

# エクセルファイルからスケジュール一覧を取得する
$Current = Split-Path $myInvocation.MyCommand.path
$excel.Workbooks.Open("$Current\スケジュール.xlsx") | %{
    # シートを読み込み
    $_.Worksheets | %{
        # 行を読み込む
        $_.UsedRange.Rows | %{
            # 列を読み込む
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

# 1秒づつループしてスケジュールにより、.gzファイルとコントロールファイルをMOVE
$row = 0
$time = 0
while ($schedule[$row, 0] -ne $null) {
    Sleep 1
    $time++
    if (($schedule[$row,0] - $time) -lt 0) {
        Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + " 移動" + $schedule[$row,1] + " : " + $schedule[$row,0] + "秒後")
        Move-Item ("D:\if\infile\" + $schedule[$row,1] + "\bk\*.*") ("D:\if\infile\" + $schedule[$row,1] + "\")
        $row++
    }
}

# Excelを終了する
$excel.Quit()

# 終了
Write-Host("")
Write-Host((Get-Date -format "yyyy/MM/dd HH:mm:ss") + " スケジュール実行完了、バッチはまだ実行中です")