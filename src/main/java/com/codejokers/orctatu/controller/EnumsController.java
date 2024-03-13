package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.dto.EnumDTO;
import com.codejokers.orctatu.enums.BodyLocal;
import com.codejokers.orctatu.enums.Detail;
import com.codejokers.orctatu.enums.PaymentMethod;
import com.codejokers.orctatu.enums.ScheduleType;
import com.codejokers.orctatu.enums.Status;
import com.codejokers.orctatu.enums.Style;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "v1/enums", produces = MediaType.APPLICATION_JSON_VALUE)
class EnumsController {

    @Cacheable("body-locals")
    @GetMapping("body-locals")
    public ResponseEntity<Map<String, EnumDTO>> getBodyLocals() {
       return ResponseEntity.ok(BodyLocal.getBodyLocals());
    }

    @Cacheable("details")
    @GetMapping("details")
    public ResponseEntity<Map<String, EnumDTO>> getDetails() {
        return ResponseEntity.ok(Detail.getDetails());
    }

    @Cacheable("payment-methods")
    @GetMapping("payment-methods")
    public ResponseEntity<Map<String, EnumDTO>> getPaymentMethods() {
        return ResponseEntity.ok(PaymentMethod.getPaymentMethods());
    }

    @Cacheable("schedule-types")
    @GetMapping("schedule-types")
    public ResponseEntity<Map<String, EnumDTO>> getScheduleTypes() {
        return ResponseEntity.ok(ScheduleType.getScheduleTypes());
    }

    @Cacheable("status")
    @GetMapping("status")
    public ResponseEntity<Map<String, EnumDTO>> getStatus() {
        return ResponseEntity.ok(Status.getStatus());
    }

    @Cacheable("styles")
    @GetMapping("styles")
    public ResponseEntity<Map<String, EnumDTO>> getStyles() {
        return ResponseEntity.ok(Style.getStyles());
    }
}