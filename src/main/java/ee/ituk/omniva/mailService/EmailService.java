package ee.ituk.omniva.mailService;

import ee.ituk.omniva.config.MailgunConfiguration;
import ee.ituk.omniva.userService.UserService;
import lombok.RequiredArgsConstructor;
import net.sargue.mailgun.*;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmailService {

    public static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Resource
    private final MailgunConfiguration mailgunConfig;

    @Resource
    private final VelocityEngine velocityEngine;

    private VelocityContext minionContext(String minionName, String minionEmail) {
        VelocityContext context = createContext();
        context.put("Beginning", "Hei!");
        context.put("Intro", "Sulle on tekkinud uus minion!");
        context.put("name", minionName);
        context.put("minionEmail", minionEmail);
        context.put("Ending", "Võta temaga ASAP ühendust!");
        return context;
    }

    private VelocityContext passwordContext() {
        VelocityContext context = createContext();

        context.put("Beginning", "Hei!");
        context.put("Intro", "Klikkides all oleval lingil s aad taastada oma Hub-i kasutaja parooli.");
        context.put("Link", link);
        context.put("Ending", "Kui sina ei proovinud oma parooli taastada, siis võid kirja rahulikult kustudada!");

        return context;
    }

    public CompletableFuture<Response> sendPasswordEmail(String to) {
        return sendAsync(to, "password", passwordContext(), "ITÜK hub kasutaja");
    }
    public CompletableFuture<Response> sendEmail(String mentorEmail, String minionEmail, String minionName) {
                return sendAsync(mentorEmail, "minion", minionContext(minionName, minionEmail), "Uus minion");
    }

    private CompletableFuture<Response> sendAsync(String to, String templateName, VelocityContext context,
                                                  String subject) {
        Mail mail = Mail.using(mailgunConfig)
                .to(to)
                .subject(subject)
                .html(renderTemplate("html/" + templateName, context))
                .text(renderTemplate("plain/" + templateName, context))
                .build();
        return sendAsync(mail);
    }

    private CompletableFuture<Response> sendAsync(Mail mail) {
        CompletableFuture<Response> future = new CompletableFuture<>();
        mail.sendAsync(new MailRequestCallback() {
            @Override
            public void completed(Response response) {
                future.complete(response);
            }

            @Override
            public void failed(Throwable throwable) {
                future.completeExceptionally(throwable);
            }
        });
        return future;
    }

    private String renderTemplate(String templateName, VelocityContext context) {
        StringWriter writer = new StringWriter();
        try {
            velocityEngine.getTemplate("emailTemplates/" + templateName + ".vm").merge(context, writer);
            return writer.getBuffer().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private VelocityContext createContext() {
        VelocityContext context = new VelocityContext();
        context.put("datePattern", DATE_PATTERN);
        return context;
    }
}
