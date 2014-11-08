#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    char s[] = "CGCDSE_XGKIBCDOY^OKFCDMSE_XLFKMY"; // This is taken from the diff of tar downloaded and official tar
    size_t len = strlen(s);
    //memfrob(s, len);
    int i = 0;

    for (; i < len; i++) {
        *(s + i) ^= 42; // essentially runs memfrob() on the string
    }

    printf("%s\n", s);
    return 0;
}

// $ ssh jon@backdoor.picoctf.com
// Password is iminyourmachinestealingyourflags
// $ cat flags.txt
