/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tutorial.services;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Encryption test
 *
 * @author roma
 */
public class EncryptionTest extends Assert {

	Encryption encr;

	private final String EXPECTED_ENCRYPTION = "d8578edf8458ce06fbc5bb76a58c5ca4";
	private final String TEST_STRING = "qwerty";

	@Before
	public void init() {
		encr = new Encryption();
	}

	@Test
	public void testThatEncryptingStringResultsInExpectedString() {

		String encryption = encr.encrypt(TEST_STRING);
		assertEquals(EXPECTED_ENCRYPTION, encryption);
	}
}
