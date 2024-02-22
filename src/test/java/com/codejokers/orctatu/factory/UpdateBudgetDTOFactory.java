package com.codejokers.orctatu.factory;

import com.codejokers.orctatu.dto.UpdateBudgetDTO;
import com.codejokers.orctatu.enums.PaymentMethod;
import com.codejokers.orctatu.enums.Status;

public class UpdateBudgetDTOFactory {

    public static UpdateBudgetDTO createUpdateBudgetDTO() {
        return new UpdateBudgetDTO(Status.DONE, PaymentMethod.PIX);
    }

    public static UpdateBudgetDTO createUpdateBudgetDTOWithoutPaymentMethod() {
        return new UpdateBudgetDTO(Status.DONE, null);
    }
}