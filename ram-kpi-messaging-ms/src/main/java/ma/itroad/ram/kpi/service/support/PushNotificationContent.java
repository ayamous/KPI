package ma.itroad.ram.kpi.service.support;

import ma.itroad.ram.kpi.common.api.messaging.enums.PushNotificationContentType;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationContent implements MessagingContent<PushNotificationContentType> {

    @Override
    public String prepare(PushNotificationContentType type) {
        return null;
    }
}
