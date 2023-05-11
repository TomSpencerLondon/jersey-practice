package com.tomspencerlondon.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import com.tomspencerlondon.model.Speaker;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

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

    public List<Speaker> get () {
        Response response = client.target(SPEAKER_URI)
                .request(MediaType.APPLICATION_JSON)
                .get();

        List<Speaker> speakers = response.readEntity(new GenericType<List<Speaker>>(){});

        return speakers;
    }

    public Speaker post (Speaker speaker) {
        return client.target(SPEAKER_URI)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(speaker, MediaType.APPLICATION_JSON))
                .readEntity(Speaker.class);
    }


    public static void main(String[] args) {
        SpeakerClient client = new SpeakerClient();
        Speaker speaker = client.get(1L);

        System.out.println(speaker.getName());

        List<Speaker> speakers = client.get();

        System.out.println(speakers.size());

        speaker = new Speaker();
        speaker.setName("Alex");
        speaker.setCompany("School");
        speaker = client.post(speaker);

        System.out.println(speaker.getId());
    }

}
