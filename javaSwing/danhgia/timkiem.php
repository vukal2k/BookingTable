<?php
    require "../../database.php";

    $searchKey = $_GET['searchkey'];
    $idnhahang = $_GET['idnhahang'];
    if(isset($searchKey)){
        $database = new database();

        $filterByDanhGiaSao ="";

        if(is_numeric($searchKey)){
            $filterByDanhGiaSao="OR danhgiasao=".$searchKey;
        }

        $query = "SELECT `iddanhgia`,t.username, `noidung`, `danhgiasao` FROM `danhgia` d
                INNER JOIN taikhoan t
                ON t.idtaikhoan=d.idtaikhoan
                WHERE idnhahang=".$idnhahang." 
                AND (t.username LIKE '%".$searchKey."%' OR noidung LIKE '%".$searchKey."%' ".$filterByDanhGiaSao.")";
                    
        $result = $database->fetchsql($query);

        
        
        echo json_encode($result);
    }
?>