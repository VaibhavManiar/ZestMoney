package me.game.service.tictactoe;

import me.game.exception.GameException;
import me.game.staterepository.tictactoe.IGameState;

public interface IGameService {
	/**
	 * 
	 * @param state
	 * @throws GameException
	 */
	public void saveGameState(IGameState state) throws GameException;
	
	/**
	 * 
	 * @throws GameException
	 */
	public void deleteGameState() throws GameException;
	
	/**
	 * 
	 * @return
	 * @throws GameException
	 */
	public IGameState resumeGame() throws GameException;
}
