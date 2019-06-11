<?php
require "dbCon.php";



$username = $_POST['usernameKH'];
$email = $_POST['emailKH'];
$password = $_POST['passwordKH'];
$tenkhachhang = $_POST['tenkhachhangKH'];
$sdt = $_POST['sdtKH'];

$query = "INSERT INTO taikhoan VALUES (null, '$username', '$email', '$password', '$tenkhachhang', '$sdt')";

if (mysqli_query($connect, $query)){
	echo "success";
} else {
	echo "error";
}
?>