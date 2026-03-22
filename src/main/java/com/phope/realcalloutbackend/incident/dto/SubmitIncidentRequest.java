package com.phope.realcalloutbackend.incident.dto;

import com.phope.realcalloutbackend.incident.IncidentStatus;
import com.phope.realcalloutbackend.incident.IncidentUrgency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitIncidentRequest {
    @NotBlank
    @Size(max = 255)
    private String title;
    @NotBlank
    private String description;
    @NotNull(message = "Category is required")
    private UUID categoryId;
    @Builder.Default
    private List<String> attachmentUrls = new ArrayList<>();
    private String locationTag;
    @Builder.Default
    private IncidentUrgency urgency = IncidentUrgency.NORMAL;
}
