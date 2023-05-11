package com.tomspencerlondon.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import com.tomspencerlondon.model.Speaker;
import jakarta.ws.rs.core.MediaType;

public class SpeakerClient {
    private Client client;
    private final String SPEAKER_URI = "http://localhost:8080/speaker";

    public SpeakerClient() {
        client = ClientBuilder.newClient();
    }


    public Speaker get(Long l) {
        return client.target(SPEAKER_URI)
                .path(String.valueOf(l))
                .request(MediaType.APPLICATION_JSON)
                .get(Speaker.class);
    }


    public static void main(String[] args) {
        SpeakerClient client = new SpeakerClient();
        Speaker speaker = client.get(1L);

        System.out.println(speaker.getName());
    }

}
