package fr.kekwel.app.utils;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.vaadin.flow.shared.Registration;

import fr.kekwel.app.components.StageLayout.InfoStage;

public class Broadcaster {
    static Executor executor = Executors.newSingleThreadExecutor();

    static LinkedList<Consumer<InfoStage>> listeners = new LinkedList<>();

    public static synchronized Registration register(
            Consumer<InfoStage> listener) {
        listeners.add(listener);

        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcast(InfoStage message) {
        for (Consumer<InfoStage> listener : listeners) {
            executor.execute(() -> listener.accept(message));
        }
    }
}