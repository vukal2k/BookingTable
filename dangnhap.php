<?php
    require "database.php";

    $userJson = $_POST['viewModel'];
    if(isset($userJson)){
        $user = json_decode($userJson);
        $database = new database();
        $result = $database->fetchsql("SELECT * FROM `taikhoan` WHERE username='".$user->username."' AND password ='".$user->password."'");
        
        echo json_encode($result[0]);
    }
?>