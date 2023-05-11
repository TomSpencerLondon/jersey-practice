package com.tomspencerlondon.repository;

import com.tomspencerlondon.model.Speaker;

import java.util.ArrayList;
import java.util.List;

public class SpeakerRepositoryStub implements SpeakerRepository {

    private static List<Speaker> speakers = new ArrayList<>();

    public SpeakerRepositoryStub() {
        Speaker speaker1 = new Speaker();
        speaker1.setId(1L);
        speaker1.setName("Bryan");
        speaker1.setCompany("Pluralsight");
        speakers.add(speaker1);

        Speaker speaker2 = new Speaker();
        speaker2.setId(2L);
        speaker2.setName("Roger");
        speaker2.setCompany("Wilco");
        speakers.add(speaker2);
    }

    @Override
    public List<Speaker> findAll() {
        return speakers;
    }

    @Override
    public Speaker findById(Long id) {
        return findSpeakerById(speakers, id);
    }

    @Override
    public Speaker create(Speaker speaker) {
        speaker.setId(speakers.size() + 1L);
        speakers.add(speaker);

        return speaker;
    }

    @Override
    public Speaker update(Speaker speaker) {
        Speaker storedSpeaker = findSpeakerById(speakers, speaker.getId());

        storedSpeaker.setName(speaker.getName());
        storedSpeaker.setCompany(speaker.getCompany());

        return storedSpeaker;
    }

    private Speaker findSpeakerById(List<Speaker> speakers, Long id) {
        return speakers
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
