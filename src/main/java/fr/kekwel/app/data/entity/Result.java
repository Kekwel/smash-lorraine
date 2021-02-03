package fr.kekwel.app.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity @Data
@Table(name = "Result")
public class Result implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@ManyToOne(targetEntity = MatchSet.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "results", referencedColumnName = "id", nullable = false) })
	private MatchSet set;

	@ManyToOne(targetEntity = Player.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "winner", referencedColumnName = "id", nullable = false) })
	private Player winner;

	@ManyToOne(targetEntity = Player.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "loser", referencedColumnName = "id", nullable = false) })
	private Player loser;

	@Column(name = "score_winner", nullable = true)
	private Integer score_winner;

	@Column(name = "score_loser", nullable = true)
	private Integer score_loser;

	public Result() {}
}