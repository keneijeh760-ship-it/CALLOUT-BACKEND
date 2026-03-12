package com.phope.realcalloutbackend.organization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByDomain(String domain);

    Optional<Organization> findOrganizationBySlug(String slug);
}
