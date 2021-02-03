package fr.kekwel.app.data.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity @Data
@Table(name = "Player")
@ToString(exclude = {"matchsP1", "matchsP2", "ORM_result", "ORM_result1", "ORM_lobby", "ORM_lobby1"})
public class Player implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@OneToMany(mappedBy = "player1", targetEntity = Match.class)
	private Set<Match> matchsP1  = new HashSet<Match>();

	@OneToMany(mappedBy = "player2", targetEntity = Match.class)
	private Set<Match> matchsP2 = new HashSet<Match>();

	@OneToOne(mappedBy = "player", targetEntity = StageBanned.class)
	private StageBanned stageBanned;

	@OneToMany(mappedBy = "winner", targetEntity = Result.class)
	private Set<Result> ORM_result = new HashSet<Result>();

	@OneToMany(mappedBy = "loser", targetEntity = Result.class)
	private Set<Result> ORM_result1 = new HashSet<Result>();

	@OneToMany(mappedBy = "p1", targetEntity = Lobby.class)
	private Set<Lobby> ORM_lobby = new HashSet<Lobby>();

	@OneToMany(mappedBy = "p2", targetEntity = Lobby.class)
	private Set<Lobby> ORM_lobby1 = new HashSet<Lobby>();

	public Player() {}
}
