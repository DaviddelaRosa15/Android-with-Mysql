<?php
$hostname = 'localhost';
$database = 'tareas';
$username = 'root';
$password = 'Mysql@root1508';

$conexion = new mysqli($hostname, $username, $password, $database);
if($conexion -> connect_errno){
    echo "El sitio web está experimentado problemas";
}

$id = $_POST['id'];

$delete = "DELETE FROM Clients WHERE id = $id";

$conexion -> query($delete);

$conexion->close();
?>