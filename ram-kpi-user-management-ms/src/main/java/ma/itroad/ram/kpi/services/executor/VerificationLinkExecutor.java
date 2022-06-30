package ma.itroad.ram.kpi.services.executor;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.keycloak.admin.client.resource.UsersResource;
import org.slf4j.Logger;


@Service
public class VerificationLinkExecutor {
	
	private static final Logger logger = LoggerFactory.getLogger(VerificationLinkExecutor.class);
	
    @Async("threadVerificationLinkExecutor")
    public void send(UsersResource usersResource, String userId) {
        usersResource.get(userId).sendVerifyEmail();        
        logger.info("sendVerifyEmail success status ");

    }
}