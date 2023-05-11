package com.tomspencerlondon.client;

import com.tomspencerlondon.model.Speaker;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

public class SpeakerSearchClient {

    private Client client;
    private final String SPEAKER_SEARCH_URI = "http://localhost:8080/search/speaker";

    public SpeakerSearchClient() {
        client = ClientBuilder.newClient();
    }

    public List<Speaker> search(String param, List<String> searchValues) {
        return client
                .target(SPEAKER_SEARCH_URI)
                .queryParam(param, searchValues)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Speaker>>(){});
    }

    public static void main(String[] args) {
        SpeakerSearchClient client = new SpeakerSearchClient();
        List<Speaker> results = client.search("company", Arrays.asList("pluralsight", "school"));
    }
}
