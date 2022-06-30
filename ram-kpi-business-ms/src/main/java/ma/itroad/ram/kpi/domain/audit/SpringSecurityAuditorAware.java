package ma.itroad.ram.kpi.domain.audit;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import java.security.Principal;

import java.util.Optional;

//https://github-wiki-see.page/m/RameshMF/spring-boot-developers-guide/wiki/Auditing-with-Spring-Boot-2-Spring-Data-JPA-MySQL-Example
@Slf4j
class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        System.out.println("SpringSecurityAuditorAware currentAuditor : ");

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        KeycloakPrincipal principal = (KeycloakPrincipal)authentication.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        String userName = accessToken.getPreferredUsername();
     /*   String emailID = accessToken.getEmail();
        String lastname = accessToken.getFamilyName();
        String firstname = accessToken.getGivenName();
        String realmName = accessToken.getIssuer();*/

        System.out.println("username : " + userName);

        return Optional.ofNullable(userName);
    }
}
