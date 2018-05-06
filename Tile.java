/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: May 2018
 * This program is a blueprint that represents a tile in a maze
 *
 ****************************************************************************/

public class Tile {
	//This class represents a Tile in a maze
	
	@SuppressWarnings("serial")
	public class InvalidValueException extends Exception {
	    public InvalidValueException(String cause) {
	        super(cause);
	    }
	}
	
	public enum Symbols {
		OPEN ('.'),
		BLOCKED ('#'),
		START ('S'),
		GOAL ('G'),
		PATH ('+');
		
		private char character;
		
		Symbols(char character) {
			//Default constructor
			this.character = character;
		}
		
		public char getChar() { return character; } //Getter
	}
	
	private Symbols symbol;
	private boolean beenVisited = false;
	private boolean correctPath = true;
	private int x;
	private int y;
	
	Tile(char character, int x, int y) throws InvalidValueException {
		//Default constructor
		
		this.x = x;
		this.y = y;
		
		switch(character) {
		case '.': symbol = Symbols.OPEN; break;
		case '#': symbol = Symbols.BLOCKED; break;
		case 'S': symbol = Symbols.START; break;
		case 'G': symbol = Symbols.GOAL; break;
		case '+': symbol = Symbols.PATH; break;
		default: throw new InvalidValueException("Invalid character "
				+ "used in maze layout");
		}
	}
	
	public Symbols getSymbol() { return symbol; } //Getter
	
	public char getChar() { return symbol.getChar(); } //Getter
	
	public boolean notVisited() {
		//Returns true if this tile is a valid place to move
		return !beenVisited && getSymbol() == Symbols.OPEN;
	}
	
	public boolean isCorrect() { return correctPath; } //Getter
	
	public boolean isStart() {
		//Returns true if this tile is the start
		return symbol == Symbols.START;
	}
	
	public int[] getCoordinates() {
		//Returns the coordinates of the tile
		return new int[] {x, y};
	}
	
	public void visit() {
		//Visit this tile
		beenVisited = true;
	}
	
	public void setCorrect(boolean correct) {
		//Set if this tile is the correct path
		correctPath = correct;
	}
}