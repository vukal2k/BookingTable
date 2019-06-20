<?php
    require "database.php";

    $iddatban = $_GET['iddatban'];
    if(isset($iddatban)){
        $database = new database();
        $result = $database->fetchsql("SELECT t.tenkhachhang, t.sdt, d.idtaikhoan, d.ngayden, d.gioden,
                                  d.soluongnguoilon, d.soluongtreem, d.ghichu, d.tongtien
                            FROM datban d
                            INNER JOIN taikhoan t
                            ON d.idtaikhoan=t.idtaikhoan
                            WHERE d.iddatban = ".$iddatban );
        
        echo json_encode($result);
    }
?>

<!-- exp url GET: http://localhost/server/getChiTietDonDatBan.php?iddatban=16 -->