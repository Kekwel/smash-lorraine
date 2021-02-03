package fr.kekwel.app.data.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity @Data
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "`Set`")
public class MatchSet implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 10)
	private int id;

	@ManyToOne(targetEntity = Tournoi.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "sets", referencedColumnName = "id", nullable = false) })
	private Tournoi tournoi;

	@Column(name = "type_bo", nullable = false, length = 2)
	private int type_bo; // TODO enum

	@OneToMany(mappedBy = "id_set", targetEntity = Match.class)
	private Set<Match> matchs = new HashSet<Match>();

	@OneToMany(mappedBy = "set", targetEntity = Result.class)
	private Set<Result> results = new HashSet<Result>();

	@OneToMany(mappedBy = "set", targetEntity = Lobby.class)
	private Set<Lobby> lobbies = new HashSet<Lobby>();

	public MatchSet() {}
}