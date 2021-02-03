package fr.kekwel.app.data.entity.push;

import fr.kekwel.app.data.entity.Joueur;
import fr.kekwel.app.utils.Constants.BanStatut;
import fr.kekwel.app.utils.Constants.StageEnum;
import lombok.Getter;
import lombok.ToString;

@ToString
public class InfoStage {
	@Getter
	String idLobby;
	@Getter
	StageEnum stage;
	@Getter
	BanStatut statut;

	// Joueur
	@Getter
	Joueur joueur;

	public InfoStage(String id, StageEnum s, BanStatut b, Joueur j) {
		idLobby = id;
		stage = s;
		statut = b;
		joueur = j;
	}
}
