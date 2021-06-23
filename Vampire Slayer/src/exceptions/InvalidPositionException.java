package exceptions;

@SuppressWarnings("serial")
public class InvalidPositionException extends CommandExecuteException {

	public InvalidPositionException(String msg) {
		super(msg);
		
	}

}
