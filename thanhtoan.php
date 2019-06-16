<?php
    require "database.php";

    $donDatBanJson = $_POST["viewModel"];
    if(isset($donDatBanJson)){
        $donDatBan = json_decode($donDatBanJson);

        $query = "UPDATE datban SET soluongnguoilon = '".$donDatBan->soluongnguoilon."' , 
                                    soluongtreem = ".$donDatBan->soluongtreem.",
                                    tongtien = ".$donDatBan->tongtien."
                     WHERE iddatban = ".$donDatBan->iddatban;

       
        //echo $query; die();            
        $database = new database();
        $result = $database->update($query);

        if($result>0){
            echo "success";die();
        }
        else{
            echo "failed";die();
        }
    }
    echo "Khong co prams";

?>

<!-- params: viewModel:{
		"iddatban":"1",
		"soluongnguoilon": "11",
		"soluongtreem":"2",
		"tongtien":"123000"
	} -->