package control;

import exceptions.CommandExecuteException;
import exceptions.CommandParseException;
import exceptions.NotEnoughCoinsException;
import logic.Game;

public class WaterCommand extends Command {

	public static final String symbol = "w";
	public static final String name = "water";
	public static final String help = "Kills all vampires same row";
	public static final String commandText = "[W]ater";	
	public String side;

	public WaterCommand() {

		super(WaterCommand.symbol, WaterCommand.name, WaterCommand.commandText, WaterCommand.help);

	}

	public WaterCommand(String side) {
		super(WaterCommand.symbol, WaterCommand.name, WaterCommand.commandText, WaterCommand.help);
		this.side = side;
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {

		try{	
			return game.throwWater(side);
		} catch(NotEnoughCoinsException ex) {
			throw new CommandExecuteException("[ERROR]:" + ex.getMessage());
		}
	}

	@Override
	public Command parse(String[] commandWords, Controller controller) throws CommandParseException {

		if (commandWords.length == 2  && (this.matchCommandName(commandWords[0]))) {
			
			return new WaterCommand( commandWords[1]);

		}
		else
			return null;
	}

}
