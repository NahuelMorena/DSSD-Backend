package dssd.global.furniture.backend.configuration;

import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.engine.api.LoginAPI;
import org.bonitasoft.engine.api.TenantAPIAccessor;

import java.util.HashMap;
import java.util.Map;

import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.ApiAccessType;
import org.bonitasoft.engine.util.APITypeManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dssd.global.furniture.backend.services.BonitaService;

@Configuration
public class BonitaConfig {
	@Bean
    public APIClient bonitaAPISession() throws Exception {
		
		// Let's set the connection settings to use HTTP on the already running Bonita runtime:
    	Map<String, String> settings = new HashMap<String, String>();
    	settings.put("server.url", "http://localhost:60000");
    	settings.put("application.name", "bonita");
    	// HTTP Basic Auth is active by default on server-side:
    	settings.put("basicAuthentication.active", "true");
    	settings.put("basicAuthentication.username", "http-api");  // default value, can be changed server-side in file <BONITA>/server/conf/tomcat-users.xml
    	settings.put("basicAuthentication.password", "h11p-@p1");  // default value, can be changed server-side in file <BONITA>/server/conf/tomcat-users.xml
    	APITypeManager.setAPITypeAndParams(ApiAccessType.HTTP, settings);
		System.out.println(settings.toString());
    	// First of all, let's log in on the engine:
    	org.bonitasoft.engine.api.APIClient apiClient = new APIClient();
		apiClient.login("walter.bates", "bpm"); // use "install" / "install" if you don't have any other user created
    	
    	return apiClient;
	}

	@Bean
	public BonitaService getBonitaService(){
		return new BonitaService();
	}


   
}