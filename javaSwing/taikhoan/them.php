<?php
    require "../../database.php";

    $taiKhoanJson = $_POST["viewModel"];
    if(isset($taiKhoanJson)){
        $taiKhoan = json_decode($taiKhoanJson,true);

        $database = new database();
        $result = $database->insert('taikhoan',$taiKhoan);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>