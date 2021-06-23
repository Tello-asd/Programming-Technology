package control;

import java.util.Scanner;

import exceptions.*;
import interfaces.IPrintable;
import logic.*;

public class Controller {


	public final String prompt = "Command > ";
	public static final String unknownCommandMsg = String.format("Unknown command");
	public static final String invalidCommandMsg = String.format("Invalid command");
	public static final String invalidPositionMsg = String.format("Invalid position");

	private Game game;
	private Scanner scanner;	
	private Level level;

	public Controller(Game game) {

		this.game = game;
		this.scanner = new Scanner(System.in);

	}

	public void run(){	

		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		this.game.update();
		boolean refreshDisplay = false;

		while (!game.isFinished()){	

			System.out.print(prompt);
			String[] words = in.nextLine().toLowerCase().trim().split ("\\s+");

			try {
				Command command = CommandGenerator.parseCommand(words,this);

				if(command != null) {
					refreshDisplay = command.execute(game);

					if(refreshDisplay)
					{					
						this.game.update();
						
					}
				}
				else {
					System.out.println(unknownCommandMsg);
				}

			} catch(GameException ex) {
				System.out.format(ex.getMessage() + "%n%n");
			}			

		}
		
		System.out.println(this.game.endGame());

	}

}

