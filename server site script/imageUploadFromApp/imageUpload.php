<?php
require 'init.php';


if($conn){

$title = $_POST['title'];
$image = $_POST['image'];
$path = "uploads/$title.jpg";


$sql = "insert into image_upload_table(title,path) values ('$title','$path')";

if(mysqli_query($conn,$sql))
{
	file_put_contents($path,base64_decode($image));
	echo json_encode(array('response' => "Image Upload Successfull."));
}
else
{
	echo json_encode(array('response' => "Image Upload Failed."));
}

mysqli_close($conn);



}



































?>