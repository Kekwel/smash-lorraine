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
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Entity @Data
@Table(name = "Bans")
public class Bans implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@PrimaryKeyJoinColumn
	@ManyToOne(targetEntity = Match.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "id_match", referencedColumnName = "id", nullable = false) })
	private Match match;

	@Column(name = "num_ban", nullable = true)
	private Integer num_ban;

	@Column(name = "is_counterpick", nullable = true)
	private boolean is_counterpick;

	@OneToOne(mappedBy = "ban", targetEntity = StageSelected.class, fetch = FetchType.LAZY)
	private StageSelected stagesSelected;

	@OneToMany(mappedBy = "ban", targetEntity = StageBanned.class)
	private Set<StageBanned> stagesBanned = new HashSet<StageBanned>();

	public Bans() {}
}