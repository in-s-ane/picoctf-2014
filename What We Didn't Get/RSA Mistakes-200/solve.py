message = "THIS_IS_THE_FLAG_UGH"

original = "fd2adfc8f9e88d3f31941e82bef75f6f9afcbba4ba2fc19e71aab2bf5eb3dbbfb1ff3e84b6a4900f472cc9450205d2062fa6e532530938ffb9e144e4f9307d8a2ebd01ae578fd10699475491218709cfa0aa1bfbd7f2ebc5151ce9c7e7256f14915a52d235625342c7d052de0521341e00db5748bcad592b82423c556f1c1051 3 52"

o_message = "1348effb7ff42372122f372020b9b22c8e053e048c72258ba7a2606c82129d1688ae6e0df7d4fb97b1009e7a3215aca9089a4dfd6e81351d81b3f4e1b358504f024892302cd72f51000f1664b2de9578fbb284427b04ef0a38135751864541515eada61b4c72e57382cf901922094b3fe0b5ebbdbac16dc572c392f6c9fbd01e"

spoof = "fd2adfc8f9e88d3f31941e82bef75f6f9afcbba4ba2fc19e71aab2bf5eb3dbbfb1ff3e84b6a4900f472cc9450205d2062fa6e532530938ffb9e144e4f9307d8a2ebd01ae578fd10699475491218709cfa0aa1bfbd7f2ebc5151ce9c7e7256f14915a52d235625342c7d052de0521341e00db5748bcad592b82423c556f1c1051 3 37"

s_message = "81579ec88d73deaf602426946939f0339fed44be1b318305e1ab8d4d77a8e1dd7c67ea9cbac059ef06dd7bb91648314924d65165ec66065f4af96f7b4ce53f8edac10775e0d82660aa98ca62125699f7809dac8cf1fc8d44a09cc44f0d04ee318fb0015e5d7dcd7a23f6a5d3b1dbbdf8aab207245edf079d71c6ef5b3fc04416"

o_N, o_e, o_user_id = original.split(' ')
o_N = int(o_N, 16)
o_e = int(o_e)
o_user_id = int(o_user_id)

print o_N
print o_e
print o_user_id
print ""

s_N, s_e, s_user_id = spoof.split(' ')
s_N = int(s_N, 16)
s_e = int(s_e)
s_user_id = int(s_user_id)

print s_N
print s_e
print s_user_id
print ""

o_encrypted = hex(pow(o_user_id * int(message.encode('hex'), 16) + (o_user_id**2), o_e, o_N))
#                                                                   52 * 52 = 2704
s_encrypted = hex(pow(s_user_id * int(message.encode('hex'), 16) + (s_user_id**2), s_e, s_N))
#                                                                   37 * 37 = 1369 
o_unencrypted = int(o_message, 16)
s_unencrypted = int(s_message, 16)
o_m = 0
s_m = 1

while o_m != s_m:
    o_unencrypted += o_N
    o_m = (o_unencrypted ** (1.0/3) - 2704) // 52

    s_unencrypted += s_N
    s_m = (s_unencrypted ** (1.0/3) - 1369) // 37

print o_m
print hex(o_m).decode('hex')

#print hex(int((o_unencrypted ** (1.0/3) - o_user_id**2) / o_user_id)).decode('hex')
