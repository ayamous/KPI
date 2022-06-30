package ma.itroad.ram.kpi.services.config;


import ma.itroad.ram.kpi.model.UserCredentials;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * Created by itroad.ma.
 */
@Service
public class KeycloakClientService {

    private Keycloak keycloak;

    @Value("${keycloak.credentials.secret}")
    private String SECRETKEY;

    @Value("${keycloak.resource}")
    private String CLIENTID;

    @Value("${keycloak.auth-server-url}")
    private String AUTHURL;

    @Value("${keycloak.realm}")
    private String REALM;

    public KeycloakClientService() {
    }


    public Keycloak getInstance() {

        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(AUTHURL)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .realm(REALM)
                    .clientId(CLIENTID)
                    .clientSecret(SECRETKEY)
                    .build();
        }

        return keycloak;
    }


    public UsersResource getUsersResource() {

        Keycloak kc = getInstance();

        RealmResource realmResource = kc.realm(REALM);

        return realmResource.users();
    }

    public RealmResource getRealmResource() {
        Keycloak kc = getInstance();
        return kc.realm(REALM);
    }


    public String getToken(UserCredentials userCredentials) {

        String responseToken = null;
       /* try {

            String username = userCredentials.getUsername();

            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            urlParameters.add(new BasicNameValuePair("grant_type", "password"));
            urlParameters.add(new BasicNameValuePair("client_id", CLIENTID));
            urlParameters.add(new BasicNameValuePair("username", username));
            urlParameters.add(new BasicNameValuePair("password", userCredentials.getPassword()));
            urlParameters.add(new BasicNameValuePair("client_secret", SECRETKEY));

            responseToken = sendPost(urlParameters);

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return responseToken;

    }


}