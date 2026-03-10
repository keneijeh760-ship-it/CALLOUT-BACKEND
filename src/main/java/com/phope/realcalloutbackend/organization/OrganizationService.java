package com.phope.realcalloutbackend.organization;

import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional()
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization getOrgByDomain(String domain){
        return organizationRepository.findOrganizationByDomain(domain)
                .orElseThrow(()-> new NotFoundException("Organization not Found", domain));

    }

    public Organization getOrgById(UUID id){
        return organizationRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Organization", id));
    }



}
