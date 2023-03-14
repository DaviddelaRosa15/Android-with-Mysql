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
$name = $_POST['name'];
$age = $_POST['age'];


$edit = "UPDATE Clients SET name = '$name', age = $age WHERE id = $id";

$conexion->query($edit);

$conexion->close();
?>