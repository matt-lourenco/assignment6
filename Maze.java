/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: May 2018
 * This program is a blueprint that represents a maze. This maze is similar
 *     to a cartesian plane with tiles on the x and y axis. Right and down
 *     are positive. x is on the vertical axis and y is on the horizontal
 *     axis.
 *
 ****************************************************************************/

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {
	//This class represents a maze
	
	@SuppressWarnings("serial")
	public class InvalidInputException extends Exception {
	    public InvalidInputException(String cause) {
	        super(cause);
	    }
	}
	
	private Tile[][] grid;
	
	Maze(String fileLocation) throws Exception {
		//Reads the inputted file to create the grid
		
		//First check if the file is a perfect rectangle of characters.
		@SuppressWarnings("resource")
		Scanner dimScanner = new Scanner(new File(fileLocation));
		int length = dimScanner.nextLine().length();
		int height = 1;
		
		while(dimScanner.hasNextLine()) {
			String line = dimScanner.nextLine();
			if(line.length() != length) {
				throw new InvalidInputException("The inputted text file"
						+ " is not a rectangle");
			}
			height++;
		}
		dimScanner.close();
		
		//Instantiate the maze
		grid = new Tile[height][length];
		
		//Then add the characters to the grid.
		Scanner scanner = new Scanner(new File(fileLocation));
		
		int x = 0; //This is the x coordinate
		
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			
			//convert characters into tiles
			char[] array = line.toCharArray();
			for(int index = 0; index < array.length; index++) {
				grid[x][index] = new Tile(array[index], x , index);
			}
			x++;
		}
		scanner.close();
		
		findPath(findStart());
	}
	
	public int[] findStart() {
		//Finds the startsquare of the maze
		
		for(Tile[] row: grid) {
			for(Tile tile: row) {
				if(tile.isStart()) {
					return tile.getCoordinates();
				}
			}
		}
		
		return new int[] {-1, -1};
	}
	
	public void findPath(int[] coordinates) {
		//This procedure recursively finds the correct path
		
		int x = coordinates[0];
		int y = coordinates[1];
		
		if(!grid[x][y].getSymbol().equals(Tile.Symbols.GOAL)) {
			
			int incorrectTiles = 0;
			
			//Create a list of the surrounding tiles
			ArrayList<Tile> surroundingTiles = new ArrayList<Tile>();
			if(x != 0) {
				surroundingTiles.add(grid[x - 1][y]);
			}
			if(y != grid[0].length - 1) {
				surroundingTiles.add(grid[x][y + 1]);
			}
			if(x != grid.length - 1) {
				surroundingTiles.add(grid[x + 1][y]);
			}
			if(y != 0) {
				surroundingTiles.add(grid[x][y - 1]);
			}
			
			//Check each tile
			for(Tile tile: surroundingTiles) {
				if(tile.notVisited()) {
					tile.visit();
					findPath(tile.getCoordinates());
				}
				if(tile.getSymbol().equals(Tile.Symbols.BLOCKED)
							|| !tile.isCorrect()) {
					incorrectTiles += 1;
				}
			}
			
			//If all paths but the one we arrived from are incorrect mark
			// this tile as incorrect also
			if(incorrectTiles == surroundingTiles.size() - 1) {
				grid[x][y].setCorrect(false);
			}
		}
	}
	
	public void getSolution() {
		//Prints the maze with the solution to the console
		for(Tile[] row: grid) {
			for(Tile tile: row) {
				if(tile.isCorrect() &&
						tile.getSymbol().equals(Tile.Symbols.OPEN)) {
					System.out.print('+');
				} else {
					System.out.print(tile.getChar());
				}
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws Exception {
		//Get the fileLocation from the user
		
		if(args.length > 0) {
			Maze pathFinder = new Maze(args[0]);
			pathFinder.getSolution();
		} else {
			System.out.println("Please attach the file to the end of"
					+ " the command\nex: java Maze ./Maze1.txt");
		}
		
	}
}