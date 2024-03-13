package com.codejokers.orctatu.dto;

import com.codejokers.orctatu.enums.PaymentMethod;
import com.codejokers.orctatu.enums.ScheduleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ScheduleInfoDTO(String budgetId,
                              @NotBlank(message = "Preencha a descrição.")
                              String description,
                              @NotBlank(message = "Preencha o sumário.")
                              String summary,
                              @NotNull(message = "Preencha o horário de início.")
                              Long start,
                              @NotNull(message = "Preencha o horário de fim.")
                              Long end,
                              @NotNull(message = "Preencha o tipo de agendamento.")
                              ScheduleType scheduleType,
                              @NotNull(message = "Informe se a tatuagem foi paga.")
                              Boolean isPaid,
                              @NotNull(message = "Preencha o método de pagamento.")
                              PaymentMethod paymentMethod) {}