<?php
    require "../../database.php";

    $datBanId = $_POST["viewModel"];
    if(isset($datBanId)){
                    
        $database = new database();
        $result = $database->delete('datban','iddatban',$datBanId);

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