package control;

import exceptions.CommandExecuteException;
import exceptions.CommandParseException;
import logic.Game;

public class StringifyCommand extends Command{
	
	public static final String symbol = "t";
	public static final String name = "stringify";
	public static final String help = "Convert to string the state of the game";
	public static final String commandText = "s[t]ringify";
	public String str = "";

	public StringifyCommand() {
		super(StringifyCommand.symbol, StringifyCommand.name, StringifyCommand.commandText, StringifyCommand.help);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		System.out.println(game.stringify(str));

		return false;
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) throws CommandParseException {
		if ((this.matchCommandName(commandWords[0]))) {

			if (commandWords.length == 1) {

				return new StringifyCommand();

			}
			else
				throw new CommandParseException("[ERROR]: save command takes 1 argument: 'fileName'.\n");

		}

		else {

			return null;

		}
	}

}
