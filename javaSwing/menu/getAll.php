<?php
    require "../../database.php";

    $idNhaHang = $_GET["idnhahang"];
    if(isset($idNhaHang)){
        $database = new database();
        $result = $database->fetchsql("SELECT `idmenu`, `hinhanh` FROM `menu` WHERE idnhahang=".$idNhaHang);

        echo json_encode($result);
    }

?>