package com.phope.realcalloutbackend.incident.dto;

import com.phope.realcalloutbackend.incident.IncidentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusRequest {
    @NotBlank
    @Valid
    private IncidentStatus newStatus;
    private String notes;

}
