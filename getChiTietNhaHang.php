<?php
require "dbCon.php";
$idnh = $_POST['idnhahang']; 

$query = "SELECT * from nhahang where idnhahang = $idnh";
$data = mysqli_query($connect, $query);


//1. Tạo class ChiTietNhaHang
class ChiTietNhaHang{
	function ChiTietNhaHang($idnhahang, $tennhahang, $diachi, $mota, $uudai, $loaihinh, $khoangtien, $giodonkhach, $hinhanh){
		$this->IdNhaHang = $idnhahang;
		// $this->IdTaiKhoan=$idtaikhoan;
		$this->TenNhaHang = $tennhahang;
		$this->DiaChi = $diachi;
		$this->MoTa = $mota;
		$this->UuDai = $uudai;
		$this->LoaiHinh = $loaihinh;
		// $this->IdKhuVuc = $idkhuvuc;
		$this->KhoangTien = $khoangtien;
		$this->GioDonKhach = $giodonkhach;
		$this->HinhAnh = $hinhanh;
	}
} 

//2. Tạo mảng

//$mangCTNH = array();

//3. Thêm phần tử vào mảng

while ($row = mysqli_fetch_assoc($data)) {
	//array_push($mangCTNH, ); //why array???
	$nhaHang = new ChiTietNhaHang($row ['idnhahang'], $row ['tennhahang'], $row ['diachi'], $row ['mota'], $row ['uudai'], $row ['loaihinh'],$row ['khoangtien'], $row ['giodonkhach'], $row ['hinhanh']);
}
//4. Chuyển định dạng của mảng thành json
echo json_encode($nhaHang);
?>