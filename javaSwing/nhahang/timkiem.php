<?php
    require "../../database.php";

    $searchKey = $_GET['searchkey'];
    if(isset($searchKey)){
        $database = new database();
        $result = $database->fetchsql("select k.idkhuvuc,k.idthanhpho,k.tenkhuvuc,t.tenthanhpho from khuvuc k
                                        INNER JOIN thanhpho t
                                        ON k.idthanhpho=t.idthanhpho
                                        WHERE k.tenkhuvuc LIKE '%".$searchKey."%' OR t.tenthanhpho LIKE '%".$searchKey."%'");
        
        echo json_encode($result);
    }
?>