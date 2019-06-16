<?php
    require "../../database.php";

    $nhaHangJson = $_POST["viewModel"];

    if(isset($nhaHangJson)){
        $nhaHang = json_decode($nhaHangJson,true);

       

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