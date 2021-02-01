package mx.rememberme.mantras.papi.exceptions;

public class MantraNotFoundException extends Exception {

	private static final long serialVersionUID = 7215415171665655097L;

	public MantraNotFoundException() {
		super("Mantra not found");
	}
}
