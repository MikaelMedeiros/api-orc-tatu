package com.codejokers.orctatu.dto;

import com.codejokers.orctatu.enums.PaymentMethod;
import com.codejokers.orctatu.enums.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateBudgetDTO(@NotNull(message = "Preencha o status.") Status status,
                              PaymentMethod paymentMethod) {}