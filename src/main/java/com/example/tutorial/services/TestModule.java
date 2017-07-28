package com.example.tutorial.services;

import com.example.tutorial.domain.security.User;
import com.example.tutorial.domain.security.sevice.UserService;
import com.example.tutorial.domain.security.sevice.UserServiceImpl;
import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * Class use only in test
 *
 * @author roma
 */
public class TestModule extends AppModule {

	/**
	 * Determines only for run test. Tapestry can bind automaticly, but not in
	 * test.
	 *
	 * @param binder
	 */
	public static void bind(ServiceBinder binder) {
		binder.bind(EncryptionInterf.class, Encryption.class);
		binder.bind(UserService.class, UserServiceImpl.class);
	}
}
