<?php
    require "../../database.php";

    $idDanhGia = $_POST["viewModel"];
    if(isset($idDanhGia)){
                    
        $database = new database();
        $result = $database->delete('danhgia','iddanhgia',$idDanhGia);

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