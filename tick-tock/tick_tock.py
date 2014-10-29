#!/usr/bin/python

'''
 This gives the first part of the secret.
 The second part is Euler's Totient of the last two prime moduli. Basically find all the numbers from 1 - 200009*160009 that are relatively prime to 200009*160009
 So, go to http://www.javascripter.net/math/calculators/eulertotientfunction.htm and find the totient of 32003240081

 The end result is that the password is 83359654581036155008716649031639683153293510843035531 and the signature is 32002880064
'''

result = 1
addConst = 1

secretz = [(1, 2), (2, 3), (8, 13), (4, 29), (130, 191), (343, 397), (652, 691), (858, 1009),(689, 2039), (1184, 4099), (2027, 7001), (5119, 10009), (15165, 19997), (15340, 30013),(29303, 70009), (42873, 160009), (158045, 200009)]

for (r,m) in secretz:
	while result % m != r:
		result = result + addConst
	addConst = addConst* m
	print "Step %d result = %d" %(m,result)

print "The first key is: %d" %(result)
