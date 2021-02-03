package fr.kekwel.app.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity @Data
@Table(name = "Lobby")
public class Lobby implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "id_access", nullable = false)
	private String idLobby;

	@Column(name = "statut", nullable = false)
	private String statut;

	@ManyToOne(targetEntity = Player.class)
	@JoinColumns({ @JoinColumn(name = "p1", referencedColumnName = "id", nullable = false) })
	private Player p1;

	@ManyToOne(targetEntity = Player.class)
	@JoinColumns({ @JoinColumn(name = "p2", referencedColumnName = "id", nullable = false) })
	private Player p2;

	@ManyToOne(targetEntity = MatchSet.class)
	@JoinColumns({ @JoinColumn(name = "lobbies", referencedColumnName = "id") })
	private MatchSet set;

	public Lobby() {}

	@Override
	public String toString() {
		return "Lobby [id=" + id + ", idLobby=" + idLobby + ", statut=" + statut + ", p1=" + p1 + ", p2=" + p2
				+ ", set=" + set + "]";
	}

}