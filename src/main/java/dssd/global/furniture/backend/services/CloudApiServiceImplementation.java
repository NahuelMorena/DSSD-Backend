package dssd.global.furniture.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dssd.global.furniture.backend.controllers.dtos.OffertsByApiDTO;
import dssd.global.furniture.backend.services.interfaces.CloudApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudApiServiceImplementation implements CloudApiService {
    private final String apiUrl = "https://dssdapi.onrender.com/api/";
    private String authToken;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Override
    public String authenticate() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("auth/login")
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "usuario");
        credentials.put("password", "1234");
        try {
            String jsonBody = new ObjectMapper().writeValueAsString(credentials);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                 url, HttpMethod.POST, entity, String.class
            );

            String responseBody = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBody);
            if (jsonNode.has("token")) {
                this.authToken = jsonNode.get("token").asText();
                System.out.println("Token: " + this.authToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.authToken;
    }
    @Override
    public List<OffertsByApiDTO> getOffersByMaterial(String materialName, String date) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        if (authToken == null){
            throw new RuntimeException("Token de autenticación no disponible");
        }

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("offersMaterial")
                .queryParam("materialName", materialName)
                .queryParam("dateStartManufacture", date)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<OffertsByApiDTO[]> responseEntity = restTemplate.exchange(
                    url,HttpMethod.GET,entity,OffertsByApiDTO[].class
            );
            OffertsByApiDTO[] response = responseEntity.getBody();
            return Arrays.asList(response);

        } catch (HttpClientErrorException.Forbidden e) {
            e.printStackTrace();
            throw new RuntimeException("Error 403, error de autenticacion JWT");
        }
    }
}
