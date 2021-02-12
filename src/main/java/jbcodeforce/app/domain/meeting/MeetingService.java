package jbcodeforce.app.domain.meeting;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.hibernate.reactive.mutiny.Mutiny;

import io.smallrye.mutiny.Uni;
import jbcodeforce.app.domain.ToDo;

@ApplicationScoped
public class MeetingService {
    

    @Inject
    Mutiny.Session mutinySession;

    public Uni<List<Meeting>> getAll() {
        return mutinySession
                .createNamedQuery( "Meetings.findAll", Meeting.class ).getResults().collectItems().asList();
    }

    public Uni<Meeting> getSingle(Integer id) {
        return mutinySession.find(Meeting.class, id);
    }

    public Uni<Meeting> create(Meeting meeting) {
        if (meeting.todos != null) {
            for (ToDo t : meeting.todos) {
                t.meeting= meeting;
            }
        
        }
        return  mutinySession.persist(meeting).chain(mutinySession::flush)
                .onItem().transform(ignore -> meeting);
    }
    
}
