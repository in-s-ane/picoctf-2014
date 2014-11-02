echo $(echo "ibase=16; $(echo $1 | sed 'y/abcdef/ABCDEF/')" | bc | tr "\n" ' ' | sed "s/\\\ //g")

