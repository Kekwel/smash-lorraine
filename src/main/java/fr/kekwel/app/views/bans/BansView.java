package fr.kekwel.app.views.bans;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.css.grid.GridLayoutComponent;
import com.github.appreciated.css.grid.sizes.Flex;
import com.github.appreciated.css.grid.sizes.Length;
import com.github.appreciated.css.grid.sizes.MinMax;
import com.github.appreciated.css.grid.sizes.Repeat;
import com.github.appreciated.layout.FlexibleGridLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.shared.Registration;

import fr.kekwel.app.components.StageLayout;
import fr.kekwel.app.data.entity.Joueur;
import fr.kekwel.app.data.entity.push.Info;
import fr.kekwel.app.data.service.LobbyService;
import fr.kekwel.app.data.service.NotifService;
import fr.kekwel.app.utils.Broadcaster;
import fr.kekwel.app.utils.Constants;
import fr.kekwel.app.utils.Constants.StageEnum;
import fr.kekwel.app.utils.MiscUtils;
import fr.kekwel.app.views.main.MainView;

@Route(value = "bans", layout = MainView.class)
@PageTitle("Bans")
@CssImport("./styles/views/bans/bans.css")
@RouteAlias(value = "", layout = MainView.class)
public class BansView extends HorizontalLayout implements HasUrlParameter<String> {
	Logger log = LoggerFactory.getLogger(BansView.class);
	
	/* Injections */
	@Autowired
	private NotifService notifService;
	@Autowired
	private LobbyService lobbyService;
	
	Registration broadcasterRegistrationStage;
	Registration broadcasterRegistrationInfo;
	
	private Map<String, StageLayout> stagesLayout = new HashMap<String, StageLayout>();
	
	private String idLobby = "";
	private H1 idLobbyText;
	
	// TODO button responsive
	private Button resetBan;
	
	private Button generateLobby;
	
	private Button joinLobby;
	private Dialog dialogJoinLobby;
	
	// -- you are J1 or J2
	private Button j1Btn, j2Btn, resetJ;
	private Joueur currentJ;
	private Joueur otherJ;
	// TODO si les 2 choisis, vous etes spectateur
	// TODO 1st bans or "Counterpick"
	// TODO share link ?
	// TODO 1st ban : 2 - 3 - 1
	// TODO CP : 3
	// TODO revoir icones

