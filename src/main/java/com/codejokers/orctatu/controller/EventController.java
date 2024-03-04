package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.config.CalendarConfig;
import com.codejokers.orctatu.dto.AgendaDTO;
import com.codejokers.orctatu.dto.UserDTO;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
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
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
        Instant startDate = Instant.ofEpochMilli(agendaDTO.getStartDateTime());
        Date date = Date.from(startDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
        //reservar tempo na agenda para a tatuagem
        DateTime startDateTime = new DateTime(date);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Sao_Paulo");
        event.setStart(start);

        Instant endDate = Instant.ofEpochMilli(agendaDTO.getEndDateTime());
        Date dateFinal = Date.from(endDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
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

        //Cor do card no Google calendar
        setColorEvent(agendaDTO, event);

        //seleciona calendario principal do tatuador
        String calendarId = "primary";

        //Cria conexão com a api do google calendar
        UserDTO userDTO =  principal.getAttribute("userDTO");
        var service = calendarConfig.serviceCalendar(userDTO.getAccessToken());


        event = service.events().insert(calendarId, event).execute();

        return ResponseEntity.status(HttpStatus.CREATED).body("{ \"data\": \"Tatuagem agendada!: " +  event.getHtmlLink()+ "\"}") ;
    }

    private void setColorEvent(AgendaDTO agendaDTO, Event event) {
        if(agendaDTO.getTipoAgendamento().equals("tattoo")) {
            if(agendaDTO.isPaid()) {
                event.setColorId("10");
            } else {
                event.setColorId("2");
            }
        } else { //retoque
            if(agendaDTO.isPaid()) {
                event.setColorId("9");
            } else {
                event.setColorId("1");
            }
        }
    }
}
