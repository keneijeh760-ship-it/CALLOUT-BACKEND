package com.phope.realcalloutbackend.incident;

import com.phope.realcalloutbackend.Shared.config.model.ApiResponse;
import com.phope.realcalloutbackend.incident.dto.IncidentResponse;
import com.phope.realcalloutbackend.incident.dto.SubmitIncidentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incidents")
public class IncidentController {
    private final IncidentService incidentService;

    public ResponseEntity<ApiResponse<IncidentResponse>> submit(@RequestBody @Valid SubmitIncidentRequest request, @AuthenticationPrincipal Jwt jwt){

        incidentService.submitIncident(request, jwt);

        IncidentResponse response = IncidentResponse
                .builder()
                .categoryId(request.getCategoryId())



    }


}
