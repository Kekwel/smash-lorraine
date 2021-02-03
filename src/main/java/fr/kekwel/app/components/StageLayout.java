package fr.kekwel.app.components;

import com.github.appreciated.card.RippleClickableCard;
import com.github.appreciated.card.content.IconItem;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import fr.kekwel.app.data.entity.Joueur;
import fr.kekwel.app.data.entity.push.InfoStage;
import fr.kekwel.app.utils.Broadcaster;
import fr.kekwel.app.utils.Constants;
import fr.kekwel.app.utils.Constants.BanStatut;
import fr.kekwel.app.utils.Constants.StageEnum;
import lombok.Setter;

public class StageLayout extends VerticalLayout {

	@Setter
	private String idLobby;
	private BanStatut statut = BanStatut.NOT_BANNED;
	private StageEnum stage;
	
	@Setter
	private Joueur currentJ;
	
	/* Composants */
	private Span title;
	
	private VerticalLayout vLayout = new VerticalLayout();
	private Image img;
	private RippleClickableCard card;
	private Image cross;
	
	public StageLayout(String idLobby, StageEnum stage) {
		setWidth(null);
		setId(stage.name());
		setSpacing(false);
		
		this.idLobby = idLobby;
		this.stage = stage;
		
		title = new Span(stage.getTitle());
		title.getStyle().set("font-weight", "bold")
						.set("font-size", "larger")
						.set("color", "rgb(235, 89, 5)");
		
		cross = createCross();
		
		card = createStageCard(stage.getImgPath(), stage.getTitle(), "");
		
		add(card);
		setAlignItems(Alignment.CENTER);
	}
	
	public RippleClickableCard createStageCard(String imgPath, String title, String description) {
		img = new Image(imgPath, title);
		IconItem iconItem = new IconItem(img, "", description);
		
		vLayout.setSpacing(false);
		vLayout.add(this.title, iconItem);

		RippleClickableCard card = new RippleClickableCard(vLayout);
		card.addClassName(BanStatut.NOT_BANNED.name());
		
		// TODO deplacer listener dans vue ? pour savoir qui a cliqué ?
		this.addClickListener(l -> {
			if (this.statut.equals(BanStatut.NOT_BANNED)) {
				this.statut = BanStatut.BANNED;
				vLayout.add(cross);
			} else {
				this.statut = BanStatut.NOT_BANNED;
				vLayout.remove(cross);
			}
			
			if (currentJ != null && currentJ.isJ2())
				cross.addClassName("j2");
			else 
				cross.removeClassName("j2");
			
			// TODO css transition ?
			img.removeClassNames(BanStatut.BANNED.name(), BanStatut.NOT_BANNED.name());
			img.addClassName(this.statut.name());
			
//			Notification.show(title + " : " + this.statut);
			InfoStage infoStage = new InfoStage(idLobby, stage, statut, currentJ);
	        Broadcaster.broadcastInfoStage(infoStage);
		});
		
		return card;
	}
	
	// refresh suite à reception "push"
	public void refresh(InfoStage infoStage) {
		this.statut = infoStage.getStatut();
		
		if (this.statut.equals(BanStatut.BANNED))
			vLayout.add(cross);
		else
			vLayout.remove(cross);
		
		if (infoStage.getJoueur() != null && infoStage.getJoueur().isJ2())
			cross.addClassName("j2");
		else 
			cross.removeClassName("j2");
		
		img.removeClassNames(BanStatut.BANNED.name(), BanStatut.NOT_BANNED.name());
		img.addClassName(this.statut.name());
//		Notification.show("REFRESH " + stage.getTitle() + " : " + this.statut);
	}
	
	public void reset(boolean sendPUSH) {
		this.statut = BanStatut.NOT_BANNED;
		if(cross != null)
			vLayout.remove(cross);
		img.removeClassNames(BanStatut.BANNED.name(), BanStatut.NOT_BANNED.name());
		
		if (sendPUSH) {
			InfoStage infoStage = new InfoStage(idLobby, stage, statut, currentJ);
	        Broadcaster.broadcastInfoStage(infoStage);
		}
	}
	
	private Image createCross() {
		// TODO player color
		Image cross = new Image(Constants.crossPath, "cross");
		cross.setHeight("170px");
		cross.setWidth("170px");
		cross.addClassName("cross");
		return cross;
	}
}
