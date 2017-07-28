package com.example.tutorial.services;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

/**
 * Class for encryption. User for encrypt user password
 *
 */
@Service
public class Encryption implements EncryptionInterf {

	private static final int LENGTH = 16;
	private static MessageDigest md5;

	/**
	 * @param password string for crypt
	 * @return encrypted string
	 */
	@Override
	public String encrypt(String password) {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			java.util.logging.Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
		}
		md5.update(password.getBytes(), 0, password.length());
		return new BigInteger(1, md5.digest()).toString(LENGTH);
	}


}
