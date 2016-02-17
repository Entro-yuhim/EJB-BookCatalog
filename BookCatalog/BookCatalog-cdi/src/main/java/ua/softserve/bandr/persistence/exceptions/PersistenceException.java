package ua.softserve.bandr.persistence.exceptions;

/**
 * Created by bandr on 17.02.2016.
 */
public class PersistenceException extends Exception {

	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenceException(Throwable cause) {
		super(cause);
	}
}
