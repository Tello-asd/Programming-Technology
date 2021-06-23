package control;

import java.io.IOException;

import exceptions.CommandExecuteException;
import exceptions.CommandParseException;
import logic.Game;

public class LoadCommand extends Command{

	public static final String symbol = "l";
	public static final String name = "load";
	public static final String help = "Saves the current game state.";
	public static final String commandText = "[l]oad";
	private String fileName;	

	public LoadCommand() {
		super(LoadCommand.name, LoadCommand.symbol, LoadCommand.commandText, LoadCommand.help);
		// TODO Auto-generated constructor stub
	}

	public LoadCommand(String fileName) {
		super(LoadCommand.name, LoadCommand.symbol, LoadCommand.commandText, LoadCommand.help);
		this.fileName = fileName;
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		try {
			game.loadState(this.fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) throws CommandParseException {
		
		if ((commandWords[0].equals(LoadCommand.name) || commandWords[0].equals(LoadCommand.symbol))) {

			if (commandWords.length == 2) {

				return new LoadCommand(commandWords[1]);

			}
			else
				throw new CommandParseException("[ERROR]: load (ld) takes 1 argument: 'fileName'.\n");

		}

		else {

			return null;

		}


	}

}
