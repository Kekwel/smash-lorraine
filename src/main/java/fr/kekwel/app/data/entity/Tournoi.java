package fr.kekwel.app.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity @Data
@Table(name = "Tournoi")
public class Tournoi implements Serializable {
	public Tournoi() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "`date`", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date date; // TODO LocalDateTime

	@OneToMany(mappedBy = "tournoi", targetEntity = MatchSet.class)
	private Set<MatchSet> sets = new HashSet<MatchSet>();
}