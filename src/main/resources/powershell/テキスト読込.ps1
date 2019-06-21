Write-Host("実行開始")
Write-Host("")

# ディレクトリをコピー
$path = "C:\Users\cac31916\Desktop\Embulk_Thread確認\ソース\test\"
Copy-Item ($path + "*") $path.Replace("\test","\testNew") -Force

# ループしてファイル全体を取得する
$list = Get-ChildItem -Path ($path + "*\*.xml")
foreach($item in $list) {
    # ファイルを読込
    $filename = $item.FullName
    $file = New-Object System.IO.StreamReader($fileName, [System.Text.Encoding]::GetEncoding("utf-8"))

    # testNewフォルダに新しいファイルを作る
    $newFilename = $item.FullName.Replace("\test","\testNew")

    # 新しいファイルに書き込み
    New-Item $newFilename -ItemType file
    $newFile = New-Object System.IO.StreamWriter($newFilename, $false, [System.Text.Encoding]::GetEncoding("utf-8"))

    while (($line = $file.ReadLine()) -ne $null) {
        # 具体的な変更ロジック
        if ($line.Contains("<include") -and $line.Contains("*.class")) {
            # 内容変更
            $newFile.WriteLine($line.Replace("*.class",".class"))
            # 一行追加
            $newFile.WriteLine($line.Replace("*.class","Flow*.class"))
        } else {
            $newFile.WriteLine($line)
        }
    }
    $file.Close()
    $newFile.Close()
}

# 終了
Write-Host("")
Write-Host("実行終了")