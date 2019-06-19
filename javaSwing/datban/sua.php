<?php
    require "../../database.php";

    $datbanJson = $_POST["viewModel"];
    if(isset($datbanJson)){
        $datBan = json_decode($datbanJson);

        $query = "UPDATE `datban` SET `idtaikhoan`=".$datBan->idtaikhoan.",
                                    `idnhahang`=".$datBan->idnhahang.",
                                    `ngayden`='".$datBan->ngayden."',
                                    `gioden`='".$datBan->gioden."',
                                    `soluongnguoilon`=".$datBan->soluongnguoilon.",
                                    `soluongtreem`=".$datBan->soluongtreem.",
                                    `ghichu`='".$datBan->ghichu."',
                                    `tongtien`='".$datBan->tongtien."' 
                                    WHERE `idatban` = ".$datBan->idatban;

       
        //echo $query; die();            
        $database = new database();
        $result = $database->update($query);

        if($result>0){
            echo "success";
            die();
        }
        else{
            echo "failed";
            die();
        }
    }
    echo "Khong co prams";

?>