package com.phope.realcalloutbackend.duplicate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SimilarIncidentResult {
    private UUID id;
    private double similarityScore;
}