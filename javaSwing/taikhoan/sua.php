<?php
    require "../../database.php";

    $taiKhoanJson = $_POST["viewModel"];
    if(isset($taiKhoanJson)){
        $taiKhoan = json_decode($taiKhoanJson);

        $query = "UPDATE taikhoan SET username = '".$taiKhoan->username."' ,
                                    email = '".$taiKhoan->email."' ,
                                    password = '".$taiKhoan->password."' , 
                                    sdt = '".$taiKhoan->sdt."' ,
                                    tenkhachhang = '".$taiKhoan->tenkhachhang."'
                    WHERE idtaikhoan = ".$taiKhoan->idtaikhoan;
                    
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