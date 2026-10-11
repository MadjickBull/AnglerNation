package com.anglernation.testpr;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestPRController {
    @GetMapping("/test-pr")
    public String testPR() {
        return "Test PR successful";
    }
}
