package me.game.tictactoe;

import java.util.Scanner;

import me.game.exception.GameException;
import me.game.service.tictactoe.TicTacToeGameService;
import me.game.service.tictactoe.UsersService;
import me.game.staterepository.tictactoe.TicTacToeGameState;
import me.game.staterepository.tictactoe.UsersState;

public class TicTacToeGame {

	private final static TicTacToeGameService service = new TicTacToeGameService();
	private static TicTacToeGameState state = null;
	private static UsersState us = null;
	private static final UsersService usService = new UsersService();

	public TicTacToeGame(TicTacToeGameState s) {
		state = s;
	}

	public void play(Scanner s) throws GameException {
		int size = state.getSize();
		boolean flag = state.isFlag();
		int n = size * size;
		boolean isWon = false;
		int[][] board = state.getBoard();
		boolean[][] visited = state.getVisited();
		while (n > 0 && !isWon) {
			System.out
					.println("To exit please enter 'e' and to save and exit enter 'se'.\nPress enter 'c' to continue.");
			String str = s.next();
			if (str.equalsIgnoreCase("e")) {
				System.exit(0);
			} else if (str.equalsIgnoreCase("se")) {
				service.saveGameState(state);
				System.exit(0);
			} else if (str.equalsIgnoreCase("c")) {
				String msg = flag ? String.format("%s please enter next cell number as i,j", state.getUser2())
						: String.format("%s please enter next cell number as i,j", state.getUser1());
				System.out.println(msg);
				String input = s.next();
				String[] iArr = input.split(",");
				if (iArr.length < 2 || iArr.length > 2) {
					System.out.println("Incorrect input : " + input);
					continue;
				}
				int x = Integer.parseInt(iArr[0]);
				int y = Integer.parseInt(iArr[1]);
				if (!visited[x][y]) {
					if (flag)
						board[x][y] = 0;
					else
						board[x][y] = 1;
					flag = !flag;
					isWon = this.isWon(board, size);
				} else
					System.out.println(input + " is already marked.");
				this.print();
			} else {
				System.out.println("Incorrect input....");
			}
		}
		if (isWon) {
			String msg =  String.format("Player %s won. Congratulations...", !flag ?state.getUser2() : state.getUser1());
			us.increaseUserScore(!flag ? state.getUser2() : state.getUser1());
			System.out.println(msg);
			usService.saveState(us);
			this.print();
		} else {
			System.out.println("Game draw, none of the player won.");
		}
		service.deleteGameState();
		state.setBoard(board);
		state.setVisited(visited);
	}

	private void print() {
		int size = state.getSize();
		int[][] board = state.getBoard();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] != -1)
					System.out.print(board[i][j]);
				else
					System.out.print("-");
				System.out.print("  ");
			}
			System.out.println();
		}
	}

	private boolean isWon(int[][] board, int size) {
		boolean isWon = true;
		int i = 0;
		for (int j = 0; j < size - 1 && i == 0; j++) {
			for (i = 0; i < size - 1; i++) {
				if (board[j][i] == -1 || board[j][i] != board[j][i + 1]) {
					isWon = false;
					break;
				}
			}

			if (i == size - 1)
				isWon = true;
			else
				i = 0;
			for (; i < size - 1 && !isWon; i++) {
				if (board[i][j] == -1 || board[i][j] != board[i + 1][j]) {
					isWon = false;
					break;
				}
			}
			if (i == size - 1)
				isWon = true;
			else
				i = 0;
		}

		/*
		 * if (i == size - 1) isWon = true; else i = 0; for (; i < size - 1 && !isWon;
		 * i++) { if (board[1][i] == -1 || board[1][i] != board[1][i + 1]) { isWon =
		 * false; break; } }
		 * 
		 * if (i == size - 1) isWon = true; else i = 0; for (; i < size - 1 && !isWon;
		 * i++) { if (board[i][1] == -1 || board[i][1] != board[i + 1][1]) { isWon =
		 * false; break; } }
		 * 
		 * if (i == size - 1) isWon = true; else i = 0; for (; i < size - 1 && !isWon;
		 * i++) { if (board[2][i] == -1 || board[2][i] != board[2][i + 1]) { isWon =
		 * false; break; } }
		 * 
		 * if (i == size - 1) isWon = true; else i = 0; for (; i < size - 1 && !isWon;
		 * i++) { if (board[i][2] == -1 || board[i][2] != board[i + 1][2]) { isWon =
		 * false; break; } }
		 * 
		 * if (i == size - 1) isWon = true; else i = 0; for (; i < size - 1 && !isWon;
		 * i++) { if (board[i][i] == -1 || board[i][i] != board[i + 1][i + 1]) { isWon =
		 * false; break; } }
		 */
		return isWon;
	}

	public static void main(String[] args) {
		try {
			us = getUsersState();
		} catch (GameException e1) {
			e1.printStackTrace();
		}

		do {
			try {
				Scanner s = new Scanner(System.in);
				System.out.println("Enter us to see all users score.");
				System.out.println("Enter p to play game.");
				System.out.println("Enter e to exit.");
				String command = s.next();
				switch (command) {
				case "us":
					us.printUsersState();
					break;
				case "p":
					state = createState(s, us);
					TicTacToeGame game = new TicTacToeGame(state);
					game.play(s);
					break;
				case "e":
					System.exit(0);
					s.close();
					break;
				default:
					System.out.println("Invalid input...exiting the game.");
					System.exit(0);
					break;
				}

			} catch (GameException e) {
				e.printStackTrace();
			}
		} while (true);

	}

	private static UsersState getUsersState() throws GameException {

		UsersState us = usService.getCurrentState();
		if (us == null) {
			us = new UsersState();
			usService.saveState(us);
		}
		return us;
	}

	private static TicTacToeGameState createState(Scanner s, UsersState us) {
		TicTacToeGameState state = null;
		try {
			state = (TicTacToeGameState) service.resumeGame();
			if (state == null) {
				state = new TicTacToeGameState();
				System.out.println("Please enter the game size.");
				state.setSize(s.nextInt());

				System.out.println("Please enter first user name.");
				state.setUser1(s.next());
				us.addUser(state.getUser1());
				System.out.println("Please enter second user name.");
				state.setUser2(s.next());
				us.addUser(state.getUser2());
				int[][] board = new int[state.getSize()][state.getSize()];
				boolean[][] visited = new boolean[state.getSize()][state.getSize()];
				for (int i = 0; i < state.getSize(); i++) {
					for (int j = 0; j < state.getSize(); j++) {
						board[i][j] = -1;
						visited[i][j] = false;
					}
				}
				state.setBoard(board);
				state.setVisited(visited);
			} else {
				System.out.println("Resuming your old game.");
			}
		} catch (GameException e) {
			e.printStackTrace();
		}
		return state;
	}

}
