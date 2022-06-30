package ma.itroad.ram.kpi;

import ma.itroad.ram.kpi.configuration.MailConfig;
import ma.itroad.ram.kpi.configuration.SmsConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SmsConfig.class, MailConfig.class})
public class MessagingBeanFactory {
}
