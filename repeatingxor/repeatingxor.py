import binascii, decimal
stuff=[]
f = open("encrypted.txt").read()
i = 0
while (i+2<len(f)):
    stuff.append(f[i:i+2])
    i+=2

offset=12 #LENGTH IS 12
prekappa=0
while (offset < 13):
    
    shift = [stuff[x+offset] for x in xrange(len(stuff)-offset)]
    shift+=stuff[0:offset]
    i = 0
    text=""
    passw=""
    decrypt=""
    while (i < 30):
        text=text+stuff[i]
        passw=passw+shift[i]
        if len((hex(int(stuff[i],16) ^ int(shift[i],16)))[2:]) == 1:
            decrypt+="0"
        elif len((hex(int(stuff[i],16) ^ int(shift[i],16)))[2:]) == 0:
            decrypt+="00"
        decrypt=decrypt+(hex(int(stuff[i],16) ^ int(shift[i],16)))[2:]
        i+=1
    offset+=12
    prekappa=0
    
    print "text:",text
    print "passw:",passw
    print "decrypt:",decrypt
    print "\n"
    
