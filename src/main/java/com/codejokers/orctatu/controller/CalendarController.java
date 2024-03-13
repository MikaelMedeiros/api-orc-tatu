package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.dto.CalendarResponseDTO;
import com.codejokers.orctatu.dto.ScheduleInfoDTO;
import com.codejokers.orctatu.service.CalendarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    ResponseEntity<CalendarResponseDTO> schedule(@RequestBody @Valid final ScheduleInfoDTO scheduleInfoDTO,
                                                        @RequestHeader(value = "Authorization") final String accessToken) throws IOException {
        return ResponseEntity.ok(calendarService.schedule(scheduleInfoDTO, accessToken));
    }
}
