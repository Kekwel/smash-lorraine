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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity @Data
@Table(name = "Stage_Banned")
public class StageBanned implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@ManyToOne(targetEntity = Bans.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "id_bans", referencedColumnName = "id", nullable = false) })
	private Bans ban;

	@OneToOne(optional = false, targetEntity = Player.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "stageBanned", referencedColumnName = "id", nullable = false) })
	private Player player;

	@Column(name = "num_stage_ban", nullable = true, length = 10)
	private Integer num_stage_ban;

	@ManyToMany(mappedBy = "banned", targetEntity = Stage.class)
	private Set<Stage> stages = new HashSet<Stage>();

	public StageBanned() {}
}