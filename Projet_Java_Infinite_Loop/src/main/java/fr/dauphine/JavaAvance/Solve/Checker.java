package fr.dauphine.JavaAvance.Solve;



import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * 
 * @author Zahra, Maria, Katia
 *
 */

public class Checker {

	/**
	 * This function will check one by one each connector and see if it's connected to another one
	 * @param grid
	 * @return boolean true if it's solved and false otherwise
	 */
	public synchronized static boolean isSolution(Grid grid) {
		for(int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				for (Orientation orientation : grid.getPiece(i, j).getConnectors()) {
					switch (orientation) {
					case NORTH -> {
						if (i == 0 || !grid.getPiece(i - 1, j).getConnectors().contains(orientation.getOpposedOrientation())) {
							return false;
						}
					} case EAST -> {
						if (j == grid.getWidth() - 1 || !grid.getPiece(i, j + 1).getConnectors().contains(orientation.getOpposedOrientation())) {
							return false;
						}
					} case SOUTH -> {
						if (i == grid.getHeight() - 1 || !grid.getPiece(i + 1, j).getConnectors().contains(orientation.getOpposedOrientation())) {
							return false;
						}
					} case WEST -> {
						if (j == 0 || !grid.getPiece(i, j - 1).getConnectors().contains(orientation.getOpposedOrientation())) {
							return false;
						}
					}
					}
				}
			}
		}
		return true;
	}
	/**
	 * This function will read the grid from the input file and returns it in the shape of a grid
	 * @param inputFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Grid readGrid(String inputFile) throws FileNotFoundException {
		BufferedReader fr = new BufferedReader(new FileReader(inputFile));
		try {
			Grid grid = new Grid(Integer.parseInt(fr.readLine()),Integer.parseInt(fr.readLine()));
			for(int i = 0; i < grid.getHeight(); i++){
				for(int j = 0; j < grid.getWidth(); j++){
					String[] str = fr.readLine().split(" ");
					if(str.length != 0){
						//setting the pieces read in the grid
						grid.setPiece(i,j,new Piece(i,j, PieceType.getTypeFromDirection(Integer.parseInt(str[0])), Orientation.getOrientations(Integer.parseInt(str[1]))));
					}
				}
			}
			return grid;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
