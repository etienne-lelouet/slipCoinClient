package security.common;

import javax.crypto.Cipher;

import jdk.nashorn.internal.ir.debug.JSONWriter;

import java.io.InputStream;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


public class RSA {
	
	// Clef privée et publique RSA 2048bits, encodée en Base64
	private static final String STR_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCXWa04dq+qjX02QVOfG4lpdliYDmTlYAcLfIgKl3QRluBmAAjxhuiVaa0kc69jU69MdcBLPBN2oeWZPLTBUcXeuG3MYV7XuWR91mxZ/BKvMhm1wHZthYYcRjQjUsYGK0aX6cDqaXD+DFhL74xBcAEvcEy1J8+ZIjOFfsObr+ugf+AU8LmDHr9/BUdeLjKMUUPwAHxDb8HaRL2moT7dnH3Id7MUq4zeZNDIm4lNM7M1lWXIOWG++cTqorXTn3WLRTezBLEfcuPZQu36cHt4Ar4krVLznvn3E4KtL9+1bMCc8CfTuMfGbUlbgR3LQejsZfsyO9fYwrSHe7oFcqLhCX7FAgMBAAECggEAZOk1xV/c4CpWQcZsqrkBdX+isj9mpkjQaaguTGGO0et20otTazY3/OboulUnq2Iwjxozi/YSRBbNrs369qo+87CkBJEnW04Q4pYEyDp5erY8ziH01DEiqddlC+g0gAh6mO8R4TlMTRaOCJM/QKIdKDQH8QEKOV/EWk2avkMdJ3UTUTt/wEWGeta6p3dHms8x4jx57cw8ADVsI/8PxWeQAg84d1sEVmM/Kue9YBfER2pYigNWLv8mI1GktxbXg7gRNlSXsi7Veh9hkOsCTxeiOm5HlQ54qaUWGGvXbrn1tSdrWYwnKHRxdgRe0MDYkIMOASA7zl1nP5Wr3oVnIujkQQKBgQDauKFRdw9vkOlNEP9MDRCglTJL8y3JpWBFTVTKMOjVilscExfd80Vzp8tAZe2dQDIcj1aCzXetftue8FyZVBGi2leOHZ13GsXuMIiOs+SKlVHlpTkAa0BLcN1CS3tGdTg/HmtGogUDb/x7tY9bP9s3RZRsxtDUumXfFQID1aCvDwKBgQCxJXrlNn4SV3zfCL4jajcYnP6UjbbN240gjeoCGZVBEucY46dkw1U7HKYNzV8undgVo/W2D0kpf4LM+Hjcn1j0SBjymS7mdUAAKXITm8CMgXsZTAT3/7L4ogSIEsIc+/WWBm+3A8/jzuTH1NUWnjnIrjwDB0xkjXR2zUfP1Wp06wKBgQC32w+v3TdafyO+JpWUJj9d3UyET4yjvqJoXxLxS5/NMRpZRSuA5Sfyio/uOEA/OWFmZI9CMNlzO/n9ZutP8D1K+eMzkW12W0kaai3AVzO70r0fH34E/iLzx5IWUkDz/0Eivb1LiJJSS2afzwUMnWb21URpE88jovRTS+N/uanyvQKBgAWFsJYSo814krj1MdAy0HLg+gKxhEBYlsasBd5447E6oJ+jASXf/PkxxG9rtoriesj56n/5bANyKSawnDvsb751vOlbIx4mC4+1uwuncFIw+yBnwUPl4bNkgZWoWArFQ/ugSb0/zixA19ru5JGm7xA1dkN158i+rCHD9nghJAdvAoGBALToOKm6MFdTB2VtREQd2TuaSgam2cIkgyIUBOcWdnwKJ4Z61qERq9s0QSc4oCFJb57VaUzJxB6n0YIwjXMkcczqO1wOek3m16KWYYu2L4iXzpc5pYJAQPnIGQS0Foeb9Viz4sdvtInyVrScA4bIq4OEqFE6BfvPQqM/NVCMPsyU";
	private static final String STR_PUBLIC_KEY  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl1mtOHavqo19NkFTnxuJaXZYmA5k5WAHC3yICpd0EZbgZgAI8YbolWmtJHOvY1OvTHXASzwTdqHlmTy0wVHF3rhtzGFe17lkfdZsWfwSrzIZtcB2bYWGHEY0I1LGBitGl+nA6mlw/gxYS++MQXABL3BMtSfPmSIzhX7Dm6/roH/gFPC5gx6/fwVHXi4yjFFD8AB8Q2/B2kS9pqE+3Zx9yHezFKuM3mTQyJuJTTOzNZVlyDlhvvnE6qK10591i0U3swSxH3Lj2ULt+nB7eAK+JK1S85759xOCrS/ftWzAnPAn07jHxm1JW4Edy0Ho7GX7MjvX2MK0h3u6BXKi4Ql+xQIDAQAB";
	
	private static PrivateKey PRIVATE_KEY = RSAKey.loadPrivateKey(STR_PRIVATE_KEY);
	private static PublicKey  PUBLIC_KEY = RSAKey.loadPublicKey(STR_PUBLIC_KEY);
	
