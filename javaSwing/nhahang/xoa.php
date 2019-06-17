<?php
    require "../../database.php";

    $nhaHangId = $_POST["viewModel"];
    if(isset($nhaHangId)){
                    
        $database = new database();
        $result = $database->delete('nhahang','idnhahang',$nhaHangId);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>