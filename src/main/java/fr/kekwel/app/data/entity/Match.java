package fr.kekwel.app.data.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity @Data
@Table(name = "Matchs")
public class Match implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@ManyToOne(targetEntity = MatchSet.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "id_set", referencedColumnName = "id", nullable = false) })
	private Set<MatchSet> id_set;

	@ManyToOne(targetEntity = Player.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "matchsP1", referencedColumnName = "id", nullable = false) })
	private Player player1;

	@ManyToOne(targetEntity = Player.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "matchsP2", referencedColumnName = "id", nullable = false) })
	private Player player2;

	@Column(name = "num_match", nullable = true, length = 10)
	private Integer num_match;

	@Column(name = "dat_cre", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dat_cre;// TODO LocalDateTime

	@OneToMany(mappedBy = "match", targetEntity = Bans.class)
	private Set<Bans> bans = new HashSet<Bans>();

	public Match() {}
}