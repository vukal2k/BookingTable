<?php
    require "../../database.php";

    $nhaHangJson = $_POST["viewModel"];
    $fileHinhAnhJson = $_POST["fileHinhAnh"]

    if(isset($thanhPhoJson)){
        $thanhpho = json_decode($nhaHangJson,true);

        $database = new database();
        $result = $database->insert('khuvuc',$thanhpho);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>