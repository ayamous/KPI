package ma.itroad.ram.kpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RamKpiGatewayMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RamKpiGatewayMsApplication.class, args);
    }

}
