package jbcodeforce.app.api;

import java.util.List;
import java.util.concurrent.atomic.LongAdder;

import static javax.ws.rs.core.Response.Status.CREATED;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Uni;
import jbcodeforce.app.domain.meeting.Meeting;
import jbcodeforce.app.domain.meeting.MeetingService;

@SessionScoped
@Path("/api/v1/meetings")
@Produces("application/json")
@Consumes("application/json")
public class MeetingResource {

    @Inject
    MeetingService meetingService;

    @GET
    public Uni<List<Meeting>>getAllActiveMeetings(){
        return meetingService.getAll();
    }
    
    @GET
    @Path("{id}")
    public Uni<Meeting> getSingleById(@RestPath  Long id) {
      return meetingService.getSingle(id);
    }

    @POST
    public Uni<Response> processAndSaveMeeting(Meeting meeting) {
        if (meeting == null || meeting.id != null) {
                throw new WebApplicationException("Id was invalid.", 422);
        }
        return meetingService.create(meeting).replaceWith(Response.ok(meeting).status(CREATED)::build);
    }
}
