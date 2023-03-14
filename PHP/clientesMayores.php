<?php
$hostname = 'localhost';
$database = 'tareas';
$username = 'root';
$password = 'Mysql@root1508';

$conexion = new mysqli($hostname, $username, $password, $database);
if($conexion -> connect_errno){
    echo "El sitio web está experimentado problemas";
}

$list = $conexion -> prepare("SELECT count(*) as mayores FROM Clients where age >= 18");

$list -> execute();
$result = $list -> get_result();
$outp = $result -> fetch_all(MYSQLI_ASSOC);
if(json_encode($outp) != "[]"){
    echo json_encode($outp);
}


$list->close();
$conexion->close();
?>