<?php
    require "../../database.php";

    $searchKey = $_GET['searchkey'];
    if(isset($searchKey)){
        $database = new database();
        $result = $database->fetchsql("SELECT n.*,k.tenkhuvuc,t.username FROM nhahang n
                                        INNER JOIN khuvuc k
                                        ON n.idkhuvuc=k.idkhuvuc
                                        INNER JOIN taikhoan t
                                        ON n.idtaikhoan=t.idtaikhoan
                                        WHERE n.tennhahang LIKE '%".$searchKey."%' 
                                        OR t.username LIKE '%".$searchKey."%' 
                                        OR n.diachi LIKE '%".$searchKey."%' 
                                        OR n.sdt LIKE '%".$searchKey."%' 
                                        OR n.loaihinh LIKE '%".$searchKey."%'");
        
        echo json_encode($result);
    }
?>