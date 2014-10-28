import binascii, decimal
stuff=[]
f = open("encrypted.txt").read()
i = 0
while (i+2<len(f)):
    stuff.append(f[i:i+2])
    i+=2

offset=12 #LENGTH IS 12
prekappa=0
while (offset < len(stuff)):
    
    shift = [stuff[x+offset] for x in xrange(len(stuff)-offset)]
    shift+=stuff[0:offset]
    i = 0
    text=""
    passw=""
    decrypt=""
    while (i < 1):
        #if (int(stuff[i],16) ^ int(shift[i],16) == 0):
        #    prekappa+=1
        #text=text+stuff[i]
        #passw=passw+shift[i]
        #if int(stuff[i],16) ^ int(shift[i],16) <= 30:
        decrypt=decrypt+str((int(stuff[i],16) ^ int(shift[i],16)))+"."
        i+=1
    decrypt+="\n"
    #print(str(offset) + "\t"+str(prekappa/(len(stuff)-1.0)))
    offset+=12
    prekappa=0
    
    #print "text:",text
    #print "passw:",passw
    print "decrypt:",decrypt
    #print "\n"
    
