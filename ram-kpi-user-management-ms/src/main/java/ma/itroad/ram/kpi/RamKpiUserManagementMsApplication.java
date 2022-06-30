package ma.itroad.ram.kpi;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ConfigurationProperties(prefix = "keycloak", ignoreUnknownFields = false)
public class RamKpiUserManagementMsApplication {

    public static void main(String[] args) {
        // System.out.println("SpringVersion :" + SpringVersion.getVersion());
        SpringApplication.run(RamKpiUserManagementMsApplication.class, args);
    }

    @Bean(name = "threadVerificationLinkExecutor")
    public Executor VerificationLinkExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsynchThread::");
        executor.initialize();
        return executor;
    }


    @Bean(name = "threadActionsEmailExecutor")
    public Executor ActionsEmailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsynchThread::");
        executor.initialize();
        return executor;
    }

}
