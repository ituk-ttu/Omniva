package ee.ituk.omniva.controller;

import ee.ituk.omniva.mailService.EmailService;
import ee.ituk.omniva.userService.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("sendEmail")
    public void launchEmail(Integer mentorId, Integer minionId) {
        String to = userService.getEmailRecipientAadress(mentorId);
        String minionName = userService.getMinionName(minionId);
        String minionEmail = userService.getMinionName(minionId);
        emailService.sendEmail(to,minionName, minionEmail); // TODO: FIX PARAMETERS
    }

}