	@PostConstruct
	public void init() {
		System.err.println("init");
		System.err.println(lobbyService.get(1));
		
		setId("bans-stage");
		setMargin(false);
		setSpacing(false);
		setSizeFull();

		// Infos + Choix joueur
		HorizontalLayout infoLobby = new HorizontalLayout();
		infoLobby.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		
		H2 lobbyLabel = new H2("Lobby");
		lobbyLabel.getStyle().set("margin-top", "0.5em")
							.set("margin-bottom", "0");
		
		idLobbyText = new H1();
		idLobbyText.getStyle().set("font-weight", "bold")
								.set("color", "rgb(235, 89, 5)")
								.set("margin-top", "0.5em")
								.set("margin-bottom", "0");
		
		j1Btn = new Button("J1", new Icon(VaadinIcon.USER));
		j1Btn.addThemeVariants(ButtonVariant.LUMO_ERROR);
		j1Btn.addClickListener(l -> {
			currentJ = new Joueur(getUI().get(), 1);
			
			// pour chaque stage de l'ui, on met le joueur 1
			stagesLayout.forEach((stage, layout) -> layout.setCurrentJ(currentJ));
			
			j1Btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
			
			j2Btn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
			j2Btn.setEnabled(false);
			
			Info info = new Info(idLobby);
			info.setJ1(currentJ);
	        Broadcaster.broadcastInfo(info);
		});
		
		j2Btn = new Button("J2", new Icon(VaadinIcon.USER));
		j2Btn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
		j2Btn.addClickListener(l -> {
			currentJ = new Joueur(getUI().get(), 2);
		
			// pour chaque stage de l'ui, on met le joueur 2
			stagesLayout.forEach((stage, layout) -> layout.setCurrentJ(currentJ));

			j2Btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

			j1Btn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
			j1Btn.setEnabled(false);
			
			Info info = new Info(idLobby);
			info.setJ2(currentJ);
	        Broadcaster.broadcastInfo(info);
		});
		
		resetJ = new Button(new Icon(VaadinIcon.REFRESH));
		resetJ.addClickListener(l -> {
			currentJ = null;
			
			j1Btn.setEnabled(true);
			j1Btn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
			
			j2Btn.setEnabled(true);
			j2Btn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
			
			Info info = new Info(idLobby);
	        Broadcaster.broadcastInfo(info);
		});
		
		// -- Reset bans stage
		resetBan = new Button("", new Icon(VaadinIcon.REFRESH));
		resetBan.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
		resetBan.addClickListener(l -> {
			// reset statut stages, avec PUSH
			stagesLayout.forEach((stage, layout) -> layout.reset(true));
		});
		
		// -- Generate new lobby
		generateLobby = new Button("Nouveau lobby", new Icon(VaadinIcon.PLUS));
		generateLobby.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		generateLobby.addClickListener(l -> {
			String newId = MiscUtils.generateRandomString();
			log.info("-- Création lobby " + newId);
			getUI().ifPresent(ui -> ui.navigate("bans/" + newId));
			
			// reset statut stages, sans PUSH
			stagesLayout.forEach((stage, layout) -> layout.reset(false));
		});
		
		// -- Dialog Join Lobby
		dialogJoinLobby = new Dialog();
		dialogJoinLobby.setModal(true);
		dialogJoinLobby.setCloseOnOutsideClick(false);
		
		TextField idLobbyTF = new TextField("ID du lobby", "_ _ _ _ _");
		idLobbyTF.setMaxLength(5);
		idLobbyTF.setValueChangeMode(ValueChangeMode.EAGER);
		idLobbyTF.addValueChangeListener(l -> {
			if (idLobbyTF.getValue() != null) idLobbyTF.setValue(idLobbyTF.getValue().toUpperCase());
		});
		dialogJoinLobby.addOpenedChangeListener(l -> idLobbyTF.focus());
		
		Button confirm = new Button("", new Icon(VaadinIcon.CHECK));
		confirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		confirm.addClickShortcut(Key.ENTER);
		confirm.addClickListener(l -> {
			String newIdLobby = idLobbyTF.getValue();
			
			if (StringUtils.isEmpty(newIdLobby)) {
				notifService.showErrorNotification("Veuillez rentrer un identifiant !");
			} else if (newIdLobby.length() != 5) {
				notifService.showErrorNotification("Veuillez rentrer 5 caractères !");
			} else if (!StringUtils.isAlphanumeric(newIdLobby)) {
				notifService.showErrorNotification("Veuillez renseigner seulement des chiffres et des lettres !");
			} else {
				dialogJoinLobby.close();
				getUI().ifPresent(ui -> ui.navigate("bans/" + newIdLobby));
			}
		});
		
		Button cancel = new Button("", new Icon(VaadinIcon.CLOSE), l -> dialogJoinLobby.close());
		cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
		
		dialogJoinLobby.add(idLobbyTF, confirm, cancel);
		// --
		
		joinLobby = new Button("Rejoindre", new Icon(VaadinIcon.SIGN_IN));
		joinLobby.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
		joinLobby.addClickListener(l -> {
			idLobbyTF.setValue("");
			dialogJoinLobby.open();
		});
		
		FlexibleGridLayout layout = new FlexibleGridLayout()
				.withColumns(Repeat.RepeatMode.AUTO_FILL, new MinMax(new Length("250px"), new Flex(1)))
				.withAutoRows(new Length("275px")).withPadding(true).withSpacing(true)
				.withAutoFlow(GridLayoutComponent.AutoFlow.ROW_DENSE).withOverflow(GridLayoutComponent.Overflow.AUTO);

		layout.setSizeFull();
		
		// Boutons
		HorizontalLayout btnLayout = new HorizontalLayout(resetBan, generateLobby, joinLobby);
		
		infoLobby.add(lobbyLabel, idLobbyText, j1Btn, j2Btn, resetJ);
		add(btnLayout, infoLobby, layout);

		for (StageEnum stage : Constants.stagelist_Helios) {
			StageLayout stageL = new StageLayout(idLobby, stage);
			stagesLayout.put(stage.name(), stageL);
			layout.add(stageL);
		}
	
	}
	
	@Override
	public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
		idLobby = parameter != null ? parameter : "";
		idLobbyText.setText(idLobby);
		
		stagesLayout.forEach((s, layout) -> {
			layout.setIdLobby(idLobby);
		});
		
		// TODO recup des infos ? bans, joueur etc
		
	}
	
	@Override
    protected void onAttach(AttachEvent attachEvent) {
		System.err.println("on attach");
        UI ui = attachEvent.getUI();
        broadcasterRegistrationStage = Broadcaster.registerInfoStage(infoStage -> {
        	ui.access(() -> {
        		getUI().get().access(() -> {
        			// si meme id lobby
        			if (!StringUtils.isEmpty(idLobby) && idLobby.equals(infoStage.getIdLobby())) {
        				StageLayout stageClicked = stagesLayout.get(infoStage.getStage().name());
        				stageClicked.refresh(infoStage);
        				
        				getUI().get().push();
        			}
        		});
        	});
        });
        
        broadcasterRegistrationInfo = Broadcaster.registerInfo(info -> {
            ui.access(() -> {
            	if (getUI().isPresent()) {
            		getUI().get().access(() -> {
            			// si meme id lobby
            			if (!StringUtils.isEmpty(idLobby) && idLobby.equals(info.getIdLobby())) {
            				// si j1 pris, on désactive btn J1
            				if (info.getJ1() != null) {
            					notifService.showNotification("Joueur 1 sélectionné");
            					j1Btn.setEnabled(false);
            				}
            				
            				// si j2 pris, on désactive btn J2
            				if (info.getJ2() != null) {
            					notifService.showNotification("Joueur 2 sélectionné");
            					j2Btn.setEnabled(false);
            				}
            				
            				// si les 2j sont null, c'est un reset
            				if (info.getJ1() == null && info.getJ2() == null) {
            					notifService.showNotification("Reset des joueurs");
            					j1Btn.setEnabled(true);
            					j2Btn.setEnabled(true);
            				}
            				
            				getUI().get().push();
            			}
            		});
            	}
            });
        });
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistrationStage.remove();
        broadcasterRegistrationStage = null;
    }
    
}