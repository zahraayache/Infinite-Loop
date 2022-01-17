package fr.dauphine.JavaAvance.Solve;


import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;
import org.codehaus.plexus.util.NioFiles;
/**
 * 
 * @author Zahra, Maria, Katia
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	private static Grid filledGrid;

	/**
	 * return generated level grid and write it inside the file by calling method write grid
	 * @param inputGrid
	 * @param width
	 * @param height
	 * @param nbcc
	 * @return
	 */
	public static Grid generateLevel(String inputGrid, int width, int height, int nbcc) {
		Random random = new Random();
		Grid grid = new Grid(width, height, nbcc);
		int[][] lines = new int[height * 2 - 1][width];

		for (int i = 0; i < height * 2 - 1; i++) {
			for (int j = 0; j < width; j++) {
				if (random.nextInt(2) == 1) { //generating randomly the shapes
					lines[i][j] = 1;
				} else {
					lines[i][j] = 0;
				}
			}
		}

		for (int i = 0; i < height * 2 - 1; i+=2) {
			for (int j = 0; j < width; j++) {
				boolean north = false;
				boolean east = false;
				boolean south = false;
				boolean west = false;
				int nbConnectors = 0; // numbers of connectors
				if (j < width - 1 && lines[i][j] == 1) {
					west = true;
					nbConnectors++;
				}
				if (i > 0 && lines[i - 1][j] == 1) {
					north = true;
					nbConnectors++;
				}
				if (i < height * 2 - 2 && lines[i + 1][j] == 1) {
					south = true;
					nbConnectors++;
				}
				if (j > 0 && lines[i][j - 1] == 1) {
					east = true;
					nbConnectors++;
				}

				Piece piece = null;
				switch (nbConnectors) {
				case 0:
					piece = new Piece(i, j, PieceType.VOID, Orientation.NORTH);
					break;
				case 1:
					piece = new Piece(i, j, PieceType.ONECONN, Orientation.NORTH);
					break;
				case 2:
					if ((north && south) || (east && west))
						piece = new Piece(i, j, PieceType.BAR, Orientation.NORTH);
					else
						piece = new Piece(i, j, PieceType.LTYPE, Orientation.NORTH);
					break;
				case 3:
					piece = new Piece(i, j, PieceType.TTYPE, Orientation.NORTH);
					break;
				case 4:
					piece = new Piece(i, j, PieceType.FOURCONN, Orientation.NORTH);
					break;
				}
				piece.setOrientation(random.nextInt(4)); //generating randomly the orientation
				grid.setPiece(i/2, j, piece);
			}
		}
		writeGrid(inputGrid, grid);
		return grid;
	}

	/**
	 * write the grid in file
	 * @param fileName
	 * @param inputGrid
	 */
	public static void writeGrid(String fileName, Grid inputGrid) {
		try {
			FileWriter myWriter = new FileWriter(fileName, false);
			myWriter.write(inputGrid.getWidth()+"\n");
			myWriter.write(inputGrid.getHeight()+"\n");
			Piece[][] pieces = inputGrid.getPieces();
			for(int i = 0; i < inputGrid.getHeight(); i++) {
				for (int j = 0; j < inputGrid.getWidth(); j++) {
					StringBuilder str = new StringBuilder();
					str.append(Piece.getIntTypeFromPiece(pieces[i][j])+" "+pieces[i][j].getOrientation().getDirections()+"\n");
					myWriter.write(str.toString());
				}
			}
			myWriter.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * this method copy the grid into the new one
	 * @param filledGrid
	 * @param inputGrid
	 * @param i
	 * @param j
	 * @return
	 */
	public static int[] copyGrid(Grid filledGrid, Grid inputGrid, int i, int j) {
		Piece p;
		int hmax = inputGrid.getHeight();
		int wmax = inputGrid.getWidth();

		if (inputGrid.getHeight() != filledGrid.getHeight())
			hmax = filledGrid.getHeight() + i; // we must adjust hmax to have the height of the original grid
		if (inputGrid.getWidth() != filledGrid.getWidth())
			wmax = filledGrid.getWidth() + j;

		int tmpi = 0;// temporary variable to stock the last index
		int tmpj = 0;

		// DEBUG System.out.println("copyGrid : i =" + i + " & j = " + j);
		// DEBUG System.out.println("hmax = " + hmax + " - wmax = " + wmax);
		for (int x = i; x < hmax; x++) {
			for (int y = j; y < wmax; y++) {
				// DEBUG System.out.println("x = " + x + " - y = " + y);
				p = filledGrid.getPiece(x - i, y - j);
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(p);
				inputGrid.setPiece(x, y, new Piece(x, y, p.getType(), p.getOrientation()));
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(inputGrid.getPiece(x, y));
				tmpj = y;
			}
			tmpi = x;
		}
		//DEBUGSystem.out.println("tmpi =" + tmpi + " & tmpj = " + tmpj);
		return new int[] { tmpi, tmpj };
	}

}