package me.game.staterepository.tictactoe;

import java.util.Arrays;

public class TicTacToeGameState implements IGameState {
	
	private static final long serialVersionUID = 812639317208877697L;
	
	private int size;
	private int[][] board;
	private boolean[][] visited;
	private boolean flag = false;
	private String user1;
	private String user2;

	public TicTacToeGameState() {}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public boolean[][] getVisited() {
		return visited;
	}

	public void setVisited(boolean[][] visited) {
		this.visited = visited;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getUser1() {
		return user1;
	}

	public void setUser1(String user1) {
		this.user1 = user1;
	}

	public String getUser2() {
		return user2;
	}

	public void setUser2(String user2) {
		this.user2 = user2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + (flag ? 1231 : 1237);
		result = prime * result + size;
		result = prime * result + ((user1 == null) ? 0 : user1.hashCode());
		result = prime * result + ((user2 == null) ? 0 : user2.hashCode());
		result = prime * result + Arrays.deepHashCode(visited);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicTacToeGameState other = (TicTacToeGameState) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (flag != other.flag)
			return false;
		if (size != other.size)
			return false;
		if (user1 == null) {
			if (other.user1 != null)
				return false;
		} else if (!user1.equals(other.user1))
			return false;
		if (user2 == null) {
			if (other.user2 != null)
				return false;
		} else if (!user2.equals(other.user2))
			return false;
		if (!Arrays.deepEquals(visited, other.visited))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TicTacToe [size=" + size + ", board=" + Arrays.toString(board) + ", visited=" + Arrays.toString(visited)
				+ ", flag=" + flag + ", user1=" + user1 + ", user2=" + user2 + "]";
	}

}
