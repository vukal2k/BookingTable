<?php
    require "database.php";

    $isComplete = $_GET["complete"];
    $viewModelJson = $_POST["viewModel"];
    if(isset($viewModelJson)&&isset($isComplete)){
        $viewModel = json_decode($viewModelJson);

        $query = "  SELECT t.idtaikhoan,t.tenkhachhang, d.gioden,d.ngayden,t.sdt
                    FROM datban d
                    INNER JOIN nhahang n
                    ON d.idnhahang = n.idnhahang
                    INNER JOIN taikhoan t
                    ON d.idtaikhoan=t.idtaikhoan
                    WHERE n.idnhahang = ".$viewModel->id;

        $filter="";
        if($viewModel->ngayDenFilterFrom!="" && $viewModel->ngayDenFilterTo!=""){
            $filter = $filter." AND d.ngayden>='".$viewModel->ngayDenFilterFrom."'
                                AND d.ngayden <='".$viewModel->ngayDenFilterTo."'";
        }
        if($viewModel->searchKey != ""){
            $filter = $filter." AND t.tenkhachhang LIKE '%".$viewModel->searchKey."%' 
                                OR t.sdt LIKE '%".$viewModel->searchKey."%' 
                                OR t.email LIKE '%".$viewModel->searchKey."%'";
        }

        if($isComplete==0){
            $filter = $filter." AND d.tongtien = 0";
        }
        if($isComplete==1){
            $filter = $filter." AND d.tongtien > 0";
        }

        $query=$query.$filter;

        $database = new database();
        $result = $database->fetchsql($query);

        echo json_encode($result);
    }
?>

<!-- truyen ntn: http://localhost/server/getDonDatHangByNhaHang.php?complete=0
complete=1 la hoan thanh roi, 0 la chua -->
<!-- method post, pram: 
viewModel: {
		"id":"1",
		"ngayDenFilterFrom": "",
		"ngayDenFilterTo":"",
		"searchKey":""
	}

class ketqua{
	idtaikhoan,tenkhachhang, gioden,ngayden,sdt
} -->
