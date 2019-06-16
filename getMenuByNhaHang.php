<?php
    require "database.php";

    $idNhaHang = $_GET['idnhahang'];
    if(isset($idNhaHang)){
        $database = new database();
        $listHinhAnh = $database->fetchsql("SELECT idmenu , hinhanh FROM menu WHERE idnhahang = ".$idNhaHang);
        
        echo json_encode($listHinhAnh);
    }
?>

<!-- hdsd: truyen url: localhost/..cac kieu../getMenuByNhaHang.php?idnhahang=caisonaodo -->