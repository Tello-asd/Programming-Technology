package control;

import Creation.VampireCreation;
import exceptions.*;
import gameElements.Vampire;
import logic.Game;

public class AddvampireCommand extends Command{

	public static final String symbol = "v";
	public static final String name = "vampire";
	public static final String help = "Adds a Vampire to the board.";
	public static final String commandText = "[v]ampire";
	private String type;
	private int x;
	private int y;

	public AddvampireCommand() {

		super(AddvampireCommand.symbol, AddvampireCommand.name, AddvampireCommand.commandText, AddvampireCommand.help);

	}

	public AddvampireCommand(String type, int x, int y) {

		super(AddvampireCommand.symbol, AddvampireCommand.name, AddvampireCommand.commandText, AddvampireCommand.help);
		this.type = type;
		this.x = x;
		this.y = y;

	}

	public boolean execute(Game game) throws CommandExecuteException {	

		try {		
			Vampire vampire = VampireCreation.getVampireByType(this.type, game, this.x, this.y);		
			return game.addVampireByCommand(vampire);

		} catch(InvalidPositionException ex) {
			throw new CommandExecuteException("[ERROR]: " + ex.getMessage());
		
		} catch(NoMoreVampiresException ex) {
			throw new CommandExecuteException("[ERROR]: " + ex.getMessage());
		
		} catch(DraculaHasArisenException ex) {
			throw new CommandExecuteException("[ERROR]: " + ex.getMessage());
		}		
	}

	public Command parse(String[] commandWords, Controller controller) throws CommandParseException {


		try {
			if (commandWords.length == 4 || commandWords.length == 3 && (this.matchCommandName(commandWords[0]))) {

				if(commandWords.length == 4 )
					return new AddvampireCommand(commandWords[1], Integer.valueOf(commandWords[2]), Integer.valueOf(commandWords[3]));
				
				else if(commandWords.length == 3)
					return new AddvampireCommand(commandWords[0], Integer.valueOf(commandWords[1]), Integer.valueOf(commandWords[2]));

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
