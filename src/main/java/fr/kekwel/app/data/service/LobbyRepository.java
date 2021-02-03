package fr.kekwel.app.data.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.kekwel.app.data.entity.Lobby;

public interface LobbyRepository extends JpaRepository<Lobby, Integer> {

	List<Lobby> findByIdLobby(String lobby);
}