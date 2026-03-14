package com.phope.realcalloutbackend.engagement.upvote;

import com.phope.realcalloutbackend.Shared.config.exception.CalloutException;
import com.phope.realcalloutbackend.Shared.config.exception.NotFoundException;
import com.phope.realcalloutbackend.user.User;
import com.phope.realcalloutbackend.user.UserRepository;
import jakarta.persistence.Transient;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UpvoteService {
    private final UserRepository userRepository;
    private final UpvoteRepository upvoteRepository;

    public void addUpvote(UUID incidentId, Jwt jwt){
        String supabaseUid = jwt.getSubject();

        int upvotecount = 0;

        User user = userRepository.findBySupabaseUid(supabaseUid)
                .orElseThrow(() -> new NotFoundException("User", supabaseUid));

        Upvote upvoteuser = upvoteRepository.findByIncidentIdAndUserId(incidentId,user.getId())
                .orElseThrow(()-> new NotFoundException("User", user.getId()));

        boolean upvote = upvoteRepository.existsUpvoteByUserId(user.getId());

        if (!upvote){
            throw new CalloutException("Already upvoted");
        }

        upvoteRepository.save(upvoteuser);

        upvotecount += 1;
    }
}
