<?php
    require "../../database.php";

    $database = new database();
    $result = $database->fetchsql("select k.idkhuvuc,k.tenkhuvuc,k.idkhuvuc,t.tenthanhpho from khuvuc k
                                    INNER JOIN thanhpho t
                                    ON k.idthanhpho=t.idthanhpho");

    echo json_encode($result);

?>