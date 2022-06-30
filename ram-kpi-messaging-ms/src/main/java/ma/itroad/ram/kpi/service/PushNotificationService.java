package ma.itroad.ram.kpi.service;

import lombok.AllArgsConstructor;
import ma.itroad.ram.kpi.common.api.messaging.domain.Ack;
import ma.itroad.ram.kpi.common.api.messaging.domain.AppMessage;
import ma.itroad.ram.kpi.common.api.messaging.enums.MessageStatus;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PushNotificationService implements MessagingService {

    private final ModelMapper modelMapper;

    @Override
    @SendTo("topic/notify")
    public List<Ack<AppMessage>> process(Collection<AppMessage> messages) {
        return messages.stream().map(appMessage -> {
            Ack<AppMessage> ack = new Ack<>();
            appMessage.setStatus(MessageStatus.DELIVERED);
            ack.ok("Notification delivered", appMessage);
            return ack;
        }).collect(Collectors.toList());
    }


}
