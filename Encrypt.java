import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;
/*
*	First version created by:	Peter Mills
*	Date of first version:		25/02/16
*
*	Last updated by:			Peter Mills
*	Date of last update:		25/02/16
*	Version Number:				1
*
*	Commit date:	25/02/16
*	Description: 	Encryption classes for password hashing etc.
*/
public class Encrypt
{
	// Iterations dictates how expensive the hashing is
	private static final int iterations = 20*1000;
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;
	
	public Encrypt()
	{
		// TEST THE HASHING CLASSES
		String hashedPassword = null;
		boolean passwordCheck = false;
		
		// Hash
		try {
			hashedPassword= getSaltedHash("testpassword");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(hashedPassword);
		
		// Check
		try {
			passwordCheck = check("testpassword", hashedPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Password check result: " + passwordCheck);
		
	}
	
	
	// Hash password with salt as key
	public static String hash(String password, byte[] salt) throws Exception
	{	
		// Check the entered password is valid
		if (password == null || password.length() == 0)
		{
			throw new IllegalArgumentException("Empty passwords are not allowed");
		}
		// Initialise key generator with desired encryption
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		// Generate key and hash password
		SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
		// Return hashed password
		return Base64.encodeBase64String(key.getEncoded());
	}
	
	// Combine hashed password with salt key
	public static String getSaltedHash(String password) throws Exception
	{	
		// Generate salt 
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
		// Append salt to password
		return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
	}
	
	// Check stored hash against entered password
	public static boolean check(String password, String stored) throws Exception
	{
		// Separate salt and hashed password
		String[] saltAndPass = stored.split("\\$");
		// Check that saltAndPass array only contains two elements
		if (saltAndPass.length != 2)
		{
			throw new IllegalStateException("The stored password should have the form 'salt$hash'");	
		}
		// Hash user entered password based on stored salt
		String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
		// Check hashed entered password against stored password and return result
		return hashOfInput.equals(saltAndPass[1]);
	}
	
	public static void main(String[] args) {
		new Encrypt();
	}
	
}