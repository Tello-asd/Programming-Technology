package control;

public abstract class NoParamsCommand extends Command {
	
	/** Executes the super constructor for a command */

	public NoParamsCommand (String name, String shortName, String commandText, String helpText) {

		super(name, shortName, commandText, helpText);

	}

	/** Receives the command name and the controller, if valid, returns the new command assigning the controller */

	public Command parse (String[] commandWords, Controller controller) {

		if ((commandWords.length == 1) && (commandWords[0].equals(this.name) || commandWords[0].equals(this.shortName))) {

			return this;

		}

		else {

			return null;

		}

	}

}
