#-------------------------------------------------------
# �@�\�� : �e�L�X�g�t�@�C������
# �@�\�T�v�F�p�����[�^�[�Ŏw�肳�ꂽ�t�@�C����
#   �����������Ƀt�@�C����������B
# ------------------------------------------------------
#  �ύX����
# ------------------------------------------------------
# �쐬: 2017/04/01 Molly 
# �X�V: 2017/99/99
#-------------------------------------------------------
#----------------------------
# �p�����[�^�[
# $pFromFile : ��������t�@�C��
# $pSplitCnt : �������錏��
# $enc : EncodeName
#----------------------------
Param(
    [string]$pFromFile	,
    [int]$pSplitCnt ,
    [string]$enc
)
#----------------------------
# �֐��F����+���b�Z�[�W�o��
#----------------------------
function putMessage($mText){
    Write-Host(([System.DateTime]::Now).ToString("yyyy-MM-dd HH:mm:ss")+$mText)
}
 
#-- Stat Message
putMessage(" FileSplit Start" )
#-- ���A�R�[�h=0
$retcode=0
try{
    $rdCnt = 0    #-- Read Count
    $allCnt = 0   #-- All Count
    $unitCnt=1    #-- File Unit Count
    #----------------------------
    # �p�����[�^�[�`�F�b�N
    # $pFromFile : ��������t�@�C��
    #----------------------------
    #-- InFile Exist 
    if( $pFromFile -eq "" ) {
        putMessage(" ### InFile Not Prameter ###" )
        $retcode=9
    }elseif( Test-Path $pFromFile ) {
    }else{
        putMessage(" ### InFile Not Found ###" )
        $retcode=9
    }
    #-- SplitCnt < 10000
    if( $pSplitCnt -cle 10000 ) {
        $pSplitCnt=10000
    }
    putMessage(" SplitCount="+$pSplitCnt )
    #-- Encode = ""
    if( $enc -eq "" ) {
        #-- Default Shift-jis
        $enc="Shift-JIS" 
    }
    putMessage(" Encode="+$enc )
    #-- �G���[�Ȃ�
    if( $retcode -eq 0 ) {
        #-- In-File Stream Open
        putMessage(" InFile="+$pFromFile )
        $fStream = New-Object System.IO.StreamReader($pFromFile, [System.Text.Encoding]::GetEncoding($enc))
        #-- OutFile Name
        $pToFile = $pFromFile.Split(".")[0] + "_" + $unitCnt + ".sql"
        #-- Out-File Stream Open
        putMessage(" OutFile="+$pToFile )
        $oStream = New-Object System.IO.StreamWriter($pToFile, $false, [System.Text.Encoding]::GetEncoding($enc))
        #-- Loop 1Line
        while (($linedata = $fStream.ReadLine()) -ne $null)
        {
            #-- ReadCount >= SplitCnt
            if( $rdCnt -ge $pSplitCnt ) {
                #-- Unit Count Clear
                $unitCnt++
                #-- ReadCount Clear
                $rdCnt = 0
                #-- OutFile Close
                $oStream.Close()
                $oStream.Dispose()
 
                #-- OutFile Name
                $pToFile = $pFromFile.Split(".")[0] + "_" + $unitCnt + ".sql"
                #-- Out-File Stream ReOpen
                putMessage(" OutFile="+$pToFile )
                $oStream = New-Object System.IO.StreamWriter($pToFile, $false, [System.Text.Encoding]::GetEncoding($enc))
            }
            #-- File Write
            if ($rdCnt -eq 0) {
                $oStream.WriteLine("insert into act_log(seq_number,mst_customer_code,timestamp,user_name,device_ip,operation_target,operation_name,resource_name,relate_resource1,relate_resource2,action_log,action_kind) value")
            }
            $oStream.WriteLine($linedata)
            #-- data count ++
            $rdCnt++
            $allCnt++
        } #-- while end
    } #-- if end
 
}catch [Exception]{
    putMessage(" ### FileSplit Error ###")
    putMessage($error)
    $retcode=1
}finally{
    #-- File Close
    $oStream.Close()
    $oStream.Dispose()
    $fStream.Close()
    $fStream.Dispose()
}
#-- Exit Message
putMessage(" Data Count=" + $allCnt)
putMessage(" FileSplit ExitCode=" + $retcode)
#-- ���A�R�[�h
exit $retcode