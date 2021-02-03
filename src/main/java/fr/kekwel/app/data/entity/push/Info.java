package fr.kekwel.app.data.entity.push;

import fr.kekwel.app.data.entity.Joueur;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Info {
	@Getter
	String idLobby;
	@Getter @Setter
	Joueur j1, j2;
	
	public Info(String id) {
		idLobby = id;
	}
}
