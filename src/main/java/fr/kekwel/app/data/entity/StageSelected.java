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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity @Data
@Table(name = "Stage_Selected")
public class StageSelected implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@OneToOne(optional = false, targetEntity = Bans.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "id_bans", referencedColumnName = "id", nullable = false) })
	private Bans ban;

	@ManyToOne(targetEntity = Stage.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "selected", referencedColumnName = "id", nullable = false) })
	private Stage stage;

	public StageSelected() {}
}