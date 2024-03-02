package com.ukukhula.bursaryapi.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Statuses {
    private final Integer StatusID;
    private final String Status;
}
