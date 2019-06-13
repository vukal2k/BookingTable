<?php
    require "../../database.php";

    $searchKey = $_GET['searchkey'];
    if(isset($searchKey)){
        $database = new database();
        $result = $database->fetchsql("select * from thanhpho
                                        WHERE tenthanhpho LIKE '%".$searchKey."%'");
    
        echo json_encode($result);
    }
?>