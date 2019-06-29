<?php
    require "../../database.php";

    $nhaHangJson = $_POST["viewModel"];
    if(isset($nhaHangJson)){
        $nhaHang = json_decode($nhaHangJson);

        $query = "UPDATE `nhahang` SET  `sdt`='".$nhaHang->sdt."',
                                        `mota`='".$nhaHang->mota."',
                                        `uudai`='".$nhaHang->uudai."',
                                        `khoangtien`='".$nhaHang->khoangtien."',
                                        `giodonkhach`='".$nhaHang->giodonkhach."',
                                        `hinhanh`='".$nhaHang->hinhanh."' 
                                        WHERE `idnhahang`=".$nhaHang->idnhahang;

        //echo $query; die();            
        $database = new database();
        $result = $database->update($query);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>