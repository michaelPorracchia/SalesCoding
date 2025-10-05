package fr.itsf.sales.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Profile("test")
public class TestController {

    @GetMapping("/internal-server-error")
    public ResponseEntity<Void> testInternalServerError() {
        throw new RuntimeException("Test exception for 500 error");
    }

    @GetMapping("/bad-request")
    public ResponseEntity<Void> testBadRequest() {
        throw new IllegalArgumentException("Test exception for 400 error");
    }
}