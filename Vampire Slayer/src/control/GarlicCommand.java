package control;

import exceptions.*;
import logic.Game;

public class GarlicCommand extends NoParamsCommand {
	
	public static final String symbol = "g";
	public static final String name = "gralic";
	public static final String help = "Push all vampires 1 tile back. Cost is 10 coins";
	public static final String commandText = "[g]arlic";	

	public GarlicCommand() {
		super(GarlicCommand.symbol, GarlicCommand.name, GarlicCommand.commandText, GarlicCommand.help);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		
		try{	
		return game.garlicThrow();
		} catch(NotEnoughCoinsException ex) {
			throw new CommandExecuteException("[ERROR]:" + ex.getMessage());
		}
	}

}
