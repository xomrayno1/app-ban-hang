<?php
 $host = "localhost";
 $username = "root";
 $password = "";
 $database = "dataappbanhang";

 $conn = mysqli_connect($host,$username,$password,$database);
 mysqli_query($conn,"SET NAME '.utf8.'");
 mysqli_set_charset($conn, 'utf8'); 

?>