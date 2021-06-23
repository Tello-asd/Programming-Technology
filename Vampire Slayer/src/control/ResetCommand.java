package control;

import logic.Game;

public class ResetCommand extends NoParamsCommand {
	
	public static final String shortName = "r";
	public static final String name = "reset";
	public static final String helpText = "Resets the game.";
	public static final String commandText = "[r]eset";
	
	public ResetCommand() {
		
		super(ResetCommand.name, ResetCommand.shortName, ResetCommand.commandText, ResetCommand.helpText);
		
	}

	@Override
	public boolean execute(Game game) {
		
		game.reset();
		return true;
		

	}

}
