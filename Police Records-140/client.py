#!/usr/bin/python

import socket
import sys
import struct
import random
from os import urandom

HOST = "vuln2014.picoctf.com"
PORT = 21212

def xor(buf, key):
    """ Repeated key xor """
    encrypted = []
    for i, cr in enumerate(buf):
        k = key[i % len(key)]
        encrypted += [cr ^ k]
    return bytes(encrypted)

def secure_pad(buf):
    """ Ensure message is padded to block size. """
    key = urandom(5)
    buf = bytes([0x13, 0x33, 0x7B, 0xEE, 0xF0]) + buf
    buf = buf + urandom(16 - len(buf) % 16)
    enc = xor(buf, key)
    return enc

def remove_pad(buf):
    """ Removes the secure padding from the msg. """
    if len(buf) > 0 and len(buf) % 16 == 0:
        encrypted_key = buf[:5]
        key = xor(encrypted_key, bytes([0x13, 0x33, 0x7B, 0xEE, 0xF0]))
        dec = xor(buf, key)
        return dec[5:-2] # Since the format "!B2L128s" requires a string of 137 characters and dec[5:] is 139, we must trim off the last two characters. Don't worry, they're just padding

''' This is included for reference!
def secure_send(msg):
    """ Sends msg back to the client securely. """
    cookie = generate_cookie()
    data = struct.pack("!B2L128s", 0xFF, cookie, len(msg), msg)
    encrypted = secure_pad(data)
    self.request.sendall(encrypted)
    return cookie
'''

def main():
    f = open('officers.txt', 'w')
    f.write('Police just got wrecked\n\n')
    f.close()
    f = open('officers.txt', 'a')

    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect((HOST, PORT))

    # This grants access and has server return cookie
    access = struct.pack("!i", 0xAA)
    sock.sendall(access)

    # Decrypt cookie and unpack it
    received = sock.recv(1024)
    decrypted = remove_pad(received)
    magic, cookie, msglength, msg = struct.unpack("!B2L128s", decrypted)

    officer = None
    cmd = 1
    entry = 0
    badge = 694
    badge_dict = {}
    # Form requests to get entries
    while str(officer).find("INVALID ENTRY -- OFFICER DOES NOT EXIST") == -1:
        request = struct.pack("!B2LHL", magic, cookie, badge, cmd, entry)
        encrypted = secure_pad(request)
        sock.sendall(encrypted)
        received = sock.recv(1024)
        decrypted = remove_pad(received)
        magic, cookie, officerlength, officer = struct.unpack("!B2L128s", decrypted)
        officer = str(officer)
        try:
            badge = int(officer[officer.find('"BADGE": ')+len('"BADGE": '):officer.find('}')])
        except:
            badge = -1
        if badge in badge_dict:
            badge_dict[badge] += 1
        else:
            badge_dict[badge] = 1
        entry += 1
        print(officer)
        f.write(officer + "\n")

    print(badge_dict)
    for (badge_number, badge_count) in zip(badge_dict.keys(), badge_dict.values()):
        if badge_count > 1:
            print("THE FLAG IS: " + str(badge_number))

    sock.close()
    f.close()

def test():
    msg = b"WELCOME TO THE POLICE RECORDS DIRECTORY"
    data = struct.pack("!B2L128s", 0xFF, random.randrange(1, 1e8), len(msg), msg)
    encrypted = secure_pad(data)
    decrypted = remove_pad(encrypted)
    cookie = struct.unpack("!B2L128s", decrypted)
    print(cookie)

#test()
if sys.version_info < (3,0):
    sys.stderr.write("You need Python 3.0 or later to run this script\n")
    exit(1)
main()
