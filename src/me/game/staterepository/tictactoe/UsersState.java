package me.game.staterepository.tictactoe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import me.game.exception.GameException;
import me.game.service.tictactoe.IUserStateService;
import me.game.service.tictactoe.UsersService;

public class UsersState implements Serializable {

	private static final long serialVersionUID = 1017964476630783900L;

	Map<String, Integer> userMap = null;
	private static final IUserStateService service = new UsersService();

	public UsersState() {
		UsersState state = null;
		try {
			state = service.getCurrentState();
		} catch (GameException e) {
			e.printStackTrace();
		}
		if (state == null) {
			this.userMap = new HashMap<>();
		} else if (state.userMap == null) {
			this.userMap = new HashMap<>();
		} else
			this.userMap = state.userMap;
	}

	public void addUser(String name) {
		this.userMap.put(name, this.userMap.getOrDefault(name, 0));
	}
	
	public void increaseUserScore(String name) {
		this.userMap.put(name, this.userMap.getOrDefault(name, -1) + 1);
	}

	public void printUsersState() {
		if(this.userMap.size() <= 0)
			System.out.println("No players found.");
		
		this.userMap.forEach((k, v) -> {
			System.out.println(String.format("Username : %s, Score : %d", k, v));
		});
	}
}
