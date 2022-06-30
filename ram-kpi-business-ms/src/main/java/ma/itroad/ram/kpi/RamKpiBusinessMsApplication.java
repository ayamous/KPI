package ma.itroad.ram.kpi;

import ma.itroad.ram.kpi.service.multipartupload.FileUploadProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableConfigurationProperties({
        FileUploadProperties.class
})
@EnableFeignClients
public class RamKpiBusinessMsApplication {

    private static final Logger log = LoggerFactory.getLogger(RamKpiBusinessMsApplication.class);

    public static void main(String[] args) {
        // System.out.println("SpringVersion :" + SpringVersion.getVersion());
        SpringApplication.run(RamKpiBusinessMsApplication.class, args);
    }
}
