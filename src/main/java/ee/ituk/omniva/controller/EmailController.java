package ee.ituk.omniva.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/omniva")
public class EmailController {

    @GetMapping("/alo")
    public void sendEmail() {
        System.out.println("yo");
    }

}
