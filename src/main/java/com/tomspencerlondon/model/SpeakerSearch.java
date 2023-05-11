package com.tomspencerlondon.model;

import java.util.List;

public class SpeakerSearch {

    private List<String> companies;
    private int ageFrom;
    private int ageTo;

    private SpeakerSearchType searchType;

    public SpeakerSearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SpeakerSearchType searchType) {
        this.searchType = searchType;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

    public int getAgeFrom() {
        return ageFrom;
    }

    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    public int getAgeTo() {
        return ageTo;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }
}
