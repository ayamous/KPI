package  ma.itroad.ram.kpi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.itroad.ram.kpi.common.api.messaging.domain.Ack;
import ma.itroad.ram.kpi.common.api.messaging.domain.AppMessage;
import ma.itroad.ram.kpi.common.api.messaging.enums.MessageStatus;
import ma.itroad.ram.kpi.common.api.messaging.enums.MessageType;
import ma.itroad.ram.kpi.common.api.messaging.utils.CollectionUtils;
import ma.itroad.ram.kpi.common.api.messaging.utils.PatternUtils;
import ma.itroad.ram.kpi.configuration.MailConfig;
import ma.itroad.ram.kpi.service.support.MailContent;
import ma.itroad.ram.kpi.service.support.MessagingAuthorization;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

@Service
@AllArgsConstructor
@Slf4j
public class MailService implements MessagingService {

    private final JavaMailSender mailSender;
    private final MailContent mailContent;
    private final MessagingAuthorization messagingAuthorization;
    private final ModelMapper mapper;
    private final MailConfig mailConfig;

    

    @Override
    public List<Ack<AppMessage>> process(Collection<AppMessage> messages) {
        List<Ack<AppMessage>> acks = new ArrayList<>();
        CollectionUtils.parallelConsume(messages, appMessage -> {
            Ack<AppMessage> ack = new Ack<>();
            if (appMessage.getType() == null) {
                appMessage.setType(MessageType.EMAIL_HTML);
            }

            if (MessageStatus.FAILED_DUPLICATED_RECIPIENT.equals(appMessage.getStatus())) {
                ack.of(null, HttpStatus.CONFLICT.value(), "Duplicated recipient", appMessage);
                acks.add(ack.data(appMessage));
                return;
            }

            ack.data(appMessage);
            if (!PatternUtils.isEmail(appMessage.getRecipient())) {
                appMessage.setStatus(MessageStatus.FAILED);
                ack.message("Invalid mail address").code(HttpStatus.INTERNAL_SERVER_ERROR.value());
                acks.add(ack);
                return;
            }
            Ack<Boolean> authAck = messagingAuthorization.canDeliverTo(appMessage.getRecipient(), MessageType.EMAIL);
            if (!authAck.getData()) {
                ack.of(authAck);
                appMessage.setStatus(MessageStatus.FAILED);
                return;
            }

            try {
                log.info("Sending mail from {} to {}", appMessage.getSender(), appMessage.getRecipient());
                appMessage.setSender(mailConfig.getSender());
                sendMail(appMessage);
                appMessage.setStatus(MessageStatus.DELIVERED);
                ack.code(200);
            } catch (MailException e) {
                log.error("Error while sending message from {} to {}", appMessage.getSender(), appMessage.getRecipient(), e);
                appMessage.setStatus(MessageStatus.FAILED);
                ack.code(500);
            }
            ack.data(appMessage);
            acks.add(ack);
        });
        return acks;
    }

    private void sendMail(AppMessage appMessage) {
        mailSender.send(mimeMessage -> {
            String content = null;
            System.out.println(" sendMail :  " + appMessage.getContent());
            System.out.println(" hasTemplateProperties : " + appMessage.hasTemplateProperties());
            if (appMessage.getContent() == null || appMessage.getContent().isEmpty()) {
                System.out.println("has email template : " + appMessage.hasTemplateProperties());
                if (appMessage.hasTemplateProperties()) {
                    System.out.println("hasTemplateProperties ");
                    content = mailContent.prepare(String.valueOf(appMessage.getProperty(AppMessage.TEMPLATE_KEY_NAME)), appMessage.getProperty(AppMessage.TEMPLATE_PARAMS_KEY_NAME));
                }
            } else {
                content = appMessage.getContent();
            }
            if (StringUtils.isEmpty(content)) {
                throw new Error("No content provided for the given mail");
            }
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
            mimeMessageHelper.setFrom(appMessage.getSender() == null ? mailConfig.getSender() : appMessage.getSender());
            mimeMessageHelper.setTo(appMessage.getRecipient());
            mimeMessageHelper.setSubject(StringUtils.isEmpty(appMessage.getSubject()) ? mailConfig.getSubject() : appMessage.getSubject());
            mimeMessageHelper.setText(content, MessageType.EMAIL_HTML.equals(appMessage.getType()));
        });
    }

	
	public AppMessage process1(AppMessage appMessage) {
		try {
			mailSender.send(
				mimeMessage -> {
					 String content = null;
			            if (appMessage.getContent() == null || appMessage.getContent().isEmpty()) {
			                if (appMessage.hasTemplateProperties()) {
			                    content = mailContent.prepare(String.valueOf(appMessage.getProperty(AppMessage.TEMPLATE_KEY_NAME)), appMessage.getProperty(AppMessage.TEMPLATE_PARAMS_KEY_NAME));
			                }
			            } else {
			                content = appMessage.getContent();
			            }
			    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
	            mimeMessageHelper.setFrom(appMessage.getSender() == null ? mailConfig.getSender() : appMessage.getSender());
	            mimeMessageHelper.setTo(appMessage.getRecipient());
	            mimeMessageHelper.setSubject(StringUtils.isEmpty(appMessage.getSubject()) ? mailConfig.getSubject() : appMessage.getSubject());
	            mimeMessageHelper.setText(content, true);
	        });
			
			return appMessage;

		}catch (Exception e) {
			// TODO: handle exception
		}
		return appMessage;
		
	}
}