	/**
	 * Charger la clef publique
	 * @param strPublicKey  Clef publique encodée en Base64
	 * @return  vrai si la clef a bien été chargée
	 */
	public static boolean setRSAPublicKey(String strPublicKey) {
		PUBLIC_KEY = RSAKey.loadPublicKey(strPublicKey);
		return (PUBLIC_KEY != null);
	}
	/**
	 * Charger la clef privée
	 * @param strPrivateKey Clef privée encodée en Base64
	 * @return  vrai si la clef a bien étét chargée
	 */
	public static boolean setRSAPrivateKey(String strPrivateKey) {
		PRIVATE_KEY = RSAKey.loadPrivateKey(strPrivateKey);
		return (PRIVATE_KEY != null);
	}
	
	
	/** Générer un couple clef privée-publique
	 * @return
	 * @throws Exception
	 */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());
        KeyPair pair = generator.generateKeyPair();
        return pair;
    }

    /*
    public static KeyPair getKeyPairFromKeyStore() throws Exception {
        //Generated with:
        //  keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg RSA -keystore keystore.jks
    	
        InputStream ins = RSA.class.getResourceAsStream("/keystore.jks");
        
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(ins, "s3cr3t".toCharArray());   //Keystore password
        KeyStore.PasswordProtection keyPassword =       //Key password
                new KeyStore.PasswordProtection("s3cr3t".toCharArray());

        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("mykey", keyPassword);

        java.security.cert.Certificate cert = keyStore.getCertificate("mykey");
        PublicKey publicKey = cert.getPublicKey();
        PrivateKey privateKey = privateKeyEntry.getPrivateKey();

        return new KeyPair(publicKey, privateKey);
    }*/
    
    
    /**
     * Crypter un tableau d'octets via la clef publique 
     * @param clearByteArray
     * @return
     * @throws Exception
     */
    public static byte[] encryptByteArray(byte[] clearByteArray) throws Exception {
    	if (PUBLIC_KEY == null) return null;
        Cipher cipherEncrypt = Cipher.getInstance("RSA");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY);
        byte[] cryptedArray = cipherEncrypt.doFinal(clearByteArray);
        return cryptedArray;
    }

    public static byte[] decryptByteArray(byte[] cryptedArray) throws Exception {
    	if (PRIVATE_KEY == null) return null;
        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, PRIVATE_KEY);
        return decriptCipher.doFinal(cryptedArray);
    }
    
    /*
    public static byte[] encryptByteArray(byte[] clearByteArray) throws Exception {
        Cipher cipherEncrypt = Cipher.getInstance("RSA");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY);
        byte[] cipherText = cipherEncrypt.doFinal(plainText.getBytes(UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes), UTF_8);
    }
    */

    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(UTF_8));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(UTF_8));

        byte[] signatureBytes = Base64.getDecoder().decode(signature);

        return publicSignature.verify(signatureBytes);
    }
    
    private static void generateKeyAndSave() {
    	
    }

    public static void main(String... argv) throws Exception {
        //First generate a public/private key pair
    	System.out.println("Génération...");
        KeyPair pair = generateKeyPair();
        
        
        //pair.getPrivate();
    	System.out.println("Génération OK");

    	System.out.println("Vérification des clefs...");
        PrivateKey myPrivateKey = pair.getPrivate();
        PublicKey myPublicKey = pair.getPublic();
        
        String privStr = RSAKey.saveKey(myPrivateKey);
        PrivateKey loadedPrivateKey = RSAKey.loadPrivateKey(privStr);
        System.out.println("myPrivateKey bien lue : " + myPrivateKey.equals(loadedPrivateKey) + " len = " + privStr.length());
        
        String pubStr = RSAKey.saveKey(myPublicKey);
        PublicKey loadedPublicKey = RSAKey.loadPublicKey(pubStr);
        System.out.println("myPublicKey bien lue : " + myPublicKey.equals(loadedPublicKey) + " len = " + pubStr.length());

		String newLineChar = System.getProperty("line.separator");
        
        String myString = "--- PRIVATE KEY ---" + newLineChar + privStr + newLineChar + "--- PUBLIC KEY ---" + newLineChar + pubStr;
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        

        System.out.println("myString =  " + myString);
        //System.out.println("privStr2 =  " + privStr2);
        
        
        //KeyPair pair = getKeyPairFromKeyStore();

        //Our secret message
        String message = "the answer to life the universe and everything";
        /*
        //Encrypt the message
    	System.out.println("Encryption du message...");
        String cipherText = encrypt(message, pair.getPublic());
        System.out.println("cipherText : " + cipherText);
    	System.out.println("Encryption du message OK !");

    	System.out.println("Decryptage du message...");
        //Now decrypt it
        String decipheredMessage = decrypt(cipherText, pair.getPrivate());
    	System.out.println("Decryptage du message OK !");

        System.out.println(decipheredMessage);*/

        //Let's sign our message
        String signature = sign("foobar", pair.getPrivate());

        //Let's check the signature
        boolean isCorrect = verify("foobar", signature, pair.getPublic());
        System.out.println("Signature correct: " + isCorrect);
    }
}
