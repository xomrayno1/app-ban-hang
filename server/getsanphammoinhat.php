<?php
    include "connect.php";
    $query = "select * from products Order by id desc limit 6";
    $data = mysqli_query($conn,$query);
    $mangspmoinhat = array();
    while($row = mysqli_fetch_assoc($data)){
        array_push($mangspmoinhat,new Sanphammoinhat(
            $row['id'],
            $row['name'],
            $row['price'],
            $row['images'],
            $row['description'],
            $row['idCategory']
        )); 

    }
    echo json_encode($mangspmoinhat);
    class Sanphammoinhat{
        function Sanphammoinhat($id,$tensp,$giasp,$hinhanhsp,$motasp,$idcategory){
            $this->id = $id;
            $this->tensp = $tensp;
            $this->giasp = $giasp;
            $this->hinhanhsp = $hinhanhsp;
            $this->motasp = $motasp;
            $this->idcategory = $idcategory;
        }
    }


?>