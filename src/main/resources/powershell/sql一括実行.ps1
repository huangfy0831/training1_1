$list = Get-ChildItem -Path E:\�����p����\DDL\*\*.sql

foreach($item in $list) {
  sqlcmd -S C010362914\SQLEXPRESS -U azuser -P azpass -i $item
}