package com.tomspencerlondon.client;

import com.tomspencerlondon.model.Speaker;
import com.tomspencerlondon.model.SpeakerSearch;
import com.tomspencerlondon.model.SpeakerSearchType;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

public class SpeakerSearchClient {

    private Client client;
    private final String SPEAKER_SEARCH_URI = "http://localhost:8080/search/speaker";

    public SpeakerSearchClient() {
        client = ClientBuilder.newClient();
    }

    public List<Speaker> searchWithObject(SpeakerSearch speakerSearch) {
        Response response = client.target(SPEAKER_SEARCH_URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(speakerSearch, MediaType.APPLICATION_JSON));

        List<Speaker> speakers = response.readEntity(new GenericType<List<Speaker>>(){});

        return speakers;
    }

    public List<Speaker> search(String param, List<String> searchValues, String ageFrom, int ageFromVal, String ageTo, int ageToVal) {
        return client
                .target(SPEAKER_SEARCH_URI)
                .queryParam(param, searchValues)
                .queryParam(ageFrom, ageFromVal)
                .queryParam(ageTo, ageToVal)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Speaker>>(){});
    }

    public static void main(String[] args) {
        SpeakerSearchClient client = new SpeakerSearchClient();
        List<Speaker> results = client.search("company", Arrays.asList("Deloitte", "Pluralsite"), "ageFrom", 20, "ageTo", 80);
        System.out.println(results.size());

        SpeakerSearch speakerSearch = new SpeakerSearch();
        speakerSearch.setCompanies(Arrays.asList("Deloitte", "Pluralsite"));
        speakerSearch.setAgeFrom(20);
        speakerSearch.setAgeTo(80);
        speakerSearch.setSearchType(SpeakerSearchType.SEARCH_BY_AGE_RANGE);

        results = client.searchWithObject(speakerSearch);

        System.out.println(results.size());
    }
}
