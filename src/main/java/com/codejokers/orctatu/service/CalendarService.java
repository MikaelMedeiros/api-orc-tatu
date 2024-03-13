package com.codejokers.orctatu.service;

import com.codejokers.orctatu.dto.CalendarResponseDTO;
import com.codejokers.orctatu.dto.ScheduleInfoDTO;
import com.codejokers.orctatu.enums.ScheduleType;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CalendarService {

    public CalendarResponseDTO schedule(final ScheduleInfoDTO scheduleInfoDTO, final String accessToken) throws IOException {

        Event event = new Event().setDescription(scheduleInfoDTO.description())
                                 .setSummary(scheduleInfoDTO.summary())
                                 .setStart(getEventDateTime(scheduleInfoDTO.start()))
                                 .setEnd(getEventDateTime(scheduleInfoDTO.end()))
                                 .setReminders(getReminders())
                                 .setColorId(getEventColor(scheduleInfoDTO.scheduleType(), scheduleInfoDTO.isPaid()));

        final String token = accessToken.replace("Bearer ", "");
        final Calendar calendar = callCalendarAPI(token);

        event = calendar.events().insert("primary", event).execute();
        return new CalendarResponseDTO(event.getHtmlLink());
    }

    private EventDateTime getEventDateTime(final Long dateTimeInMilliseconds) {

        final String timeZone = "America/Sao_Paulo";
        final Instant instantDateTime = Instant.ofEpochMilli(dateTimeInMilliseconds);
        final Date date = Date.from(instantDateTime.atZone(ZoneId.of(timeZone)).toInstant());
        final DateTime dateTime = new DateTime(date);
        return new EventDateTime().setDateTime(dateTime).setTimeZone(timeZone);
    }

    private Event.Reminders getReminders() {

        final List<EventReminder> eventReminders = List.of(new EventReminder().setMethod("email").setMinutes(24 * 60),
                                                           new EventReminder().setMethod("popup").setMinutes(45));
        return new Event.Reminders().setUseDefault(false).setOverrides(eventReminders);
    }

    private String getEventColor(final ScheduleType scheduleType, final boolean isPaid) {

        if (scheduleType == ScheduleType.TATTOO) return isPaid ? "10" : "2";
        return isPaid ? "9" : "1";
    }

    private Calendar callCalendarAPI(final String token) {

        final Credential credential = new Credential(BearerToken.authorizationHeaderAccessMethod()).setAccessToken(token);
        return new Calendar.Builder(new NetHttpTransport(),
                                    new GsonFactory(),
                                    credential
        ).setApplicationName("Orc Tattoo").build();
    }
}