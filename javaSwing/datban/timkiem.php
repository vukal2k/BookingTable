<?php
    require "../../database.php";

    $searchKey = $_GET['searchkey'];
    $idnhahang = $_GET['idnhahang'];
    if(isset($searchKey)){
        $database = new database();

        $query = "SELECT `iddatban`,t.username,  `ngayden`, `gioden`, `soluongnguoilon`, `soluongtreem`, `ghichu`, `tongtien` FROM `datban` d
                    INNER JOIN taikhoan t
                    ON d.`idtaikhoan` = t.idtaikhoan
                    WHERE `idnhahang`=".$idnhahang." AND (t.username LIKE '%".$searchKey."%' 
                    OR ngayden = '".$searchKey."' 
                    OR gioden = '".$searchKey."')";
                    
        $result = $database->fetchsql($query);

        
        
        echo json_encode($result);
    }
?>