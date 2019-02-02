package ee.ituk.omniva.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @GetMapping
    public void sendEmail() {
        System.out.println("yo");
    }
}
