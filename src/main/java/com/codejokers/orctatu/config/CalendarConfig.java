package com.codejokers.orctatu.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class CalendarConfig {

    public Calendar serviceCalendar(String token) {
        //identificar uma forma de utilizar o credential não depreciado
        GoogleCredential credential = new GoogleCredential().setAccessToken(token);

        return new Calendar.Builder(
            new NetHttpTransport(),
            new GsonFactory(),
                credential

        ).setApplicationName("Orc Tattoo").build();
    }
}
