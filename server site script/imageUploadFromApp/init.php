<?php
header('Content-Type: application/json; charset=utf-8');
$host = "localhost";
$user_name ="root";
$user_pass ="obl@1#beacon#*";
$database ="beacon_obl";

$conn = mysqli_connect($host,$user_name,$user_pass,$database);
mysqli_query($conn,"SET character_set_results = 'utf8', character_set_client = 'utf8', character_set_connection = 'utf8', character_set_database = 'utf8', character_set_server = 'utf8'");



?>