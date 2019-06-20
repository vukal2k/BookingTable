<?php
    require "database.php";

    $danhGiaJson = $_POST["viewModel"];

    if(isset($danhGiaJson)){
        $danhGia = json_decode($danhGiaJson,true);

        $database = new database();
        
        //validate danh gia bi trung
        $queryValidate = "SELECT `iddanhgia` FROM `danhgia` WHERE idnhahang =".$danhGia['idnhahang']." 
                        AND idtaikhoan=".$danhGia["idtaikhoan"]." AND noidung='".$danhGia["noidung"]."'";

        $countExist=$database->total($queryValidate);
        if($countExist>0){
            echo "duplicated";
            die();
        }

        //insert vao bang danh gia
        $result = $database->insert('danhgia',$danhGia);

        if($result>0){
            echo "success";
            die();
        }
        else{
            echo "failed";
            die();
        }
    }
    echo "Khong co prams";

?>

<!-- viewModel:{
		"iddanhgia":"null",
		"idnhahang": "1",
		"idtaikhoan":"50",
		"noidung":"ngol!!!!!!",
		"danhgiasao":5.0
} -->