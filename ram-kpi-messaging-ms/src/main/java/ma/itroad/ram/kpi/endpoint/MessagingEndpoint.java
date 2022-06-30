package ma.itroad.ram.kpi.endpoint;

import lombok.AllArgsConstructor;
import ma.itroad.ram.kpi.common.api.messaging.domain.Ack;
import ma.itroad.ram.kpi.common.api.messaging.domain.AppMessage;
import ma.itroad.ram.kpi.service.MailService;
import ma.itroad.ram.kpi.service.PushNotificationService;
import ma.itroad.ram.kpi.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/messaging")
@AllArgsConstructor
public class MessagingEndpoint {

    private final SmsService smsService;
    private final MailService mailService;
    private final PushNotificationService pushNotificationService;

    @PostMapping("sms")
    public ResponseEntity<Ack<List<Ack<AppMessage>>>> sendSms(@Valid @RequestBody Collection<AppMessage> messages) {
        return ResponseEntity.ok(smsService.send(messages));
    }

    @PostMapping("mails")
    public ResponseEntity<Ack<List<Ack<AppMessage>>>> sendMails(@Valid @RequestBody Collection<AppMessage> messages) {
        // TODO Auto-generated method stub
        return ResponseEntity.ok(mailService.send(messages));
    }

    @PostMapping("mail")
    public ResponseEntity<Ack<AppMessage>> sendMail(@Valid @RequestBody AppMessage messages) {
        String token = "adass";
        String url = "http://localhost:8092/api/coref/v1/auth/email-verify?token=" + token;
        messages.addProperty(AppMessage.TEMPLATE_KEY_NAME, "email-verification");
        Map<String, String> model = new HashMap<>();
        model.put("userName", "sss");
        model.put("userEmailTokenVerificationLink", url);
        messages.addProperty(AppMessage.TEMPLATE_PARAMS_KEY_NAME, model);
        return ResponseEntity.ok(mailService.send(messages));
    }

    @PostMapping("notify")
    public ResponseEntity<Ack<List<Ack<AppMessage>>>> sendNotification(
            @Valid @RequestBody Collection<AppMessage> messages) {
        return ResponseEntity.ok(pushNotificationService.send(messages));
    }

}
