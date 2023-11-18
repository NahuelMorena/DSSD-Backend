package dssd.global.furniture.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dssd.global.furniture.backend.controllers.dtos.api.DateSpaceApiDTO;
import dssd.global.furniture.backend.controllers.dtos.api.OffersByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.OffersToReserveDTO;
import dssd.global.furniture.backend.controllers.dtos.api.ReserveByApiDTO;
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
    public List<OffersByApiDTO> getOffersByMaterial(String materialName, String date) {
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
            ResponseEntity<OffersByApiDTO[]> responseEntity = restTemplate.exchange(
                    url,HttpMethod.GET,entity, OffersByApiDTO[].class
            );
            OffersByApiDTO[] response = responseEntity.getBody();
            return Arrays.asList(response);

        } catch (HttpClientErrorException.Forbidden e) {
            e.printStackTrace();
            throw new RuntimeException("Error 403, error de autenticacion JWT");
        }
    }

    @Override
    public ReserveByApiDTO reserveMaterials(OffersToReserveDTO.Offer offer) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        if (authToken == null){
            throw new RuntimeException("Token de autenticación no disponible");
        }

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("reserveMaterials/reserve/")
                .toUriString();


        Map<String, String> data = new HashMap<>();
        data.put("idProviderOfferMaterial", offer.getIdProviderOfferMaterial().toString());
        data.put("quantity", offer.getQuantity().toString());

        try {
            String jsonBody = new ObjectMapper().writeValueAsString(data);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(this.authToken);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class
            );

            String responseBody = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseBody, ReserveByApiDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DateSpaceApiDTO> getDateSpaces() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        if (authToken == null){
            throw new RuntimeException("Token de autenticación no disponible");
        }

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("/dateSpaces/getAvailableSpaces")
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<DateSpaceApiDTO[]> responseEntity = restTemplate.exchange(
                    url,HttpMethod.GET,entity, DateSpaceApiDTO[].class
            );
            DateSpaceApiDTO[] response = responseEntity.getBody();
            return Arrays.asList(response);

        } catch (HttpClientErrorException.Forbidden e) {
            e.printStackTrace();
            throw new RuntimeException("Error 403, error de autenticacion JWT");
        }
    }
}
