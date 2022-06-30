package ma.itroad.ram.kpi.service.OpenFeign;

import ma.itroad.core.common.api.messaging.domain.AppMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;

@FeignClient(value="FeignMessaging" , url = "${messaging-url}")
public interface MessagingService {
    @PostMapping("mails")
    String sendMail(Collection<AppMessage> messages);
}
