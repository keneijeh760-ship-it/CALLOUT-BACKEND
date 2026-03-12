package com.phope.realcalloutbackend.incident.dto;

import com.phope.realcalloutbackend.incident.IncidentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequest {
    @NotNull(message = "New status is required")
    private IncidentStatus newStatus;
    private String note;

}
