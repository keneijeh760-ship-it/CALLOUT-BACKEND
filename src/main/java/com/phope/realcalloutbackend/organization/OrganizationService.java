package com.phope.realcalloutbackend.organization;

import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization getOrgByDomain(String domain){
        return organizationRepository.findByDomain(domain)
                .orElseThrow(()-> new NotFoundException("Organization", domain));

    }

    public Organization getOrgById(UUID id){
        return organizationRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Organization", id));
    }

    public Organization getBySlug (String slug){
        return organizationRepository.findOrganizationBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Organization", slug));
    }



}
