<?php
    require "../../database.php";

    $thanhPhoId = $_POST["viewModel"];
    if(isset($thanhPhoId)){
                    
        $database = new database();
        $result = $database->delete('thanhpho','idthanhpho',$thanhPhoId);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>