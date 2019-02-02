package ee.ituk.omniva.controller;

import ee.ituk.omniva.mailService.EmailService;
import ee.ituk.omniva.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/omniva")
public class EmailController {

    @Resource
    EmailService emailService;

    @Resource
    UserService userService;

    @GetMapping("/alo")
    public void sendEmail() {
        System.out.println("yo");
    }

    @PostMapping("/newMinion")
    public void sendNewMinionEmail(Integer mentorId, Integer minionId) {
        String to = userService.getEmailRecipientAadress(mentorId);
        String minionName = userService.getMinionName(minionId);
        String minionEmail = userService.getMinionName(minionId);
        emailService.sendEmail("r.ryngenen@gmail.com","BOI", "BOI.COM");
        ResponseEntity.status(HttpStatus.OK).body(null);

    }

    @GetMapping("/newPassword/{id}")
    public void sendPasswordEmail(@PathVariable Integer id) {
        String to = userService.getMinionEmail(id);
        emailService.sendPasswordEmail(to);
        ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
