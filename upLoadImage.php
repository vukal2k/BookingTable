<?php
    $fileHinhAnh=$_FILES["file"];

    if(isset($fileHinhAnh)){
        $target_dir = "img/";
        $target_file = $target_dir . basename($fileHinhAnh["name"]);
        $uploadOk = 1;
        $check = getimagesize($fileHinhAnh["tmp_name"]);

        if($check !== false) {
            $uploadOk = 1;
        } else {
            echo "Failed Check getimagesize fail";die();
            $uploadOk = 0;
        }
        if (file_exists($target_file)) {
            echo "Success";die();
            $uploadOk = 0;
        }
        if ($uploadOk == 0) {
            echo "Failed Upload is not OK";die();
        // if everything is ok, try to upload file
        } else {
            if (move_uploaded_file($fileHinhAnh["tmp_name"], $target_file)) {
                echo "Success";
            } else {
                echo "Failed"; die();
            }
        }
    }
?>