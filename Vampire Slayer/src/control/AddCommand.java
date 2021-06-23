package control;

import exceptions.*;
import gameElements.Slayer;
import logic.Game;

public class AddCommand extends Command {

	public static final String symbol = "a";
	public static final String name = "add";
	public static final String help = "Adds a Slayer to the board.";
	public static final String commandText = "[a]dd";
	private String slayer;
	private int x;
	private int y;
	private int health;

	public AddCommand() {

		super(AddCommand.symbol, AddCommand.name, AddCommand.commandText, AddCommand.help);

	}

	public AddCommand(String slayer, int x, int y) {

		super(AddCommand.symbol, AddCommand.name, AddCommand.commandText, AddCommand.help);
		this.slayer = slayer;
		this.x = x;
		this.y = y;
		this.health = 3;

	}

	public boolean execute(Game game) throws CommandExecuteException {		

		try {
			Slayer slayeraux = new Slayer(game, x, y, health, 1, "S");		
			return game.addSlayer(slayeraux);
		
		} catch(InvalidPositionException ex) {
			throw new CommandExecuteException("[ERROR]: " + ex.getMessage());
		
		} catch(NotEnoughCoinsException ex) {
			throw new CommandExecuteException("[ERROR]: " + ex.getMessage());

		}
	}

	public Command parse(String[] commandWords, Controller controller) throws CommandParseException {

		try {

			if (commandWords.length == 3 && (this.matchCommandName(commandWords[0]))) {

				if(commandWords.length == 3)
					return new AddCommand(commandWords[1], Integer.valueOf(commandWords[1]), Integer.valueOf(commandWords[2]));
				else
					throw new CommandParseException("Invalid arguments\n");
			}

			else {

				return null;

			}
		
	} catch(NumberFormatException ex) {
		throw new CommandParseException("[ERROR]: " + ex.getMessage());
	}


}




}
