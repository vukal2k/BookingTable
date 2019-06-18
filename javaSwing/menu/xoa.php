<?php
    require "../../database.php";

    $menuId = $_POST["viewModel"];
    if(isset($menuId)){
                    
        $database = new database();
        $result = $database->delete('menu','idmenu',$menuId);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>