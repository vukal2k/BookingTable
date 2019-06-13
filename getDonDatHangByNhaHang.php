<?php
    require "database.php";

    $viewModelJson = $_POST["viewModel"];
    if(isset($viewModelJson)){
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

        $query=$query.$filter;

        $database = new database();
        $result = $database->fetchsql($query);

        echo json_encode($result);
    }
?>

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
