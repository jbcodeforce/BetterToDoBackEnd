package jbcodeforce.app.domain.meeting;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

/**
 * This implementations is far more complex as all the relationship between entities
 * needs to be done by code.
 */
@ApplicationScoped
public class MeetingServiceWithPGClient {
    private Logger logger = Logger.getLogger(MeetingServiceWithPGClient.class);

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    
/* REMOVED TO MAKE DEAD CODE
    @Inject
    @ConfigProperty(name = "app.schema.create", defaultValue = "true")
*/
    boolean schemaCreate = false;

    public MeetingServiceWithPGClient() {
        super();
    }

    /**
     * Save meeting and process the invited persons with person service
     * 
     * @param m
     */
/** 
     public Uni<Long> processAndSave(Meeting meeting) {
        logger.info(meeting.toString());
        return meeting.save(client);
        // personService.updateInvitedPersonContext(meeting.splitAttendees(),meeting.context);
        
    }
*/
    /*
     * public Multi<Customer> findCustomer(String name) { return
     * Multi.createFrom().items(meetingRepository.findCustomer(name).stream()); }
     */
/**
    public Multi<Meeting> getActiveMeetings() {
        Uni<RowSet<Row>> rowSet = client.query("SELECT * FROM meetings ORDER BY title ASC").execute();
        return rowSet
                .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
                .onItem().transform(Meeting::from);
    }
 */
    void config(@Observes StartupEvent ev) {
        if (schemaCreate) {
            initdb();
        }
    }

    private void initdb() {
        
        client.query("DROP TABLE IF EXISTS meetings").execute()
        .flatMap(r -> client.query("CREATE TABLE meetings (id SERIAL PRIMARY KEY"
            + ", title TEXT NOT NULL"
            + ", attendees TEXT"
            + ", context TEXT"
            + ", customer TEXT"
            + ", creationdate DATE"
            + ", updatedate DATE"
            + ", active BOOLEAN)").execute())
        .flatMap(r -> client.query("INSERT INTO meetings (title,attendees,context,customer,creationDate,updateDate,active) VALUES ('Test Meeting', 'Toto','test context','test customer','2021-02-01','2021-02-01','t')").execute())
        .await().indefinitely();
        
        client.query("DROP TABLE IF EXISTS todos").execute()
        .flatMap(r -> client.query("CREATE TABLE todos (id SERIAL PRIMARY KEY"
            + ", text TEXT NOT NULL"
            + ", meetingid DECIMAL)").execute()).await().indefinitely();
    }
   
}
