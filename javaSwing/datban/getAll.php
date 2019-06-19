<?php
    require "../../database.php";

    $idNhaHang = $_GET["idnhahang"];
    if(isset($idNhaHang)){
        $database = new database();
        $result = $database->fetchsql("SELECT `iddatban`,t.username,  `ngayden`, `gioden`, `soluongnguoilon`, `soluongtreem`, `ghichu`, `tongtien` FROM `datban` d
                                        INNER JOIN taikhoan t
                                        ON d.`idtaikhoan` = t.idtaikhoan
                                        WHERE `idnhahang`=".$idNhaHang);

        echo json_encode($result);
    }

?>