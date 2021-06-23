package control;
import interfaces.IPrintable;
import logic.Game;
import utils.MyStringUtils;

public class GamePrinter {

	Game game;
	int numRows; 
	int numCols;
	IPrintable printable;
	String[][] board;
	final String space = " ";	

	public GamePrinter (IPrintable printable,  int cols, int rows) {

		this.printable= printable;
		this.numRows = rows;
		this.numCols = cols;

		
	}

	public void encodeGame(Game game) {
		board = new String[game.getLevel().getDim_y()][game.getLevel().getDim_x()];
		String str = "";

		for (int i = 0; i < game.getLevel().getDim_y(); i++) {

			for (int j = 0; j < game.getLevel().getDim_x(); j++) {

				board[i][j] = space;

			}

		}

		str = elemToString(board, game);
	
		System.out.print(str);
		
		

	}

	public String elemToString(String[][] board, Game game) {

		String str = "";
		
		for (int i = 0; i < game.getObjectCount(); i++) {
			board[game.getObjectY(i)][game.getObjectX(i)] = game.getObjectToString(i);
		}
		
		str = toString();
		
		return str;
	}

	public String toString() {		

		int cellSize = 7;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		String intersect = space;
		String vIntersect = space;
		String hIntersect = "-";
		String corner = space;

		String cellDelimiter = MyStringUtils.repeat(hDelimiter, cellSize);

		String rowDelimiter = vIntersect + MyStringUtils.repeat(cellDelimiter + intersect, numCols-1) + cellDelimiter + vIntersect;
		String hEdge =  corner + MyStringUtils.repeat(cellDelimiter + hIntersect, numCols-1) + cellDelimiter + corner;

		String margin = MyStringUtils.repeat(space, marginSize);
		String lineEdge = String.format("%n%s%s%n", margin, hEdge);
		String lineDelimiter = String.format("%n%s%s%n", margin, rowDelimiter);

		StringBuilder str = new StringBuilder();

		str.append(lineEdge);
		for(int i=0; i<numRows; i++) {
			str.append(margin).append(vDelimiter);
			for (int j=0; j<numCols; j++)
				str.append( MyStringUtils.centre(board[i][j], cellSize)).append(vDelimiter);
			if (i != numRows - 1) str.append(lineDelimiter);
			else str.append(lineEdge);   
		}

		return printable.getInfo() + str.toString();
	}

}

