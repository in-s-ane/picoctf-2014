#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>

int secret = 0;

int main(int argc, char **argv){
    int *ptr = &secret;
    printf(argv[1]);
    printf("\n%p\n", ptr);
    printf("\n%d\n", secret);

    if (secret == 1337){
        printf("Success!");
        //give_shell();
    }
    return 0;
}
