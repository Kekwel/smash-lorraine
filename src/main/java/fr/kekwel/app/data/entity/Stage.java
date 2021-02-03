package fr.kekwel.app.data.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity @Data
@Table(name = "Stage")
public class Stage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "shortname", nullable = true)
	private String shortname;

	@ManyToMany(targetEntity = StageBanned.class)
	@JoinTable(name = "Stage_Banned_Stage", joinColumns = { @JoinColumn(name = "id_stage") }, inverseJoinColumns = {
			@JoinColumn(name = "id_stage_banned") })
	private Set<StageBanned> banned = new HashSet<StageBanned>();

	@OneToMany(mappedBy = "stage", targetEntity = StageSelected.class)
	private Set<StageSelected> selected = new HashSet<StageSelected>();

	public Stage() {}

}