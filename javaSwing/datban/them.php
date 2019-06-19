<?php 
	require "../../database.php";

	$datBanInforJson = $_POST["viewModel"];
//echo $datBanInforJson;die();
	$datBan = json_decode($datBanInforJson,true); //de true thi no moi decode thanh array
	$validate=true;
	//echo $datBan["idnhahang"];die();
	$database = new database();
	$query = "SELECT * from datban where idnhahang = ".$datBan['idnhahang']." AND idtaikhoan= ".$datBan['idtaikhoan']." AND ngayden = '".$datBan['ngayden']."' AND gioden='".$datBan['gioden']."'";
            $data = $database->total($query);
            if($data>0){
            	$validate=false;
            }
            else{
            	$validate=true;
            }

	if($validate==true){
		$result = $database->insert("datban",$datBan);
		if($result>0){
			echo "success"; die();
		}else{
			echo "failed";
		}
	}else{
		echo "duplicated";
	}

	 // public function validate($sql)
  //       {
  //       	$query = "SELECT * from datban where idnhahang = $idnhahang, $idtaikhoan, ";
  //           $data = mysqli_query($connect, $query);
  //           $result = mysqli_query($this->link  , $sql);
  //           $tien = mysqli_fetch_assoc($result);
  //           return $tien;
  //       }
       
	// sử dụng class database để gọi ấmy cái hàm có sẵn của nó. K phải gọi key ra nhiều
	//restFul API
	// GTE: lấy về
	//POST:insert
	//PUT: update
	//Delete: xóa
	// dùng phải đúng chức năng của nó. Đừng cái gì cũng POST

 ?>