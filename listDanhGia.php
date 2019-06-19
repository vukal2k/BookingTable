<?php
    require "database.php";

    $idNhaHang = $_GET["idnhahang"];
    if(isset($idNhaHang)){
        $database = new database();
        $result = $database->fetchsql("SELECT d.`iddanhgia`, d.`idtaikhoan`, d.`noidung`, d.`danhgiasao`, t.tenkhachhang FROM `danhgia` d
                                        INNER JOIN taikhoan t
                                        ON d.idtaikhoan=t.idtaikhoan
                                        WHERE d.`idnhahang`=".$idNhaHang);

        echo json_encode($result);
    }
?>

<!-- http://localhost/server/listDanhGia.php?idnhahang=1 -->