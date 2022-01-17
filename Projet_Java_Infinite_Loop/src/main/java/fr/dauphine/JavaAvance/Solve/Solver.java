package fr.dauphine.JavaAvance.Solve;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;

import java.util.ArrayList;

import javax.swing.JOptionPane;
/**
 * This class solve the level
 * @author Zahra, Maria, Katia
 *
 */
public class Solver {
	private Grid outputGrid;
	private static String outputFile = null;

	/**
	 * send the grid in the parameters and return true if it is solved
	 * @param i
	 * @param j
	 * @param grid
	 * @return
	 */
	public static boolean solveGrid(Grid grid) {

		//solve the grid 
		//turn the pieces that are not in the right orientation
		for(int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				for (Orientation orientation : grid.getPiece(i, j).getConnectors()) {
					switch (orientation) {
					case NORTH -> {
						if (i == 0 || !grid.getPiece(i - 1, j).getConnectors().contains(orientation.getOpposedOrientation())) {
							grid.getPiece(i - 1, j).turn();
						}
					} case EAST -> {
						if (j == grid.getWidth() - 1 || !grid.getPiece(i, j + 1).getConnectors().contains(orientation.getOpposedOrientation())) {
							grid.getPiece(i - 1, j).turn();
						}
					} case SOUTH -> {
						if (i == grid.getHeight() - 1 || !grid.getPiece(i + 1, j).getConnectors().contains(orientation.getOpposedOrientation())) {
							grid.getPiece(i - 1, j).turn();
						}
					} case WEST -> {
						if (j == 0 || !grid.getPiece(i, j - 1).getConnectors().contains(orientation.getOpposedOrientation())) {
							grid.getPiece(i - 1, j).turn();
						}
					}
					}
				}
			}
		}

		//verify that the grid is solved
		boolean voidType = false;
		if (Checker.isSolution(grid)) {
			voidType = true;
			outputFile = "solvedGrid";
			Generator.writeGrid("./LoopLevels/" + outputFile, grid);
			//send a message solved if it is
			JOptionPane.showMessageDialog(null, "SOLVED: true", "We solved the Infinity LOOP", JOptionPane.INFORMATION_MESSAGE);
			return voidType;
		}else {
			JOptionPane.showMessageDialog(null, "SOLVED: false", "We couldn't solve the Infinity LOOP", JOptionPane.INFORMATION_MESSAGE);
			return voidType;
		}


	}


}
