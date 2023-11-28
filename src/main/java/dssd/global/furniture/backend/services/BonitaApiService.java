package dssd.global.furniture.backend.services;

import java.util.List;

import org.bonitasoft.engine.bpm.data.impl.DataDefinitionImpl;
import org.bonitasoft.engine.bpm.process.impl.internal.ArchivedProcessInstanceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import dssd.global.furniture.backend.controllers.dtos.apiBonita.ArchivedCases;
import dssd.global.furniture.backend.controllers.dtos.apiBonita.VariableBonita;
import jakarta.servlet.http.Cookie;

@Service
public class BonitaApiService implements dssd.global.furniture.backend.services.interfaces.BonitaApiService {
	
	private final String apiUrl = "http://localhost:60000/bonita";
	private static String authToken="";
	private static String jsessionid="";
	@Autowired
    RestTemplateBuilder restTemplateBuilder;
	
	public void login() {
		RestTemplate restTemplate = restTemplateBuilder.build();
        String loginUrl = apiUrl + "/loginservice";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", "april.sanchez");
        body.add("password", "bpm");
        body.add("redirect", "false");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(loginUrl, request, String.class);
        HttpHeaders headersResponse=responseEntity.getHeaders();
        List<String> headerValues = headersResponse.get("Set-Cookie"); // Obtener todos los valores del encabezado "Set-Cookie"
        if (headerValues != null) {
            for (String headerValue : headerValues) {
                if (headerValue.contains("X-Bonita-API-Token") || headerValue.contains("JSESSIONID")) {
                    String[] cookieElements = headerValue.split(";");
                    for (String element : cookieElements) {
                        if (element.trim().startsWith("X-Bonita-API-Token")) {
                            String[] tokenParts = element.trim().split("=");
                            if (tokenParts.length == 2) {
                                String apiToken = tokenParts[1];
                                authToken=apiToken;
                            }
                        }else if(element.trim().startsWith("JSESSIONID")) {
                        	String[] tokenParts = element.trim().split("=");
                            if (tokenParts.length == 2) {
                                jsessionid = tokenParts[1];
                            }
                        }
                    }
                }
            }
        }
    }
	
	public VariableBonita getIdCollectionCase(String caseId) {
		RestTemplate restTemplate = restTemplateBuilder.build();
	    String caseVariableUrl = apiUrl + "/api/bpm/caseVariable/" + caseId + "/id_collection";
	    HttpHeaders headers = new HttpHeaders();
	    Cookie cookie = new Cookie("X-Bonita-API-Token", authToken);
	    Cookie cookiej = new Cookie("JSESSIONID", jsessionid);
	    headers.add(HttpHeaders.COOKIE, cookie.getName()+"="+cookie.getValue());
	    headers.add(HttpHeaders.COOKIE, cookiej.getName()+"="+cookiej.getValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<VariableBonita> responseEntity = restTemplate.exchange(
            caseVariableUrl, HttpMethod.GET, entity, VariableBonita.class);
        
        return responseEntity.getBody();
	}
	
	public List<ArchivedCases> getArchivedProcessInstances(){
		RestTemplate restTemplate = restTemplateBuilder.build();
		String url= apiUrl+"/api/bpm/archivedCase?p=0&c=100";
		HttpHeaders headers = new HttpHeaders();
	    Cookie cookie = new Cookie("X-Bonita-API-Token", authToken);
	    Cookie cookiej = new Cookie("JSESSIONID", jsessionid);
	    headers.add(HttpHeaders.COOKIE, cookie.getName()+"="+cookie.getValue());
	    headers.add(HttpHeaders.COOKIE, cookiej.getName()+"="+cookiej.getValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<ArchivedCases>> responseEntity = restTemplate.exchange(
        	    url,
        	    HttpMethod.GET,
        	    entity,
        	    new ParameterizedTypeReference<List<ArchivedCases>>() {}
        	);
    	return responseEntity.getBody();
	}

	
	public boolean isAuthenticated() {
		return ! BonitaApiService.authToken.isBlank() ;
	}
	
}

