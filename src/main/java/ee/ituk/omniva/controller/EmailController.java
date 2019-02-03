package ee.ituk.omniva.controller;

import ee.ituk.omniva.mailService.EmailService;
import ee.ituk.omniva.userService.UserService;
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

    @PostMapping("/newMinion")
    public void sendNewMinionEmail(Integer mentorId, Integer minionId) {
        String to = userService.getEmailRecipientAadress(mentorId);
        String minionName = userService.getMinionName(minionId);
        String minionEmail = userService.getMinionName(minionId);
//        emailService.sendEmail(to, minionName, minionEmail);
        emailService.sendEmail("martinkalvikk@gmail.com","BOI", "BOI.COM");
        ResponseEntity.status(HttpStatus.OK).body(null);

    }

    @PostMapping("/newPassword/id={id}&uuid={uuid}")
    public void sendPasswordEmail(@PathVariable("id") Integer id, @PathVariable("uuid") String uuid) {
        String to = userService.getMinionEmail(id);
        emailService.sendPasswordEmail(to,uuid);
        ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
