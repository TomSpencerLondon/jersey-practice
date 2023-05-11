package com.tomspencerlondon;

import com.tomspencerlondon.model.Speaker;
import com.tomspencerlondon.repository.SpeakerRepository;
import com.tomspencerlondon.repository.SpeakerRepositoryStub;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;import jakarta.ws.rs.core.MultivaluedMap;

import java.util.List;

@Path("speaker")
public class SpeakerResource {

    private SpeakerRepository speakerRepository = new SpeakerRepositoryStub();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Speaker> findAll() {
        return speakerRepository.findAll();
    }

    @Path("{id}")
    @GET
    public Speaker getSpeaker(@PathParam("id") Long id) {
        return speakerRepository.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Speaker createSpeakerWithParams(MultivaluedMap<String, String> formParams) {
        System.out.println(formParams.getFirst("name"));
        System.out.println(formParams.getFirst("company"));

        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Speaker createSpeaker(Speaker speaker) {
        System.out.println(speaker.getName());

        speaker = speakerRepository.create(speaker);
        return speaker;
    }
}
