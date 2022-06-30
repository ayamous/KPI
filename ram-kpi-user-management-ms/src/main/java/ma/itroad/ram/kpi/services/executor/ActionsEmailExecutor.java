package ma.itroad.ram.kpi.services.executor;


import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

import org.keycloak.admin.client.resource.UsersResource;
import org.slf4j.Logger;

//https://www.keycloak.org/docs-api/10.0/javadocs/org/keycloak/services/resources/admin/UserResource.html
@Service
public class ActionsEmailExecutor {
	//Send a update account email to the user An email contains a link the user can click to perform a set of required actions.

	private static final Logger logger = LoggerFactory.getLogger(ActionsEmailExecutor.class);

    @Async("threadActionsEmailExecutor")
    public void send(UsersResource usersResource, String userId) {
        String redirectUri = "http://20.86.130.237:3000/users";
        // @Value("${keycloak.resource}")
        String clientId = "kpi-back";
        usersResource.get(userId).executeActionsEmail(clientId, redirectUri, Collections.singletonList("UPDATE_PASSWORD"));
        logger.info("ActionsEmailExecutor success status ");

    }
}