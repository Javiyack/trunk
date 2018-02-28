package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
		"reservation_id", "question_id"
	}))
public class Answer extends DomainEntity{

	private String text;

	private Reservation reservation;
	
	private Question question;

	@NotBlank
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation rSVP) {
		this.reservation = rSVP;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	
}
