package fr.kekwel.app.utils;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

import fr.kekwel.app.data.entity.push.Info;
import fr.kekwel.app.data.entity.push.InfoStage;

public class Broadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Consumer<InfoStage>> listenersStage = new LinkedList<>();
    
    static LinkedList<Consumer<Info>> listenersInfo = new LinkedList<>();

    /* Infos stages (stage, statut ban, joueur, lobby) */
    public static synchronized Registration registerInfoStage(Consumer<InfoStage> listener) {
    	listenersStage.add(listener);
    	
    	return () -> {
    		synchronized (Broadcaster.class) {
    			listenersStage.remove(listener);
    		}
    	};
    }
    
    public static synchronized void broadcastInfoStage(InfoStage message) {
    	for (Consumer<InfoStage> listener : listenersStage) {
    		executor.execute(() -> listener.accept(message));
    	}
    }
    
    /* Infos vue ban (joueur 1, joueur 2, lobby) */
    public static synchronized Registration registerInfo(Consumer<Info> listener) {
    	listenersInfo.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
            	listenersInfo.remove(listener);
            }
        };
    }

    public static synchronized void broadcastInfo(Info message) {
        for (Consumer<Info> listener : listenersInfo) {
            executor.execute(() -> listener.accept(message));
        }
    }
}