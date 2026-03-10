package com.phope.realcalloutbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserBySupabaseUid(String supabaseUid);

    Optional<User> findUserByOrgId(UUID orgId);

    Optional<User> findUserByUsername(String username);

}
