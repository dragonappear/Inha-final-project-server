package com.dragonappear.inha.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
@Configuration
public class FirebaseConfig {
    private final String path = "src/main/resources/firebase/firebaseAccountKey.json";

    @PostConstruct
    public void init(){
        try{
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(path))).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}