<?php
    require "../../database.php";

    $database = new database();
    $result = $database->fetchsql("select * from thanhpho");

    echo json_encode($result);

?>