package ua.softserve.bandr.persistence.exceptions;

/**
 * Created by bandr on 04.02.2016.
 */
public class InvalidEntityStateException extends RuntimeException {
	private static final long serialVersionUID = 5422167090948802695L;

	public InvalidEntityStateException(String message) {
		super(message);
	}
}
