package jbcodeforce.app.domain.meeting;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jbcodeforce.app.domain.ToDo;

@ApplicationScoped
public class MeetingService {
    
   
    
    public Uni<List<Meeting>> getAll() {
        return Meeting
                .listAll(Sort.by("customer"));
    }

    public Uni<Meeting> getSingle(Long id) {
        return Meeting.findById(id);
    }

    public Uni<Meeting> create(Meeting meeting) {
        if (meeting.todos != null) {
            for (ToDo t : meeting.todos) {
                t.meeting= meeting;
            }
        
        }
        return  Panache.withTransaction(meeting::persist);
    }
    
}
