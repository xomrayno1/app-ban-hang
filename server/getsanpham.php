<?php
    include "connect.php";
    $page = $_GET['page'];
    $idsp = $_POST['idcategory'];
    $space = 5;
    $limit = ($page - 1) * $space;
    $mangsanpham = array();
    $query = "select * from products where idcategory = $idsp limit $limit,$space";
    $data = mysqli_query($conn, $query);

    while($row = mysqli_fetch_assoc($data)){
        array_push($mangsanpham,new Sanpham(
            $row['id'],
            $row['name'],
            $row['price'],
            $row['images'],
            $row['description'],
            $row['idCategory']
        )); 

    }
    echo json_encode($mangsanpham);
    class Sanpham{
        function Sanpham($id,$tensp,$giasp,$hinhanhsp,$motasp,$idcategory){
            $this->id = $id;
            $this->tensp = $tensp;
            $this->giasp = $giasp;
            $this->hinhanhsp = $hinhanhsp;
            $this->motasp = $motasp;
            $this->idcategory = $idcategory;
        }
    }


?>