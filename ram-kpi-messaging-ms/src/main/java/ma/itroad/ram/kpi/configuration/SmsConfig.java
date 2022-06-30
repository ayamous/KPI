package ma.itroad.ram.kpi.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("fm6.sga.smsprovider")
public class SmsConfig {
    private final String username;
    private final String password;
    private final String sender;
    private final MessageProperties message;

    @Data
    public static final class MessageProperties {
        private long max;
        private long time;
        private ChronoUnit chronoUnit;
    }
}

