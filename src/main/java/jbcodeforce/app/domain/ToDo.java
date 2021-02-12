package jbcodeforce.app.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jbcodeforce.app.domain.meeting.Meeting;

@Entity
@Table(name = "todos")
//@NamedQuery(name = "Todos.findAll", query = "SELECT m FROM Todo m ORDER BY m.meetingId")
public class ToDo {
    @Id
    @SequenceGenerator(name = "todosSequence", sequenceName = "todos_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "todosSequence")
    public Integer id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="meetingId", nullable=true)
    public Meeting meeting;
    public String text;

    public ToDo(){}

	public ToDo(Meeting meeting, String inText) {
        this.meeting = meeting;
        this.text = inText;
	}
}
