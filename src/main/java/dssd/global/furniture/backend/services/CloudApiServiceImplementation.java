package dssd.global.furniture.backend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dssd.global.furniture.backend.controllers.dtos.api.DateSpaceApiDTO;
import dssd.global.furniture.backend.controllers.dtos.api.OffersByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.OffersToReserveDTO;
import dssd.global.furniture.backend.controllers.dtos.api.ReserveByApiDTO;
import dssd.global.furniture.backend.controllers.dtos.request.QueryRequestDTO;
import dssd.global.furniture.backend.controllers.dtos.request.ReserveDateSpaceRequestDTO;
import dssd.global.furniture.backend.services.interfaces.CloudApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CloudApiServiceImplementation implements CloudApiService {
    private final String apiUrl = "https://dssdapi.onrender.com/api/";
    private String authToken;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    private RestTemplate getRestTemplate(){
        return restTemplateBuilder.build();
    }
    private void verifyToken(){
        if (this.authToken == null){
            throw new RuntimeException("Token de autenticación no disponible");
        }
    }
    
    public boolean isLogged() {
    	return ! (this.authToken==null);
    }

    private HttpEntity<String> assembleHeader(String jsonBody){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(this.authToken);
        if (jsonBody == null) {
            return new HttpEntity<>(headers);
        }
        return new HttpEntity<>(jsonBody, headers);
    }

    @Override
    public String authenticate() {

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

            ResponseEntity<String> responseEntity = this.getRestTemplate().exchange(
                 url, HttpMethod.POST, entity, String.class
            );

            String responseBody = responseEntity.getBody();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseBody);
            if (jsonNode.has("token")) {
                this.authToken = jsonNode.get("token").asText();
                //System.out.println("Token: " + this.authToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.authToken;
    }
    @Override
    public List<OffersByApiDTO> getOffersByMaterial(String materialName, String date) {
        this.verifyToken();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("offersMaterial")
                .queryParam("materialName", materialName)
                .queryParam("dateStartManufacture", date)
                .toUriString();

        try {
            ResponseEntity<OffersByApiDTO[]> responseEntity = this.getRestTemplate().exchange(
                    url, HttpMethod.GET, this.assembleHeader(null), OffersByApiDTO[].class
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
        this.verifyToken();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("reserveMaterials/reserve/")
                .toUriString();

        try {
            Map<String, String> data = new HashMap<>();
            data.put("idProviderOfferMaterial", offer.getIdProviderOfferMaterial().toString());
            data.put("quantity", offer.getQuantity().toString());
            String jsonBody = new ObjectMapper().writeValueAsString(data);


            ResponseEntity<String> responseEntity = this.getRestTemplate().exchange(
                    url, HttpMethod.POST, this.assembleHeader(jsonBody), String.class
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
        this.verifyToken();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("/dateSpaces/getAvailableSpaces")
                .toUriString();


        try {
            ResponseEntity<DateSpaceApiDTO[]> responseEntity = this.getRestTemplate().exchange(
                    url, HttpMethod.GET, this.assembleHeader(null), DateSpaceApiDTO[].class
            );
            DateSpaceApiDTO[] response = responseEntity.getBody();
            return Arrays.asList(response);

        } catch (HttpClientErrorException.Forbidden e) {
            e.printStackTrace();
            throw new RuntimeException("Error 403, error de autenticacion JWT");
        }
    }

    @Override
    public DateSpaceApiDTO reserveDateSpace(ReserveDateSpaceRequestDTO reserveDateSpace) {
        this.verifyToken();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("dateSpaces/reserveManufacturingSpace/")//
                .path(String.valueOf(reserveDateSpace.getDateSpace_id()))
                .toUriString();

        try {
            Map<String, List<ReserveDateSpaceRequestDTO.ReserveID>> requestBody =
                    Collections.singletonMap("reserves", reserveDateSpace.getReserves());

            String jsonBody = new ObjectMapper().writeValueAsString(requestBody);

            ResponseEntity<String> responseEntity = this.getRestTemplate().exchange(
                    url, HttpMethod.PUT, this.assembleHeader(jsonBody), String.class
            );

            String responseBody = responseEntity.getBody();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(responseBody, DateSpaceApiDTO.class);
        } catch (HttpClientErrorException.Forbidden e) {
            throw new RuntimeException("Error 403, error de autenticación JWT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean checkExistenceOfDelays(List<Long> list) {
        this.authenticate();
        this.verifyToken();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("reserveMaterials/queryExistanceOfDelays")
                .toUriString();

        try {
            //cargar la información
            QueryRequestDTO queryRequestDTO = new QueryRequestDTO(list);
            ObjectMapper m = new ObjectMapper();
            String jsonBody = m.writeValueAsString(queryRequestDTO);

            ResponseEntity<Boolean> responseEntity = this.getRestTemplate().exchange(
                    url, HttpMethod.POST, this.assembleHeader(jsonBody), Boolean.class
            );

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean checkArrivalOfAllMaterials(Long reserve_id) {
        this.authenticate();
        this.verifyToken();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("reserveMaterials/checkArrivalOfAllMaterials")
                .toUriString();
        try {
            //cargar la información
            Map<String, String> data = new HashMap<>();
            data.put("id", reserve_id.toString());
            String jsonBody = new ObjectMapper().writeValueAsString(data);

            ResponseEntity<Boolean> responseEntity = this.getRestTemplate().exchange(
                    url, HttpMethod.POST, this.assembleHeader(jsonBody), Boolean.class
            );

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean checkAvailableManufacturingSpace() {
        this.authenticate();
        this.verifyToken();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("dateSpaces/checkAvailableManufacturingSpace")
                .toUriString();

        try {
            ResponseEntity<Boolean> responseEntity = this.getRestTemplate().exchange(
                    url, HttpMethod.GET, this.assembleHeader(null), Boolean.class
            );
            return responseEntity.getBody();
        } catch (HttpClientErrorException.Forbidden e) {
            e.printStackTrace();
            throw new RuntimeException("Error 403, error de autenticacion JWT");
        }
    }

    @Override
    public Boolean manufacturingCompletionInquiry(Long reserve_id) {
        this.authenticate();
        this.verifyToken();

        String url = UriComponentsBuilder.fromHttpUrl(this.apiUrl)
                .path("dateSpaces/manufacturingCompletionInquiry")
                .toUriString();
        try {
            //cargar la información
            Map<String, String> data = new HashMap<>();
            data.put("reserve_id", reserve_id.toString());
            String jsonBody = new ObjectMapper().writeValueAsString(data);

            ResponseEntity<Boolean> responseEntity = this.getRestTemplate().exchange(
                    url, HttpMethod.POST, this.assembleHeader(jsonBody), Boolean.class
            );

            return responseEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
