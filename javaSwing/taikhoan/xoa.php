<?php
    require "../../database.php";

    $taikhoanId = $_POST["viewModel"];
    if(isset($taikhoanId)){
                    
        $database = new database();
        $result = $database->delete('taikhoan','idtaikhoan',$taikhoanId);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>