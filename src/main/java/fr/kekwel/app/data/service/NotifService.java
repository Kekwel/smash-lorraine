package fr.kekwel.app.data.service;

import org.springframework.stereotype.Service;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

@Service
public class NotifService {

	public void showNotification(String text) {
		Button closeNotif = new Button(new Icon(VaadinIcon.CLOSE));
		closeNotif.addThemeVariants(ButtonVariant.LUMO_ERROR);

		Span details = new Span(text);
		details.getStyle().set("font-weight", "bold");

		Notification notif = new Notification(details, closeNotif);
		notif.setPosition(Position.TOP_CENTER);
		notif.setDuration(3000);

		closeNotif.addClickListener(l -> notif.close());

		notif.open();
	}

	public void showErrorNotification(String text) {
		Button closeNotif = new Button(new Icon(VaadinIcon.CLOSE));
		closeNotif.addThemeVariants(ButtonVariant.LUMO_ERROR);

		Span error = new Span(text);
		error.getStyle().set("color", "red").set("font-weight", "bold");

		Notification notif = new Notification(error, closeNotif);
		notif.setPosition(Position.TOP_CENTER);
		notif.setDuration(3000);

		closeNotif.addClickListener(l -> notif.close());

		notif.open();
	}
}
