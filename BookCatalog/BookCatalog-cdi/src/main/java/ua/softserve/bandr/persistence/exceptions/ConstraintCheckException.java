package ua.softserve.bandr.persistence.exceptions;

/**
 * Created by bandr on 04.02.2016.
 */
public class ConstraintCheckException extends PersistenceException {
	private static final long serialVersionUID = -8640780571327221775L;

	public ConstraintCheckException(String message) {
		super(message);
	}

	public ConstraintCheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConstraintCheckException(Throwable cause) {
		super(cause);
	}
}
