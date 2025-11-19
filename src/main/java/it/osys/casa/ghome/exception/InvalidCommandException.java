package it.osys.casa.ghome.exception;

public class InvalidCommandException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCommandException() {
		super();
	}

	public InvalidCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCommandException(String message) {
		super(message);
	}

	public InvalidCommandException(Throwable cause) {
		super(cause);
	}

}
