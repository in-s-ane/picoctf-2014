<?php
include "config.php";
$con = mysqli_connect("localhost", "sql4", "sql4", "sql4");
$username = $_POST["username"];
$query = "SELECT * FROM users WHERE username='$username'";
$result = mysqli_query($con, $query);

if (mysqli_num_rows($result) !== 0) {
  die("Someone has already registered " . htmlspecialchars($username));
}

die("Registration has been disabled.");
?>
