package ma.itroad.ram.kpi.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("fm6.sga.mail")
public class MailConfig {
    private final String sender;
    private final String subject;
    private final List<String> support;
}

