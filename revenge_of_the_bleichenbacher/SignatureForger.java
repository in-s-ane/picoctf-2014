import java.io.*;
import java.security.MessageDigest;
import java.math.BigInteger;

/* Solution:
First, decompile the jar file with jd-gui and read through the implementations of all the classes
Notice a few things: 
a) There is no ASN.1 tag in the signature as is commonly used because the server expects SHA-1. This means that we'll have to do a bit of math ourselves. 
b) The server accepts uppercase versions of commands, which will prove to be handy later on when we need to manipulate a value into a multiple of 3. 
c) The signature verification uses an exponent of three, which makes it vulnerable to Bleichenbacher's signature forgery method.
See explanations of Daniel Bleichenbacher's RSA signature forgery at:
1. http://www.imc.org/ietf-openpgp/mail-archive/msg06063.html
2. http://www.symantec.com/connect/blogs/common-rsa-implementation-mistake-explained
 */
public class SignatureForger {
      private static final BigInteger N = new BigInteger("c5ddc7decb1beede4ebb96742e4279eb120b9c8b44472c0d0bb39da95a10cf72b630dbea181eeda65772779de8b6af53f2b0c5c3eccae2ef7a349b66637345f1cc0dec4d63550206688751e49da001b2f901cf39ebb1758bae0a89a3a4f8342fa26283f802ce6df144113a2abe075497d373435f80aa96bdf1ea500f58eea6bffb28add63c9d337dacf3bbf81996c7b6b9ac532007010acedb0714a547486c78ca162a0a85c643ce774b2805bd294435d262fb390adce055b971396c0363bb5f7aa409f5c223fa9c211945cb6be7a8df23a3357257a11bfe4bd983799d975e9ba337e928c33a7cd9638c5f4553b2a263233442677f848e948ccc4470a5a5bc16682b3a24188398389a079096d28588f03d01b7bfa6cce9a829e2f5c1b1cc785e891ffa89d63607f48473126f99aca203e0c2e77f21a35b6d6c8816c0650715144ff148d9c60f81bfacbfc5ef879a07bb6cd8e12476803006cc7ae25e8faafa4ee52dac698d7927092d10c4fb748dea6b3dd62a3588cf315f54216689877f3f0d", 16);

