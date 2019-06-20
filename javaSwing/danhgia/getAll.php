<?php
    require "../../database.php";

    $idNhaHang = $_GET["idnhahang"];
    if(isset($idNhaHang)){
        $database = new database();
        $result = $database->fetchsql("SELECT `iddanhgia`,t.username, `noidung`, `danhgiasao` FROM `danhgia` d
                                        INNER JOIN taikhoan t
                                        ON t.idtaikhoan=d.idtaikhoan
                                        WHERE idnhahang=".$idNhaHang);

        echo json_encode($result);
    }

?>