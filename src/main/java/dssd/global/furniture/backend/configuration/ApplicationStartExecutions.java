package dssd.global.furniture.backend.configuration;

import java.util.Arrays;

import org.bonitasoft.engine.api.APIClient;
import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.flownode.TaskInstance;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.bpm.process.ProcessInstance;
import org.bonitasoft.engine.bpm.process.impl.ProcessDefinitionBuilder;
import org.bonitasoft.engine.bpm.process.impl.internal.ProcessDefinitionImpl;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.platform.LoginException;
import org.bonitasoft.engine.session.APISession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import dssd.global.furniture.backend.filters.AuthFilter;
import dssd.global.furniture.backend.services.BonitaService;


@Configuration

public class ApplicationStartExecutions implements ApplicationRunner {
	@Autowired
	BonitaService bonitaService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		IdentityAPI identityAPI= this.bonitaService.getIdentityAPI();
		APISession apiSession=this.bonitaService.getSession();
		ProcessAPI processAPI= this.bonitaService.getProcessAPI();
		
		System.out.println(" ID apiSession ==== " + apiSession.getUserName());
		
		User user= bonitaService.getCurrentLoggedInUser();
		System.out.println("ID usuario:" + user.getId() + " PRIMER NOMBRE:" +user.getFirstName() + " APELLIDO: " + user.getLastName());
		
		System.out.println("LISTA DE PROCESOS DEPLOYADOS:" +
		this.bonitaService.getLast100DeployedProcess().getResult().toString() + " SIZE: " +this.bonitaService.getLast100DeployedProcess().getCount() );
		
	}
	
	@Bean
	public FilterRegistrationBean<AuthFilter> loggingFilter(){
	    FilterRegistrationBean<AuthFilter> registrationBean 
	      = new FilterRegistrationBean<>();
	        
	    registrationBean.setFilter(new AuthFilter());
	    registrationBean.addUrlPatterns("/api/*");
	        
	    return registrationBean;    
	}
	
	@Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowedMethods(Arrays.asList("POST", "OPTIONS", "GET", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
	

	    
}
