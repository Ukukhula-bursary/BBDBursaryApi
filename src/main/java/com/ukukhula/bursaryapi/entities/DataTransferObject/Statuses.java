package com.ukukhula.bursaryapi.entities.DataTransferObject;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
public class Statuses {
    private final Integer StatusID;
    private final String Status;

    public Statuses(int statusID, String status) {
        this.StatusID = statusID;
        this.Status = status;

    }
}
