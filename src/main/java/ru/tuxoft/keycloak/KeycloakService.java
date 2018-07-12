package ru.tuxoft.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tuxoft.profile.domain.ProfileVO;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    private Keycloak kc;

    @PostConstruct
    private void init() {
       this.kc = getKeycloak();
    };

    /**
     * Получить объект доступа к keycloak
     */
    public Keycloak getKeycloak() {
        return KeycloakService.getInstance(keycloakUrl, "rest-admin", "rest-admin");
    }

    private static Keycloak getInstance(String url, String user, String pass) {
        return KeycloakBuilder.builder()
                .serverUrl(url)
                .realm("book-realm")
                .username(user)
                .password(pass)
                .clientId("admin-cli")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).socketTimeout(10, TimeUnit.SECONDS).build())
                .build();

    }

    public void updateProfileInKeycloak(ProfileVO profile) {
        //почему-то не авторизуется в кейклоаке
        /*UserResource user = getUserResourseByLogin(profile.getUserId());
        UserRepresentation urp = user.toRepresentation();
        urp.setEmail(profile.getEmail());
        urp.setFirstName(profile.getFirstName());
        urp.setLastName(profile.getLastName());
        user.update(urp);*/
    }

    /**
     * Получить объект доступа к пользователя по логину
     */
    public UserResource getUserResourseByLogin(String login) {
        RealmResource realmResource = kc.realm("book-realm");
        UsersResource userResource = realmResource.users();
        List<UserRepresentation> users = userResource.search(login);
        UserResource user = null;
        if (!users.isEmpty()) {
            user = realmResource.users().get(users.get(0).getId());
        }
        return user;
    }


}
