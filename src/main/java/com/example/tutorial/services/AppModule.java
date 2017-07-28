package com.example.tutorial.services;

import com.example.tutorial.domain.security.sevice.UserService;
import com.example.tutorial.domain.security.sevice.UserServiceImpl;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import java.io.IOException;
import org.apache.tapestry5.services.ComponentEventResultProcessor;
import org.apache.tapestry5.services.ComponentRequestFilter;

/**
 * This module is automatically included as part of the Tapestry IoC Registry,
 * it's a good place to configure and extend Tapestry, or to place your own
 * service definitions.
 */
public class AppModule {

	
	public static void bind(ServiceBinder binder) {
		// binder.bind(MyServiceInterface.class, MyServiceImpl.class);

		// Make bind() calls on the binder object to define most IoC services.
		// Use service builder methods (example below) when the implementation
		// is provided inline, or requires more initialization than simply
		// invoking the constructor.
	}

	/**
	 * Switch production mode true/false. In tapestry some capabilities not work
	 * until production mode false.
	 *
	 * @param configuration
	 */
	public static void contributeFactoryDefaults(
			MappedConfiguration<String, Object> configuration) {
		// The application version is incorporated into URLs for most assets. Web
		// browsers will cache assets because of the far future expires header.
		// If existing assets change (or if the Tapestry version changes) you
		// should also change this number, to force the browser to download new
		// versions. This overrides Tapesty's default (a random hexadecimal
		// number), but may be further overriden by DevelopmentModule or QaModule 
		// by adding the same key in the contributeApplicationDefaults method.
//		configuration.override(SymbolConstants.APPLICATION_VERSION, "1.0-SNAPSHOT");
		configuration.override(SymbolConstants.APPLICATION_VERSION, "0.8");
		configuration.override(SymbolConstants.PRODUCTION_MODE, false);

	}

	/**
	 * Add SymbolConstants.HOSTPORT and SymbolConstants.HOSTPORT_SECURE for use
	 * SSL. Add upload filesize-max 100 Mb.
	 *
	 * @param configuration
	 */
	public static void contributeApplicationDefaults(
			MappedConfiguration<String, Object> configuration) {
		// Contributions to ApplicationDefaults will override any contributions to
		// FactoryDefaults (with the same key). Here we're restricting the supported
		// locales to just "en" (English). As you add localised message catalogs and other assets,
		// you can extend this list of locales (it's a comma separated series of locale names;
		// the first locale name is the default when there's no reasonable match).
		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
		// because default port null
		configuration.add(SymbolConstants.HOSTPORT, "8080");
		configuration.add(SymbolConstants.HOSTPORT_SECURE, "8443");
		configuration.add("upload.filesize-max", "100000000");
//        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");

	}

	/**
	 * Use annotation or method naming convention:
	 * <code>contributeApplicationDefaults</code>. Add
	 * SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER for use JQuery.
	 */
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void setupEnvironment(MappedConfiguration<String, Object> configuration) {
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
		configuration.add(SymbolConstants.MINIFICATION_ENABLED, true);
		// Set the HMAC pass phrase to secure object data serialized to client
		configuration.add(SymbolConstants.HMAC_PASSPHRASE, "keep this secret");
	}

	/*
	// This will override the bundled bootstrap version and will compile it at runtime
	@Contribute(JavaScriptStack.class)
	@Core
	public static void overrideBootstrapCSS(OrderedConfiguration<StackExtension> configuration)
	{
		configuration.override("bootstrap.css",
				new StackExtension(StackExtensionType.STYLESHEET, "context:mybootstrap/css/bootstrap.css"), "before:tapestry.css");
	}
	 */
	/**
	 * This is a service definition, the service will be named "TimingFilter".
	 * The interface, RequestFilter, is used within the RequestHandler service
	 * pipeline, which is built from the RequestHandler service configuration.
	 * Tapestry IoC is responsible for passing in an appropriate Logger
	 * instance. Requests for static resources are handled at a higher level, so
	 * this filter will only be invoked for Tapestry related requests.
	 * <p/>
	 * <p/>
	 * Service builder methods are useful when the implementation is inline as
	 * an inner class (as here) or require some other kind of special
	 * initialization. In most cases, use the static bind() method instead.
	 * <p/>
	 * <p/>
	 * If this method was named "build", then the service id would be taken from
	 * the service interface and would be "RequestFilter". Since Tapestry
	 * already defines a service named "RequestFilter" we use an explicit
	 * service id that we can reference inside the contribution method.
	 */
	public RequestFilter buildTimingFilter(final Logger log) {
		return new RequestFilter() {
			public boolean service(Request request, Response response, RequestHandler handler)
					throws IOException {
				long startTime = System.currentTimeMillis();

				try {
					// The responsibility of a filter is to invoke the corresponding method
					// in the handler. When you chain multiple filters together, each filter
					// received a handler that is a bridge to the next filter.

					return handler.service(request, response);
				} finally {
					long elapsed = System.currentTimeMillis() - startTime;

					log.info(String.format("Request time: %d ms", elapsed));
				}
			}
		};
	}

	/**
	 * This is a contribution to the RequestHandler service configuration. This
	 * is how we extend Tapestry using the timing filter. A common use for this
	 * kind of filter is transaction management or security. The @Local
	 * annotation selects the desired service by type, but only from the same
	 * module. Without @Local, there would be an error due to the other
	 * service(s) that implement RequestFilter (defined in other modules).
	 */
	public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,
			@Local RequestFilter filter) {
		// Each contribution to an ordered configuration has a name, When necessary, you may
		// set constraints to precisely control the invocation order of the contributed filter
		// within the pipeline.

		configuration.add("Timing", filter);
	}

	/**
	 * For write file in server. Adds ComponentEventResultProcessors.
	 *
	 * @param configuration the configuration where new
	 * ComponentEventResultProcessors are registered by the type they are
	 * processing
	 * @param response the response that the event result processor handles
	 */
	public void contributeComponentEventResultProcessor(MappedConfiguration<Class<?>, ComponentEventResultProcessor<?>> configuration, Response response) {
		configuration.add(OutputStreamResponse.class, new OutputStreamResponseResultProcessor(response));
	}

	/**
	 * For protect page and use authorisation.
	 *
	 * @param configuration
	 */
	public void contributeComponentRequestHandler(OrderedConfiguration<ComponentRequestFilter> configuration) {
		configuration.addInstance("PageProtectionFilter", PageProtectionFilter.class);
	}
}
