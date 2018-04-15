package me.game.service.tictactoe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import me.game.exception.GameException;
import me.game.staterepository.tictactoe.UsersState;

public class UsersService implements IUserStateService {

	private static final String FILE_NAME = "./Users.state";

	@Override
	public void saveState(UsersState us) throws GameException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILE_NAME)))) {
			oos.writeObject(us);
		} catch (FileNotFoundException e) {
			throw new GameException(e.getMessage(), e.getCause());
		} catch (IOException e) {
			throw new GameException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public UsersState getCurrentState() throws GameException {
		File file = new File(FILE_NAME);
		UsersState us = null;
		if (file.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(FILE_NAME)))) {
				us = (UsersState) ois.readObject();
			} catch (FileNotFoundException e) {
				throw new GameException(e.getMessage(), e.getCause());
			} catch (IOException e) {
				throw new GameException(e.getMessage(), e.getCause());
			} catch (ClassNotFoundException e) {
				throw new GameException(e.getMessage(), e.getCause());
			}
		}
		return us;
	}
}
