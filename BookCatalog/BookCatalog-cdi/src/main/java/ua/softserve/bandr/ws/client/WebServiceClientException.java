package ua.softserve.bandr.ws.client;

/**
 * Created by bandr on 11.02.2016.
 */
public class WebServiceClientException extends Exception {
	private static final long serialVersionUID = 2416712023588739184L;

	public WebServiceClientException(String message) {
		super(message);
	}

	public WebServiceClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebServiceClientException(Throwable cause) {
		super(cause);
	}

	public WebServiceClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
