<?php
$hostname = 'localhost';
$database = 'tareas';
$username = 'root';
$password = 'Mysql@root1508';

$conexion = new mysqli($hostname, $username, $password, $database);
if($conexion -> connect_errno){
    echo "El sitio web está experimentado problemas";
}

$idCliente=$_POST['id'];

$client = $conexion -> prepare("SELECT * FROM Clients WHERE id = $idCliente");

$client -> execute();
$result = $client -> get_result();
$outp = $result -> fetch_all(MYSQLI_ASSOC);
if(json_encode($outp) != "[]"){
    echo json_encode($outp);
}


$client->close();
$conexion->close();
?>