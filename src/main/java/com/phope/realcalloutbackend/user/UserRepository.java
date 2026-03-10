package com.phope.realcalloutbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findBySupabaseUid(String supabaseUid);

    Optional<User> findByOrgIdAndUsername(UUID orgId, String username);

    boolean existsByOrgIdAndUsername(UUID orgId, String username);

}
