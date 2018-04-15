package me.game.service.tictactoe;

import me.game.exception.GameException;
import me.game.staterepository.tictactoe.UsersState;

public interface IUserStateService {
	public void saveState(UsersState us) throws GameException;
	public UsersState getCurrentState() throws GameException;
}
