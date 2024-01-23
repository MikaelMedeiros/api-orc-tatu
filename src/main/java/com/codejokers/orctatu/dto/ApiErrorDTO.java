package com.codejokers.orctatu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiErrorDTO(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
                          LocalDateTime timestamp,
                          Integer status,
                          String error) {}