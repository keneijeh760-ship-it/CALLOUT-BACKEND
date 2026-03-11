package com.phope.realcalloutbackend.organization;

import com.phope.realcalloutbackend.Shared.config.model.ApiResponse;
import com.phope.realcalloutbackend.organization.dto.OrganizationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping("/organisations/{slug}")
    public ResponseEntity<ApiResponse<OrganizationResponse>> getOrgBySlug(@PathVariable String slug){
        Organization organization = organizationService.getOrgBySlug(slug);

        OrganizationResponse org = OrganizationResponse
                .builder()
                .id(organization.getId())
                .name(organization.getName())
                .slug(organization.getSlug())
                .domain(organization.getDomain())
                .logoUrl(organization.getLogoUrl())
                .build();

        return ResponseEntity.ok(ApiResponse.ok(org));

    }

}
