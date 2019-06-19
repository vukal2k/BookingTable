<?php
    require "database.php";

    $donDatBanId = $_POST["viewModel"];
    if(isset($donDatBanId)){
                    
        $database = new database();
        $result = $database->delete('datban','iddatban',$donDatBanId);

        if($result>0){
            echo "success"; die();
        }
        else{
            echo "failed"; die();
        }
    }
    echo "Khong co prams";

?>