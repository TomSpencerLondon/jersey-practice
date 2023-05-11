package com.tomspencerlondon.repository;

import com.tomspencerlondon.model.Speaker;
import com.tomspencerlondon.model.SpeakerSearch;

import java.util.ArrayList;
import java.util.List;

public class SpeakerRepositoryStub implements SpeakerRepository {

    private static List<Speaker> speakers = new ArrayList<>();

    public SpeakerRepositoryStub() {
        Speaker speaker1 = new Speaker();
        speaker1.setId(1L);
        speaker1.setName("Bryan");
        speaker1.setCompany("Pluralsite");
        speakers.add(speaker1);

        Speaker speaker2 = new Speaker();
        speaker2.setId(2L);
        speaker2.setName("Roger");
        speaker2.setCompany("Wilco");
        speakers.add(speaker2);

        Speaker speaker3 = new Speaker();
        speaker3.setId(3L);
        speaker3.setName("Harry");
        speaker3.setCompany("Deloitte");
        speakers.add(speaker3);
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

    @Override
    public void delete(Long id) {
        Speaker speaker = findSpeakerById(speakers, id);
        speakers.remove(speaker);
    }

    @Override
    public List<Speaker> findByCompany(List<String> companies, int ageFromVal, int ageToVal) {
        // select * from speakers where company in [?,?] and age > ? and age < ?

        List<Speaker> result = new ArrayList<>();

        for (Speaker speaker : speakers) {
            if (companies.contains(speaker.getCompany())) {
                result.add(speaker);
            }
        }

        return result;
    }

    @Override
    public List<Speaker> findByConstraints(SpeakerSearch speakerSearch) {
        System.out.println(speakerSearch.getSearchType());
        List<Speaker> result = new ArrayList<>();

        for (Speaker speaker : speakers) {
            if (speakerSearch.getCompanies().contains(speaker.getCompany())) {
                result.add(speaker);
            }
        }

        return result;
    }

    private Speaker findSpeakerById(List<Speaker> speakers, Long id) {
        return speakers
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new);
    }
}
