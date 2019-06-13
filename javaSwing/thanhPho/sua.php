<?php
    require "../../database.php";

    $thanhPhoJson = $_POST["viewModel"];
    if(isset($thanhPhoJson)){
        $thanhpho = json_decode($thanhPhoJson);

        $query = "UPDATE thanhpho SET tenthanhpho = '".$thanhpho->tenthanhpho."'
                    WHERE idthanhpho = ".$thanhpho->idthanhpho;
                    
        $database = new database();
        $result = $database->update($query);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>