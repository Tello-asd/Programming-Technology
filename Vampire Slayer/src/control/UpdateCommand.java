package control;

import logic.Game;

public class UpdateCommand extends NoParamsCommand {
	
	public static final String shortName = "";
	public static final String name = "";
	public static final String helpText = "Updates the Game";
	public static final String commandText = "[ENTER]";


	public UpdateCommand() {
		
		super(UpdateCommand.name, UpdateCommand.shortName, UpdateCommand.commandText, UpdateCommand.helpText);
		
	}	

	@Override
	public boolean execute(Game game) {
		
		game.noAction();
		return true;
		
	}


}