    public static String sha1(String paramString) {
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
            byte[] arrayOfByte = localMessageDigest.digest(paramString.getBytes("UTF-8"));
            StringBuffer localStringBuffer = new StringBuffer();
            for (int i = 0; i < arrayOfByte.length; i++) {
                String str = Integer.toHexString(0xFF & arrayOfByte[i]);
                if (str.length() == 1) localStringBuffer.append('0');
                localStringBuffer.append(str);
            }
            return localStringBuffer.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public boolean verifySignature(String paramString1, String paramString2) {
        // Preface: this was my analysis of the method before reading up on Bleichenbacher's exploit
        // Summary: your signature ^ 3 mod N has to look like
        // 0001ffffffffff...00ebfdec641529d4b59a54e18f8b0e9730f85939fb...
        String str1 = sha1(paramString1); // SHA-1 hash of your cmd
        BigInteger localBigInteger1 = new BigInteger(paramString2, 16); // Store your signature as a BigInteger
        BigInteger localBigInteger2 = localBigInteger1.modPow(new BigInteger("3"), N); // Your signature ^ 3 mod N
        String str2 = localBigInteger2.toString(16); // Your signature ^ 3 mod N in hexadecimal
        while (str2.length() < 768) { // Pad it to length 768 (the length of N)
            str2 = "0" + str2;
        }
        if ((str2.indexOf("0001ffffffffff") == 0) && (str2.length() == 768) && (str2.contains(str1))) { // Starts with 0001ffffffffff (a byte of 0, then 1, then a series of 0xFF bytes, 14 bytes total) and contains your cmd's hash
            for (int i = str2.indexOf("f"); i < str2.indexOf(str1) - 2; i++) {
                if (str2.charAt(i) != 'f') return false; // everything before your cmd's hash is an f 
            }
            if ((str2.charAt(str2.indexOf(str1) - 2) != '0') || (str2.charAt(str2.indexOf(str1) - 1) != '0')) { // The two chars before your cmd's hash are 0 (a single byte of 0)
                return false;
            }
            return true;
        }

        return false;
    }

    public static void getSignature(String cmd) {
        String str1 = sha1(cmd); // SHA-1 hash of your cmd
        System.out.println("SHA-1:\t" + str1);
        str1 = "00" + str1; // Prepend the 0 byte
        BigInteger d = new BigInteger(str1, 16); // d is the numeric representation of the 0 byte prepended to the hash. It needs to be preserved when the signature is cube rooted and then cubed.
        /* Our signature has the form 2^3057 - 2^2240 + d(2^2072) + GARBAGE */
        BigInteger T = new BigInteger("2").pow(168).subtract(d); // To simplify our cube root calculation, we let T = 2^168 - d
        /* By substituting d in terms of T, our signature is 2^3057 - (T)2^2072 + GARBAGE */
        BigInteger T_divide_multiply_3 = T.divide(new BigInteger("3")).multiply(new BigInteger("3"));
        System.out.println("T:\t" + T.toString(16));
        System.out.println("T/3*3:\t" + T_divide_multiply_3.toString(16)); // In order to preserve the signature after it is cube rooted and cubed, we need T/3 * 3 to be equal to the T, meaning that T is divisible by 3
        if (T.compareTo(T_divide_multiply_3) != 0) {
            System.out.println("[ERROR]: Invalid command! T must be divisible by 3.");
            return;
        }
        BigInteger cubedSig1 = new BigInteger("2").pow(3057).subtract(new BigInteger("2").pow(2240)).add(new BigInteger("2").pow(2072).multiply(d)); // 2^2057 - 2^2240 + (d)*2^2072 
        BigInteger cubedSig2 = new BigInteger("2").pow(3057).subtract(new BigInteger("2").pow(2072).multiply(T));  // 2^2057 - (T)2^2072
        if (cubedSig1.compareTo(cubedSig2) != 0) {
            System.out.println("[ERROR]: Incorrect substitution!");
            return;
        }
        System.out.println(cubedSig1.toString(16));

        /* This isn't necessary, but to prove that our algorithm works, we can complete the cube for the signature by letting GARBAGE =
         * ((T^2)2^1087)/3 - ((T^3)2^102)/27
         * We then get the cube root to be equal to 2^1019 - (T*2^34)/3 and we
         * can check our work by cubing this value to get:
         * 2^3057 - (T)2^2072 - ((T^2)2^1087)/3 - ((T^3)2^102)/27
         * thereby verifying our GARBAGE value
         * Therefore, we can forge a valid RSA signature by using the value
         * 2^1019 - (T*2^34)/3
        */
        
        BigInteger signature = new BigInteger("2").pow(1019).subtract(T.multiply(new BigInteger("2").pow(34)).divide(new BigInteger("3")));
        System.out.println("Forged RSA signature for use with command '" + cmd + "':" + signature.toString(16));
    }

    public static void main(String[] args) {
        getSignature("list");
        getSignature("CAT");
    }
}


/* Endgame:
Note: We get the port number from decompiling CommandServer.class and finding the PORT variable
   >> nc vuln2014.picoctf.com 4919
   list 7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffeaaf6483a865278dc8825fc8f68492e01cc41bd47b400000000
   Please enter which directory you'd like to list in (enter '.' for current
   directory).
   .
   CommandServer.jar
   .profile
   flag
   .bashrc
   .bash_logout
   CAT 7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffeabbf79f47ae5b05c2aca11965889b00089093ff3e000000000
   Please enter which file you'd like to read.
   flag
   arent_signature_forgeries_just_great
*/
