def STR(a):
    a = str(a)
    # Yes, this is a little bit silly :-)
    for i in range(0, len(a) - 1, 2):
            print(chr(int(a[i:i+2]))),


STR(6976767380847367326785)
STR(828669833265826932708578)
