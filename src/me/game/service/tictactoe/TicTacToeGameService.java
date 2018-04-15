package me.game.service.tictactoe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.game.exception.GameException;
import me.game.staterepository.tictactoe.IGameState;
import me.game.staterepository.tictactoe.TicTacToeGameState;

public class TicTacToeGameService implements IGameService {

	private static final String FILE_NAME = "./TicTacToeGame.state";

	@Override
	public void saveGameState(IGameState state) throws GameException {
		this.deleteGameState();
		File file = new File(FILE_NAME);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));) {
			oos.writeObject((TicTacToeGameState) state);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteGameState() throws GameException {
		File file = new File(FILE_NAME);
		if (file.exists())
			file.delete();
	}

	@Override
	public IGameState resumeGame() throws GameException {
		File file = new File(FILE_NAME);
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				return (TicTacToeGameState) ois.readObject();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
