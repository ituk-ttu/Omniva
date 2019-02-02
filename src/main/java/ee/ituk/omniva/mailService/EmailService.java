package ee.ituk.omniva.mailService;

import ee.ituk.omniva.config.MailgunConfiguration;
import lombok.RequiredArgsConstructor;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.MailRequestCallback;
import net.sargue.mailgun.Response;
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

    public CompletableFuture<Response> sendEmail(String mentorID, String minionName, String minionEmail) {
        VelocityContext context = createContext();
       /* context.put("ticket", ticket);
        context.put("ticketType", ticketType);
        context.put("ticketOffering", ticketOffering);
        context.put("perPerson", personCost.toString());
        context.put("invoiceNumber",
                "2019-359027-" + "000".substring(Integer.toString(ticket.getId()).length()) + ticket.getId());
        context.put("payByDate",
                OffsetDateTime.ofInstant(ticket.getDateCreated().plusDays(3).toInstant(), ZoneId.systemDefault()));
        context.put("date", OffsetDateTime.now());
        String to = getOwnerId(ticket.getOwnerId());
        switch (type) {
            case "ticketReserved":
                return sendAsync(to, "ticketReserved", context, "Pilet reserveeritud / Ticket Reserved");
            case "ticketWaiting":
                return sendAsync(getOwnerId(ticket.getOwnerId()), "ticketWaiting", context, "Pilet ootel / Ticket In Waiting List ");
            case "ticketCanceled":
                return sendAsync(getOwnerId(ticket.getOwnerId()), "ticketCanceled", context, "Pilet tühistatud / Ticket Canceled");
            case "ticketConfirmed":
                return sendAsync(getOwnerId(ticket.getOwnerId()), "ticketConfirmed", context, "Pilet kinnitatud / Ticket Confirmed");
        }
       */ throw new RuntimeException();
    }

    private CompletableFuture<Response> sendAsync(String to, String templateName, VelocityContext context,
                                                  String subject) {
        Mail mail = Mail.using(mailgunConfig)
                .to(to)
                .subject(subject)
                .html(renderTemplate("html/" + templateName, context))
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
