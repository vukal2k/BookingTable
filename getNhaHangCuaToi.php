
<?php
  require "dbCon.php";

  $idtk = $_GET['idtaikhoan']; 

  $query = "SELECT * FROM nhahang WHERE idtaikhoan = ".$idtk; 
  $data = mysqli_query($connect, $query);

 

  // 1. Tạo class NhaHang
class NhaHang{
	function NhaHang($idnhahang, $idtaikhoan, $tennhahang, $diachi,  $uudai, $loaihinh, $idkhuvuc,  $hinhanh){
		$this->IdNhaHang = $idnhahang;
		$this->IdTaiKhoan=$idtaikhoan;
		$this->TenNhaHang = $tennhahang;
		$this->DiaChi = $diachi;
		$this->UuDai = $uudai;
		$this->LoaiHinh = $loaihinh;
		$this->IdKhuVuc = $idkhuvuc;
		$this->HinhAnh = $hinhanh;
		
	}
}
// 2. Tạo mảng
$mangNH = array();
//3. Thêm phần tử vào mảng

while ($row = mysqli_fetch_assoc($data)) {
  	array_push($mangNH, new NhaHang($row ['idnhahang'],  $row ['idtaikhoan'], $row ['tennhahang'], $row ['diachi'], $row ['uudai'], $row ['loaihinh'], $row ['idkhuvuc'], $row ['hinhanh']));

  }

//4. Chuyển định dạng của mảng -> Json
echo json_encode($mangNH);
?>