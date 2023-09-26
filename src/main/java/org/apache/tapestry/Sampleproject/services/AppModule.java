package org.apache.tapestry.Sampleproject.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.commons.MappedConfiguration;
import org.apache.tapestry5.commons.OrderedConfiguration;
import org.apache.tapestry5.http.services.Request;
import org.apache.tapestry5.http.services.RequestFilter;
import org.apache.tapestry5.http.services.RequestHandler;
import org.apache.tapestry5.http.services.Response;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.ImportModule;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.modules.Bootstrap4Module;
import org.slf4j.Logger;
import com.example.service.Userservice;
import com.example.service.UserserviceImp;
import org.apache.tapestry5.ioc.ServiceBinder;
import java.io.IOException;


@ImportModule(Bootstrap4Module.class)
public class AppModule {

    public static void bind(ServiceBinder binder) {
    	binder.bind(Userservice.class, UserserviceImp.class);
     }
    
    public static void contributeFactoryDefaults(MappedConfiguration<String, Object> configuration) {
     
        configuration.override(SymbolConstants.APPLICATION_VERSION, "0.0.1-SNAPSHOT");

        configuration.override(SymbolConstants.PRODUCTION_MODE, false);
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {   	
    	
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
    
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "Mysecret123");
        configuration.add(SymbolConstants.ENABLE_PAGELOADING_MASK, Boolean.FALSE);
        configuration.add(SymbolConstants.ENABLE_HTML5_SUPPORT, Boolean.FALSE);
        
    }

	
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void setupEnvironment(MappedConfiguration<String, Object> configuration) {
       
		configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
	}


    public RequestFilter buildTimingFilter(final Logger log) {
        return new RequestFilter() {
            public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
                long startTime = System.currentTimeMillis();

                try {
             
                    return handler.service(request, response);
                } finally {
                    long elapsed = System.currentTimeMillis() - startTime;
                    log.info("Request time: {} ms", elapsed);
                }
            }
        };
    }

    @Contribute(RequestHandler.class)
    public void addTimingFilter(OrderedConfiguration<RequestFilter> configuration, @Local RequestFilter filter) {
    	configuration.add("Timing", filter);
    }
}
