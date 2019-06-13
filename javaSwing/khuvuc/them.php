<?php
    require "../../database.php";

    $thanhPhoJson = $_POST["viewModel"];
    if(isset($thanhPhoJson)){
        $thanhpho = json_decode($thanhPhoJson,true);

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