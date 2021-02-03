package fr.kekwel.app.utils;

import lombok.Getter;

public class Constants {

	public enum BanStatut {
		BANNED,
		NOT_BANNED
	}
	
	public static final String stagePath = "images/stages";
	public enum StageEnum {
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
		StageEnum(String title, String imgPath) {
			this.title = title;
			this.imgPath = imgPath;
		}
	}
	
	public static final StageEnum[] stagelist_Helios = new StageEnum[] {StageEnum.BF, StageEnum.FD, StageEnum.PS2, StageEnum.SBF, StageEnum.SV, StageEnum.TC, StageEnum.YS};

	/* ---- */
	public static String crossPath = "images/cross_j1.png";
}
