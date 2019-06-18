<?php
    require "../../database.php";

    $menuJson = $_POST["viewModel"];

    if(isset($menuJson)){
        $menu = json_decode($menuJson,true);

        $database = new database();
        $result = $database->insert('menu',$menu);

        if($result>0){
            echo "success";
        }
        else{
            echo "failed";
        }
    }
    echo "Khong co prams";

?>