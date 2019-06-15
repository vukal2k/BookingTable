<?php
    require "../../database.php";

    $database = new database();
    $result = $database->fetchsql("select * from taikhoan");

    echo json_encode($result);

?>