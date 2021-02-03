package fr.kekwel.app.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

import fr.kekwel.app.data.entity.Lobby;

@Service
public class LobbyService extends CrudService<Lobby, Integer> {

    private LobbyRepository repository;

    public LobbyService(@Autowired LobbyRepository repository) {
        this.repository = repository;
    }

    @Override
    protected LobbyRepository getRepository() {
        return repository;
    }

}
