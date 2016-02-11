package ua.softserve.bandr.exceptions;

/**
 * Created by bandr on 11.02.2016.
 */
public class NullArgumentException extends IllegalArgumentException {
	public NullArgumentException() {
	}

	public NullArgumentException(String s) {
		super(s);
	}

	public NullArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public NullArgumentException(Throwable cause) {
		super(cause);
	}
}
