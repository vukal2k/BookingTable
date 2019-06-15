<?php
    require "database.php";

    $idNhaHang = $_GET['idnhahang'];
    if(isset($idNhaHang)){
        $database = new database();
        $listHinhAnh = $database->fetchsql("SELECT hinhanh FROM menu WHERE idnhahang = ".$idNhaHang);
        
        $result= array();
        for($i = 0; $i < count($listHinhAnh); $i++) {
            array_push($result,$listHinhAnh[$i]['hinhanh']);
        }
        
        echo json_encode($result);
    }
?>

<!-- hdsd: truyen url: localhost/..cac kieu../getMenuByNhaHang.php?idnhahang=caisonaodo -->