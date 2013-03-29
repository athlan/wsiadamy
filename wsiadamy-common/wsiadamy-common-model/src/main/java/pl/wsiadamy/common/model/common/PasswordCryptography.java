package pl.wsiadamy.common.model.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordCryptography
{
	public static boolean compare(String passrowdOriginal, String passwordToCompare, String passwordSalt)
	{
		if(null == passrowdOriginal || null == passwordToCompare || null == passwordSalt)
			return false;
		
		return passrowdOriginal.equals(encode(passwordToCompare, passwordSalt));
	}
	
	public static String getSalt(String password)
	{
		return getSalt(password, 3);
	}
	
	public static String getSalt(String password, int length)
	{
		MessageDigest m = null;
		
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
		
		int randomStringTokenizerLength = 3;
		String randomString = System.currentTimeMillis() + password.substring(0, (password.length() < randomStringTokenizerLength) ? password.length() : randomStringTokenizerLength);
		
		m.update(randomString.getBytes(),0, randomString.length());
		
		randomString = new BigInteger(1,m.digest()).toString(16);
		
		return randomString.substring(0, length);
	}
	
	public static String encode(String password, String passwordSalt)
    {
		MessageDigest m = null;
		
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
		
		String randomString = password + passwordSalt;

		m.update(randomString.getBytes(),0, randomString.length());
		randomString = new BigInteger(1,m.digest()).toString(16);
		
		randomString = passwordSalt + randomString;
		
		m.update(randomString.getBytes(),0, randomString.length());
		randomString = new BigInteger(1,m.digest()).toString(16);
		
        return randomString;
    }
	    
}
