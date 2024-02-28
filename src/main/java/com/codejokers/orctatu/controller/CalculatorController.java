package com.codejokers.orctatu.controller;

import com.codejokers.orctatu.enums.BodyLocal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "calculator",  produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculatorController {


    @GetMapping("body-locals")
    public ResponseEntity<List<BodyLocal>> getBodyLocal(){
       return ResponseEntity.ok(Arrays.asList(BodyLocal.values()));
    }


}
