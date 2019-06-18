<?php
    require "database.php";

    $idthanhpho = $_GET["idthanhpho"];
    if (isset($idthanhpho)){
        $database = new database();
        $result = $database->fetchsql("SELECT `idkhuvuc`, `tenkhuvuc` FROM `khuvuc` WHERE idthanhpho=".$idthanhpho);
    
        echo json_encode($result);
    }
?>

<!-- example: http://localhost/server/getKhuVucByThanhPho.php?idthanhpho=1 -->