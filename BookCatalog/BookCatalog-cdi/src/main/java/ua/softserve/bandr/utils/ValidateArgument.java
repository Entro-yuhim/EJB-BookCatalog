package ua.softserve.bandr.utils;

import ua.softserve.bandr.exceptions.NullArgumentException;

/**
 * Created by bandr on 11.02.2016.
 */
public class ValidateArgument {
	private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";

	public static <T> T notNull(T o) {
		if (o == null) {
			throw new NullArgumentException(DEFAULT_IS_NULL_EX_MESSAGE);
		}
		return o;
	}

	public static <T> T notNull(T o, final String message, final Object... values) {
		if (o == null) {
			throw new NullArgumentException(String.format(message, values));
		}
		return o;
	}
}
