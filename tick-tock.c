// this requires the chinese remainder theorem. The code below will take too long to work!!!

#include <stdio.h>
#include <stdlib.h>

int main() {
    long i = 358054;
    for (; i<9223372036854775807; i+=200009) {
        //printf("%ld\n", i);
        if (i % 2 == 1) {
        if (i % 3 == 2) {
        if (i % 13 == 8) {
        if (i % 29 == 4) {
        if (i % 191 == 130) {
        if (i % 397 == 343) {
        if (i % 691 == 652) {
        if (i % 1009 == 858) {
        if (i % 2039 == 689) {
        if (i %  4099 == 1184) {
        if (i %  7001 == 2027) {
        if (i %  10009 == 5119) {
        if (i %  19997 == 15165) {
        if (i %  30013 == 15340) {
        if (i %  70009 == 29303) {
        if (i %  160009 == 42873) {
        if (i %  200009 == 158045) {
            printf("\nasdasd%ld\n", i);
        }}}}}}}}}}}}}}}}}
    }
}
