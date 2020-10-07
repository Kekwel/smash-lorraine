package fr.kekwel.app.utils;

import lombok.Getter;

public class Constants {

	public enum BanStatut {
		BANNED,
		NOT_BANNED
	}
	
	public static final String stagePath = "images/stages";
	public enum Stage {
		BF ("Battlefield", stagePath + "/BF.jpg"),
		FD ("Final Destination", stagePath + "/FD.jpg"),
		PS2 ("Pokemon Stadium 2", stagePath + "/PS2.jpg"),
		SBF ("Small Battlefield", stagePath + "/SBF.jpg"),
		SV ("Smashville", stagePath + "/SV.jpg"),
		TC ("Town & City", stagePath + "/TC.jpg"),
		YS ("Yoshi's Island", stagePath + "/YS.jpg"),;
		
		@Getter
		private String title;
		@Getter
		private String imgPath;
		Stage(String title, String imgPath) {
			this.title = title;
			this.imgPath = imgPath;
		}
	}
	
	public static final Stage[] stagelist_Helios = new Stage[] {Stage.BF, Stage.FD, Stage.PS2, Stage.SBF, Stage.SV, Stage.TC, Stage.YS};

	/* ---- */
	public static String crossPath = "images/cross_j1.png";
}
