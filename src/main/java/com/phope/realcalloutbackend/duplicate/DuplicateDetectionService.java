package com.phope.realcalloutbackend.duplicate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DuplicateDetectionService {

    private final DuplicateSuggestionRepository;

    public
}
