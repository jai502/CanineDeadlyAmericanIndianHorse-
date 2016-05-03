package encryptionRSA;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSAEncryptDecrypt {

	private KeyPairGenerator keyPairGenerator;
	private KeyPair keyPair;
	private KeyFactory keyCreateFactory;
	private static KeyFactory keyEncryptFactory;
	private static KeyFactory keyDecryptFactory;
	private RSAPublicKeySpec pubTemp;
	private RSAPrivateKeySpec privTemp;

	private int keySize = 4096;
	private static BigInteger encryptModulus;
	private static BigInteger encryptExponent;
	private static BigInteger decryptModulus;
	private static BigInteger decryptExponent;



	public static void main(String[] args){
	}

	// Method to create RSA keypair of desired length and save them to hard files
	public void createKeys() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException{

		//Create key pair generator with suitable bit length and get public and private key (Server)
		keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(keySize);
		keyPair = keyPairGenerator.genKeyPair();

		//Create "Soft keys" if required (Currently set up for hard keys) This method will need to
		//be adjusted to return these keys if soft keys are required
		//Key publicKey = keyPair.getPublic();
		//Key privateKey = keyPair.getPrivate();

		// Get the key pair details and save to hard files for use with encrypting and decrypting
		keyCreateFactory = KeyFactory.getInstance("RSA");
		pubTemp = keyCreateFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
		privTemp = keyCreateFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);

		saveToFile("bin/public.key", pubTemp.getModulus(), pubTemp.getPublicExponent());
		saveToFile("bin/private.key", privTemp.getModulus(), privTemp.getPrivateExponent());
	}

	// Method to save keypair details to hard files for distribution as required
	// Note: By default the keys are saved to the bin folder within the project
	public static void saveToFile(String fileName, BigInteger mod, BigInteger exp) throws IOException {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
		try {
			objectOutputStream.writeObject(mod);
			objectOutputStream.writeObject(exp);
		} catch (Exception e) {
			throw new IOException("ERROR: Unable to write modulus or exponent details to file", e);
		} finally {
			objectOutputStream.close();
		}
	}

	// Encrypt key (Client)
	static PublicKey readEncryptKeyFromFile(String keyFileName) throws IOException {
		InputStream in = RSAEncryptDecrypt.class.getResourceAsStream(keyFileName);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		try {
			encryptModulus = (BigInteger) oin.readObject();
			encryptExponent = (BigInteger) oin.readObject();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(encryptModulus, encryptExponent);
			keyEncryptFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyEncryptFactory.generatePublic(keySpec);
			return pubKey;
		} catch (Exception e) {
			throw new RuntimeException("Error reading public key from file", e);
		} finally {
			oin.close();
		}
	}

	// Decrypt key (Server)
	static PrivateKey readDecryptKeyFromFile(String keyFileName) throws IOException {
		InputStream in = RSAEncryptDecrypt.class.getResourceAsStream(keyFileName);
		ObjectInputStream oin = new ObjectInputStream(new BufferedInputStream(in));
		try {
			decryptModulus = (BigInteger) oin.readObject();
			decryptExponent = (BigInteger) oin.readObject();
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(decryptModulus, decryptExponent);
			keyDecryptFactory = KeyFactory.getInstance("RSA");
			PrivateKey privKey = keyDecryptFactory.generatePrivate(keySpec);
			return privKey;
		} catch (Exception e) {
			throw new RuntimeException("Error reading private key from file", e);
		} finally {
			oin.close();
		}
	}

	// Method to encrypt raw data passed in and return encrypted data (Client)
	public static byte[] rsaEncrypt(byte[] data) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		PublicKey pubKey = readEncryptKeyFromFile("/public.key");
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] cipherData = cipher.doFinal(data);
		return cipherData;
	}

	// Method to decrypt encrypted data passed in and return normal data (Server)
	public static byte[] rsaDecrypt(byte[] cipherData) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		PrivateKey privKey = readDecryptKeyFromFile("/private.key");
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		byte[] data = cipher.doFinal(cipherData);
		return data;
	}
}
