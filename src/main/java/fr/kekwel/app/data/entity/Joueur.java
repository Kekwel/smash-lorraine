package fr.kekwel.app.data.entity;

import com.vaadin.flow.component.UI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Joueur {
	private UI ui;
	private int num;
	
	public boolean isJ2() {
		return num == 2;
	}
}