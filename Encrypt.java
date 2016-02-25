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
*	Commit date:
*	Description:
*/
public class Encrypt
{
	// Iterations dictates how expensive the hashing is
	private static final int iterations = 20*1000;
	private static final int saltLen = 32;
	private static final int desiredKeyLen = 256;
	
	public Encrypt()
	{
		String hashedPassword = null;
		boolean passwordCheck = false;
		
		// Hash
		try {
			hashedPassword= getSaltedHash("testpassword");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(hashedPassword);
		
		// Check
		try {
			passwordCheck = check("testpassword", hashedPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Password check result " + passwordCheck);
		
	}
	
	
	// Hash the password
	public static String hash(String password, byte[] salt) throws Exception
	{
		if (password == null || password.length() == 0)
		{
			throw new IllegalArgumentException("Empty passwords are not supported.");
		}
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
		return Base64.encodeBase64String(key.getEncoded());
	}
	
	// Combine salt with Hash
	public static String getSaltedHash(String password) throws Exception
	{
		byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
		// Append salt to password
		return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
	}
	
	public static boolean check(String password, String stored) throws Exception
	{
		String[] saltAndPass = stored.split("\\$");
		if (saltAndPass.length != 2)
		{
			throw new IllegalStateException("The stored password should have the form 'salt$hash'");	
		}
		String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
		return hashOfInput.equals(saltAndPass[1]);
	}
	
	public static void main(String[] args) {
		new Encrypt();
	}
	
}