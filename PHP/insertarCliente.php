<?php
$hostname = 'localhost';
$database = 'tareas';
$username = 'root';
$password = 'Mysql@root1508';

$conexion = new mysqli($hostname, $username, $password, $database);
if($conexion -> connect_errno){
    echo "El sitio web está experimentado problemas";
}

$name=$_POST['name'];
$age=$_POST['age'];

$insert = "INSERT INTO Clients(name, age, registerdate) VALUES ('$name', $age, curdate())";
$conexion->query($insert);

$conexion->close();
?>