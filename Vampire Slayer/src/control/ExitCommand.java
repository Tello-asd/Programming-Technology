package control;

import logic.Game;

public class ExitCommand extends NoParamsCommand {
	
	public static final String shortName = "e";
	public static final String name = "exit";
	public static final String helpText = "Ends the game and terminates the program.";
	public static final String commandText = "[e]xit";

	public ExitCommand() {
		
		super(ExitCommand.name, ExitCommand.shortName, ExitCommand.commandText, ExitCommand.helpText);
		
	}

	@Override
	public boolean execute(Game game) {
		
		game.exitGame();
		return false;
		
	}

}
