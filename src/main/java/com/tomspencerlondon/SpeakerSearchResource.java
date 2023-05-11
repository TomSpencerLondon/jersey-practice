package com.tomspencerlondon;

import com.tomspencerlondon.model.Speaker;
import com.tomspencerlondon.repository.SpeakerRepository;
import com.tomspencerlondon.repository.SpeakerRepositoryStub;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

@Path("search/speaker")
public class SpeakerSearchResource {

    private SpeakerRepository speakerRepository = new SpeakerRepositoryStub();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForSpeakers(@QueryParam(value = "company") List<String> companies, @QueryParam(value = "ageFrom") int ageFromVal, @QueryParam(value = "ageTo") int ageToVal) {
        List<Speaker> speakers = speakerRepository.findByCompany(queryParams(companies.get(0)), ageFromVal, ageToVal);

        if (speakers == null || speakers.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok()
                .entity(new GenericEntity<List<Speaker>>(speakers){})
                .build();
    }

    private static List<String> queryParams(String searchValues) {
        return Arrays.asList(searchValues.substring(1, searchValues.length() - 1).split(", "));
    }
}
