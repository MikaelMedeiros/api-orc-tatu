package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.config.CalendarConfig;
import com.codejokers.orctatu.dto.AgendaDTO;
import com.codejokers.orctatu.dto.UserInfoDTO;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    private final CalendarConfig calendarConfig;

    @PostMapping
    public ResponseEntity<String> agendarTatuagem(
            @RequestBody AgendaDTO agendaDTO,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal
    ) throws IOException {

        //Colocar o titulo, descricao e local
        Event event = new Event()
                .setSummary(agendaDTO.getSummary())
                .setDescription(agendaDTO.getDescription());
        Date date = Date.from(agendaDTO.getStartDateTime().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        //reservar tempo na agenda para a tatuagem
        DateTime startDateTime = new DateTime(date);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Sao_Paulo");
        event.setStart(start);

        var finalDate = agendaDTO.getStartDateTime().plusHours(2);
        Date dateFinal = Date.from(finalDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        DateTime endDateTime = new DateTime(dateFinal);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Sao_Paulo");
        event.setEnd(end);

        //Quantos minutos antes sera lembrado da tatto
        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(45),
        };

        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);


        //Cor
        // 10 = verde - tattoo agendado
        // 6 = Amarelo - tattoo agendado sem pix
        event.setColorId("10");

        //seleciona calendario principal do tatuador
        String calendarId = "primary";

        //Cria conexão com a api do google calendar
        UserInfoDTO userInfoDTO =  principal.getAttribute("userInfoDTO");
        var service = calendarConfig.serviceCalendar(userInfoDTO.getTokenInfoDTO().getAccessToken());


        event = service.events().insert(calendarId, event).execute();

        return ResponseEntity.status(HttpStatus.CREATED).body("{ \"data\": \"Tatuagem agendada!: " +  event.getHtmlLink()+ "\"}") ;
    }
}
