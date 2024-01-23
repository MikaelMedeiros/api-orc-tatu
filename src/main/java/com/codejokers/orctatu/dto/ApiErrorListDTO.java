package com.codejokers.orctatu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorListDTO (@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
                               LocalDateTime timestamp,
                               Integer status,
                               List<String> errors) {}