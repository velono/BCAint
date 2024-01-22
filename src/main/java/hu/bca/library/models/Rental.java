package hu.bca.library.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private Long userId;

	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
	@JsonIgnoreProperties({ "rentals", "authors" })
	@JsonBackReference
	private Book book;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date endDate;
}
