package com.tomspencerlondon.repository;

import com.tomspencerlondon.model.Speaker;
import com.tomspencerlondon.model.SpeakerSearch;

import java.util.List;

public interface SpeakerRepository {
    List<Speaker> findAll();

    Speaker findById(Long id);

    Speaker create(Speaker speaker);

    Speaker update(Speaker speaker);

    void delete(Long id);

    List<Speaker> findByCompany(List<String> companies, int ageFromVal, int ageToVal);

    List<Speaker> findByConstraints(SpeakerSearch speakerSearch);
}
