def reverse(dictionary):
    newdic = {}
    for key in dictionary:
        if dictionary[key] in newdic:           # If the value is already in the new dictionary
            newdic[dictionary[key]].append(key) # Add the key to list which is the value of the key in the new dictionary
        else:
            newdic[dictionary[key]] = [key] # Creates a new dictionary key with the value as the key and the key as the value
    for key in newdic:
        if len(newdic[key]) == 1:        # If the key is not equal to multiple values,
            newdic[key] = newdic[key][0] # the value is taken out of the list
    return newdic

data = '''
deg ihdecwbtidbcm zckg bu bxxoipgioimchdclach

xgdu ygd kcvm dc jhubmguu
dc kglgid deg ehmu
kbk dega ugmk og kihyedgwu vegm b iupgk lcw ucmu
achwg deg uikkgud jhmze b gfgw ogd
jhd ach zim jgd jglcwg vgwg dewchye
obudgw bxx oipg i oim chd cl ach

dwimrhbx iu i lcwgud
jhd cm lbwg vbdebm
cmzg ach lbmk achw zgmdgw
ach iwg uhwg dc vbm
achwg i usbmgxguu sixg sidegdbz xcd
imk ach eifgmd ycd i zxhg
ucogecv bxx oipg i oim chd cl ach

bo mgfgw ycmmi zidze oa jwgide
uia ycckjag dc decug vec pmgv og
jca viu b i lccx bm uzeccx lcw zhddbmy yao
debu yhau ycd go uziwgk dc kgide
ecsg eg kcgumd ugg wbyed dewchye og
mcv b wgixxa vbue deid b pmgv ecv dc uvbo

jg i oim
vg ohud jg uvbld iu i zchwubmy wbfgw
jg i oim
vbde ixx deg lcwzg cl i ywgid daseccm
jg i oim
vbde ixx deg udwgmyde cl i wiybmy lbwg
oaudgwbchu iu deg kiwp ubkg cl deg occm

dbog bu wizbmy dcviwk hu dbxx deg ehmu iwwbfg
eggk oa gfgwa cwkgw imk ach obyed uhwfbfg
achwg hmuhbdgk lcw deg wiyg cl viw
uc sizp hs yc ecog achwg dewchye
ecv zchxk b oipg i oim chd cl ach

jg i oim
vg ohud jg uvbld iu i zchwubmy wbfgw
jg i oim
vbde ixx deg lcwzg cl i ywgid daseccm
jg i oim
vbde ixx deg udwgmyde cl i wiybmy lbwg
oaudgwbchu iu deg kiwp ubkg cl deg occm

jg i oim
vg ohud jg uvbld iu i zchwubmy wbfgw
jg i oim
vbde ixx deg lcwzg cl i ywgid daseccm
jg i oim
vbde ixx deg udwgmyde cl i wiybmy lbwg
oaudgwbchu iu deg kiwp ubkg cl deg occm
'''

buckets = {}
for i in data:
    if i != ' ' and i != '\n':
        letter = ord(i)
        #print letter
        if buckets.has_key(i):
            buckets[i] += 1
        else:
            buckets[i] = 0

import operator
buckets = sorted(buckets.items(), key=operator.itemgetter(1))
print buckets

[('r', 0), ('t', 0), ('s', 8), ('f', 9), ('p', 10), ('j', 20), ('z', 20), ('k', 27), ('v', 28), ('a', 29), ('x', 30), ('l', 31), ('y', 31), ('o', 40), ('h', 48), ('m', 59), ('e', 62), ('b', 64), ('u', 64), ('w', 67), ('i', 93), ('d', 95), ('c', 99), ('g', 125)]

data = data.replace('g','E')
data = data.replace('c','T')
data = data.replace('d','A')
data = data.replace('i','O')
data = data.replace('w','I')
data = data.replace('u','N') #switch
data = data.replace('b','S')
data = data.replace('e','H')
data = data.replace('m','R')
data = data.replace('h','D')
data = data.replace('o','L')
data = data.replace('y','C') #switch
data = data.replace('l','U')
data = data.replace('x','M')
data = data.replace('a','W')
data = data.replace('v','F')
data = data.replace('k','G')
data = data.replace('z','Y')
data = data.replace('j','P')
data = data.replace('p','B')
data = data.replace('f','V')
data = data.replace('s','K')
data = data.replace('t','J')
data = data.replace('r','X')
print data
