<?php
    require "../../database.php";

    $searchKey = $_GET['searchkey'];
    if(isset($searchKey)){
        $database = new database();
        $result = $database->fetchsql("select * from taikhoan
                                        WHERE tenkhachhang LIKE '%".$searchKey."%' OR
                                        email LIKE '%".$searchKey."%' OR
                                        username LIKE '%".$searchKey."%'");
    
        echo json_encode($result);
    }
?>