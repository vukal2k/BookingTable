<?php
    require "../../database.php";

    $database = new database();
    $result = $database->fetchsql("SELECT n.*,k.tenkhuvuc,t.username FROM nhahang n
                                    INNER JOIN khuvuc k
                                    ON n.idkhuvuc=k.idkhuvuc
                                    INNER JOIN taikhoan t
                                    ON n.idtaikhoan=t.idtaikhoan");

    echo json_encode($result);

?>