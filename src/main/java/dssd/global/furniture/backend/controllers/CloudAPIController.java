package dssd.global.furniture.backend.controllers;

import dssd.global.furniture.backend.services.interfaces.CloudApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CloudAPIController {

    @Autowired
    private CloudApiService cloudApiService;

    private final String baseUrl = "/api/cloudapi";

    @GetMapping(baseUrl + "/login")
    public HttpEntity<String> login() {
        return ResponseEntity.ok(cloudApiService.authenticate());
    }
}
