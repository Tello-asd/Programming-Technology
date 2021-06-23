package control;

import exceptions.*;
import gameElements.BloodBank;
import gameElements.Slayer;
import logic.Game;

public class AddBloodBank extends Command{

	public static final String symbol = "b";
	public static final String name = "bank";
	public static final String help = "Adds a Bloodbank in position.";
	public static final String commandText = "[b]ank";	
	private int x;
	private int y;
	private int z;


	public AddBloodBank() {

		super(AddBloodBank.symbol, AddBloodBank.name, AddBloodBank.commandText, AddBloodBank.help);

	}

	public AddBloodBank(int x, int y, int z) {

		super(AddBloodBank.symbol, AddBloodBank.name, AddBloodBank.commandText, AddBloodBank.help);

		this.x = x;
		this.y = y;
		this.z = z;

	}

	public boolean execute(Game game) throws CommandExecuteException {	

		try {
			BloodBank b = new BloodBank(game, x, y, z);
			return game.addBloodBank(b);
		
		} catch(InvalidPositionException ex) {
			throw new CommandExecuteException("[ERROR]: " + ex.getMessage());
		}

	}

	public Command parse(String[] commandWords, Controller controller) throws CommandParseException {

		try {

			if (commandWords.length == 4 && (this.matchCommandName(commandWords[0]))) {

				if(commandWords.length == 4)
					return new AddBloodBank(Integer.valueOf(commandWords[1]), Integer.valueOf(commandWords[2]), Integer.valueOf(commandWords[3]));
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
