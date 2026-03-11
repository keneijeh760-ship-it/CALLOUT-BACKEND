package com.phope.realcalloutbackend.organization.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationResponse {
    private UUID id;
    private String name;
    private String slug;;
    private String domain;
    private String logoUrl;

}
