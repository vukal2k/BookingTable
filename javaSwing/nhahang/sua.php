<?php
    require "../../database.php";

    $nhaHangJson = $_POST["viewModel"];
    if(isset($nhaHangJson)){
        $nhaHang = json_decode($nhaHangJson);

        $query = "UPDATE `nhahang` SET  `idtaikhoan`=".$nhaHang->idtaikhoan.",
                                        `tennhahang`='".$nhaHang->tennhahang."',
                                        `diachi`='".$nhaHang->diachi."',
                                        `sdt`='".$nhaHang->sdt."',
                                        `mota`='".$nhaHang->mota."',
                                        `uudai`='".$nhaHang->uudai."',
                                        `loaihinh`='".$nhaHang->loaihinh."',
                                        `idkhuvuc`='".$nhaHang->idkhuvuc."',
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