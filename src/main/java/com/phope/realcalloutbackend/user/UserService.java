package com.phope.realcalloutbackend.user;

import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import com.phope.realcalloutbackend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.util.UUID;

@Service

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public UserResponse getOrCreateUser(Jwt jwt) {
        String supabaseUid = jwt.getSubject();

        User user = userRepository.findBySupabaseUid(supabaseUid)
                .orElseGet(() -> createNewUser(jwt));
    }

        private User createNewUser(Jwt jwt) {

            String supabaseUid = jwt.getSubject();
            String email = jwt.getClaimAsString("email");
            String orgIdClaim = jwt.getClaimAsString("org_id");

            if (email == null || orgIdClaim == null) {
                throw new NotFoundException("Required JWT claims missing for user", supabaseUid);
            }

            User newUser = new User();

            newUser.setSupabaseUid(supabaseUid);
            newUser.setUserRole(UserRole.REPORTER);
            newUser.setEmail(hashEmail(email));
            newUser.setOrgId(UUID.fromString(orgIdClaim));
            newUser.setUsername(generateUsername(email));

            return userRepository.save(newUser);
        }

    private String generateUsername(String email) {
        return email.split("@")[0];
    }

    private String hashEmail(String email) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(email.toLowerCase().getBytes());

            // Convert the byte array to a readable hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Failed to hash email", e);
        }

    }

    public User getById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("User", id));
    }

}
