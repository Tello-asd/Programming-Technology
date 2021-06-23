package control;

import java.io.IOException;
import exceptions.*;
import logic.Game;

public class SaveCommand extends Command{

	public static final String symbol = "s";
	public static final String name = "save";
	public static final String help = "Saves the current game state.";
	public static final String commandText = "[s]ave";
	private String fileName;	

	public SaveCommand() {

		super(SaveCommand.symbol, SaveCommand.name, SaveCommand.commandText, SaveCommand.help);

	}

	public SaveCommand(String fileName) {

		super(SaveCommand.symbol, SaveCommand.name, SaveCommand.help, SaveCommand.commandText);
		this.fileName = fileName;		

	}

	public boolean execute(Game game) throws CommandExecuteException {
		boolean ok = false;

		try {

			ok = game.saveGame(this.fileName);			

		} catch (IOException ex) {
			throw new CommandExecuteException("[ERROR]: " + ex.getMessage());
		}

		return ok;

	}
	
	@Override
	public Command parse(String[] commandWords, Controller controller) throws CommandParseException {
		if ((this.matchCommandName(commandWords[0]))) {

			if (commandWords.length == 2) {

				return new SaveCommand(commandWords[1]);

			}
			else
				throw new CommandParseException("[ERROR]: save command takes 1 argument: 'fileName'.\n");

		}

		else {

			return null;

		}
	}

}