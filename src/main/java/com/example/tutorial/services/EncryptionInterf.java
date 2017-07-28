package com.example.tutorial.services;


/**
 * Class for encryption. User for encrypt user password
 *
 */
public interface EncryptionInterf {

	

	/**
	 * @param password string for crypt
	 * @return encrypted string
	 */
	public String encrypt(String password);
}
