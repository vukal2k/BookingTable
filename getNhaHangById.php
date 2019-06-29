
<?php
  require "database.php";

  $idNhaHang = $_GET['idnhahang']; 
  if(isset($idNhaHang)){
	$query = "SELECT idnhahang,tennhahang,diachi,sdt,uudai,khoangtien,giodonkhach,hinhanh,mota FROM nhahang WHERE idnhahang = ".$idNhaHang; 

	$database = new database();
	$result = $database->fetchsql($query);
	
	echo json_encode($result[0]);
}
  
  
?>

<!-- http://localhost/server/getNhaHangById.php?idnhahang=1 -->