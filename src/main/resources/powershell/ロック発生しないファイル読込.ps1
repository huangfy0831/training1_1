# テーブル一覧を取得する
$filePath = "D:\if\infile\APL100\bk\control.txt"

# FileAccess:このstreamに許可する読み書きアクセス
# FileShare:ファイルを開いた後に同一ファイルに対して許可する操作（下記の例は別のプロセスで読み書きと削除全部許可する）
$sr = [System.IO.StreamReader]::new([System.IO.FileStream]::new($filePath,[System.IO.FileMode]::Open,[System.IO.FileAccess]::Read,[System.IO.FileShare]::ReadWrite + [System.IO.FileShare]::Delete))
$line1 = $sr.ReadLine()
$remaining = $sr.ReadToEnd()
$sr.Close()

Write-Host("line1:" + $line1.Trim())
Write-Host("remaining:" + $remaining.Trim())
