package me.game.exception;

public class GameException extends Exception {
	
	private static final long serialVersionUID = -4398973469296075285L;

	public GameException(String message, Throwable th) {
		super(message, th);
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}
	
	public Throwable getException() {
		return super.getCause();
	}
}
