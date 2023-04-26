package com.flab.fkream.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.security.PublicKey;
import javax.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class FCMInitializer {

    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

    @Value("${fcm.key.scope}")
    private String fireBaseScope;


    @PostConstruct
    public void initialize() {
        try {
            ClassPathResource resource = new ClassPathResource(FCM_PRIVATE_KEY_PATH);
            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials
                    .fromStream(resource.getInputStream())
                    .createScoped(fireBaseScope))
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

}
