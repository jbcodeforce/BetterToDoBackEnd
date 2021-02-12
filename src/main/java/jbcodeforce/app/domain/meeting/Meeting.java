package jbcodeforce.app.domain.meeting;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jbcodeforce.app.domain.ToDo;


@Entity
@Table(name = "meetings")
@NamedQuery(name = "Meetings.findAll", query = "SELECT m FROM Meeting m ORDER BY m.title")
public class Meeting {
    @Id
    @SequenceGenerator(name = "meetingsSequence", sequenceName = "meetings_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "meetingsSequence")
    public Integer id;
    @Column(length = 50, unique = true)
    public String title;
    public LocalDate creationDate;
    public LocalDate updateDate;
    @Column(length = 1000)
    public String attendees;
    @Column(length = 1000)
    public String context;
    @Column(length = 50)
    public String customer;
    @OneToMany(targetEntity=jbcodeforce.app.domain.ToDo.class, mappedBy="meeting", cascade=CascadeType.ALL)
    public List<ToDo> todos;
    public Boolean active=true;

    public Meeting(){}

    public Meeting(Integer id,String t){
        this.id = id;
        this.title = t;
    }

    public String[] splitAttendees(){
        String[] inv = {};
        if (this.attendees != null) {
            inv = this.attendees.split(",");
        }
        return inv;
    }

    public String toString(){
        return this.customer +  " - " + this.id + " " + this.title;
    }

/** 
 * TO BE USED WHEN USING PGclient
    public static Meeting from(Row row) {
        Meeting m = new Meeting(row.getLong("id"), row.getString("title"));
        m.attendees = Meeting.getStringOrNot(row,"attendees");
        m.customer = Meeting.getStringOrNot(row,"customer");
        m.context = Meeting.getStringOrNot(row,"context");
        try {
            m.creationDate = row.getLocalDate("creationdate");
            m.updateDate = row.getLocalDate("updatedate");
            m.active = row.getBoolean("active");
        } catch(Exception e) {
            // this is bad api.
        }
        return m;
    }

    private static String getStringOrNot(Row row,String colName){
        try {
            return row.getString(colName);
        } catch( java.util.NoSuchElementException e){
            System.err.println(colName + " not found in row");
            System.out.println(row.deepToString());
            return "";
        }
    }

    public Uni<Long> save(PgPool client) {
        if (creationDate == null ) {
            creationDate = LocalDate.now();
        }
        updateDate = LocalDate.now();
        if (this.todos != null) {
            System.out.println(" --------- in to do" );
            Uni<Long> meetingID = SqlClientHelper.inTransactionUni(client, tx -> tx
                .preparedQuery("INSERT INTO meetings (title,attendees,context,customer,active,creationDate,updateDate) VALUES ($1,$2,$3,$4,$5,$6,$7) RETURNING id")
            .execute(Tuple.of(title,attendees,context,customer,active,creationDate).addLocalDate(updateDate))
            .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id")))
            .onItem().transform( meetingid -> {
                for(ToDo todo : todos) {
                    System.out.println(" --------- add to do " + todo.text );
                    client.preparedQuery("INSERT INTO todos (meetingid,text) VALUES($1,$2) RETURNING id")
                        .execute(Tuple.of(meetingid,todo.text))
                        .onItem()
                        .ignore();
                }
                return id;
            });

            
            return meetingID;
        } else {
            System.out.println(" --------- in save without todo" );
            return client.preparedQuery("INSERT INTO meetings (title,attendees,context,customer,active,creationDate,updateDate) VALUES ($1,$2,$3,$4,$5,$6,$7) RETURNING id")
            .execute(Tuple.of(title,attendees,context,customer,active,creationDate).addLocalDate(updateDate))
            .onItem().transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
        }
        
    }
    */
}
