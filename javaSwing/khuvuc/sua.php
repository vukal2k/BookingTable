<?php
    require "../../database.php";

    $khuVucJson = $_POST["viewModel"];
    if(isset($khuVucJson)){
        $khuVuc = json_decode($khuVucJson);

        $query = "UPDATE khuvuc SET tenkhuvuc = '".$khuVuc->tenkhuvuc."' , 
                                    idthanhpho = ".$khuVuc->idthanhpho."
                     WHERE idkhuvuc = ".$khuVuc->idkhuvuc;

       
        //echo $query; die();            
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