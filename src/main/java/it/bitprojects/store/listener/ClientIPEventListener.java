package it.bitprojects.store.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class ClientIPEventListener {

	@EventListener
	public void handleClientIPEvent(RequestHandledEvent e) {
		// Fa qualcosa con l'indirizzo IP del client
		// Ad esempio, lo registra o lo utilizza per altre operazioni
		// ...
		System.out.println("Ciao sono l'evento : ");
		System.out.println(e);
	}
}
