package com.kaplan.gym_service.dto;

import lombok.Data;

@Data
public class CreateReservationRequest {
    private Long memberId;
    private Long classId;
}
