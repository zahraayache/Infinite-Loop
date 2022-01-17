package fr.dauphine.JavaAvance.GUI;

import java.util.ArrayList;
import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;

/**
 * 
 * @author Zahra , Maria , katia
 *
 */
public class Grid {
	private int width; // j
	private int height; // i
	private int nbcc = 0; //number of connected connectors initially we have none connected
	private Piece[][] pieces;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		pieces = new Piece[height][width];
	}

	//Constructor
	public Grid(Grid grid) { //passing a grid as a parameter
		this.width = grid.width;
		this.height = grid.height;
		pieces = new Piece[height][width];
		for (int i = 0; i < grid.pieces.length; i++) {
			for (int j = 0; j < grid.pieces[i].length; j++) {
				Piece piece = grid.pieces[i][j];
				pieces[i][j] = new Piece(piece.getPosX(), piece.getPosY(), piece.getType(), piece.getOrientation());
			}
		}
		this.nbcc = grid.nbcc;
	}

	// Constructor
	public Grid(int width, int height, int nbcc) { //passing width, height and number of connected component
		this.width = width;
		this.height = height;
		this.nbcc = nbcc;
		pieces = new Piece[height][width];
	}

	//getters and setters
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Integer getNbcc() {
		return nbcc;
	}

	public void setNbcc(int nbcc) {
		this.nbcc = nbcc;
	}

	/**
	 * this method will return the Piece that we are searching for on that line and column
	 * @param line
	 * @param column
	 * @return piece
	 */
	public Piece getPiece(int line, int column) {
		return this.pieces[line][column];
	}

	/**
	 * this method will set a specific piece giving in the parameters in the line and column indicated
	 * @param line
	 * @param column
	 * @param piece
	 */
	public void setPiece(int line, int column, Piece piece) {
		this.pieces[line][column] = piece;
	}

	public Piece[][] getAllPieces() {
		return pieces;
	}

	/**
	 * 
	 * @param line
	 * @param column
	 * @return boolean true if the case is a corner and false otherwise
	 */
	public boolean isCorner(int line, int column) {
		if (line == 0) {
			if (column == 0)
				return true;
			if (column == this.getWidth() - 1)
				return true;
			return false;
		} else if (line == this.getHeight() - 1) {
			if (column == 0)
				return true;
			if (column == this.getWidth() - 1)
				return true;
			return false;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param line
	 * @param column
	 * @return true if a piece is member of the first or the last line
	 */
	public boolean isBorderLine(int line, int column) {
		if (line == 0 && column > 0 && column < this.getWidth() - 1) {
			return true;
		} else if (line == this.getHeight() - 1 && column > 0 && column < this.getWidth() - 1) {
			return true;
		}
		return false;

	}

	/**
	 * 
	 * @param line
	 * @param column
	 * @return true if a piece is member of the first or the last column
	 */
	public boolean isBorderColumn(int line, int column) {
		if (column == 0 && line > 0 && line < this.getHeight() - 1) {
			return true;
		} else if (column == this.getWidth() - 1 && line > 0 && line < this.getHeight() - 1) {
			return true;
		}
		return false;

	}

	/**
	 * 
	 * @param piece
	 * @return boolean true if the piece has neighbors to connect to, false otherwise
	 */
	public boolean hasNeighborsToConnectTo(Piece p) {
		for (Orientation ori : p.getConnectors()) {
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];// i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];// j
			try {
				if (this.getPiece(oppPieceY, oppPieceX).getType() == PieceType.VOID) {
					return false;
				}

			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return true;

	}

	/**
	 * 
	 * @param piece
	 * @return boolean true if the piece has already fixed neighbors to connect to
	 */
	public boolean hasFixedNeighbors(Piece p) {
		boolean bool = false;
		for (Orientation ori : p.getConnectors()) {
			bool = false;
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];// i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];// j
			try {
				Piece neigh = this.getPiece(oppPieceY, oppPieceX);
				if (neigh.getType() == PieceType.VOID || !neigh.isFixed()) {
					return false;
				}
				if (neigh.isFixed()) {
					for (Orientation oriOppPiece : neigh.getConnectors()) {
						if (ori == oriOppPiece.getOpposedOrientation()) {
							bool = true;
						}
					}
					if (!bool) {
						return false;
					}

				}
			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return bool;
	}

	/**
	 * 
	 * @param piece
	 * @return boolean true if the piece has at least one fixed neighbor to connect to
	 */
	public boolean hasAtLeast1FixedNeighbour(Piece p) {
		for (Orientation oriantation : p.getConnectors()) {
			int oppPieceY = oriantation.getOpposedPieceCoordinates(p)[0];// i
			int oppPieceX = oriantation.getOpposedPieceCoordinates(p)[1];// j
			try { 
				Piece oppositePiece = this.getPiece(oppPieceY, oppPieceX);
				if (oppositePiece.isFixed()) {
					for (Orientation oriOppPiece : oppositePiece.getConnectors()) {
						if (oriantation == oriOppPiece.getOpposedOrientation()) {
							return true;
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param piece
	 * @return the list of all the neighbors of the piece sent in parameters
	 */
	public ArrayList<Piece> listOfNeighbors(Piece p) {
		ArrayList<Piece> allNeighbors = new ArrayList<>();
		for (Orientation ori : p.getConnectors()) {
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];// i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];// j

			if (oppPieceY >= 0 && oppPieceY < this.getHeight() && oppPieceX >= 0 && oppPieceX < this.width) {
				if (this.getPiece(oppPieceY, oppPieceX).getType() != PieceType.VOID) {
					allNeighbors.add(this.getPiece(oppPieceY, oppPieceX));
				}
			}

		}
		return allNeighbors;
	}

	/**
	 * @param p
	 * @return this function returns the number of neighbors
	 */
	public int numberOfNeighbors(Piece p) {
		ArrayList<Piece> allNeighbors = listOfNeighbors(p);
		return allNeighbors.size();
	}

	/**
	 * @param p
	 * @return this function returns the number of fixed neighbors
	 */
	public int numberOfFixedNeibors(Piece p) {
		int X = p.getPosX();
		int Y = p.getPosY();
		int count = 0;
		//the max number of fixed neighbors to a piece is 4
		// so we have to check the 4 directions of a piece.
		if (Y < this.getHeight() - 1 && getPiece(Y + 1, X).getType() != PieceType.VOID && getPiece(Y + 1, X).isFixed())
			count++;
		if (X < this.getWidth() - 1 && getPiece(Y, X + 1).getType() != PieceType.VOID && getPiece(Y, X + 1).isFixed())
			count++;
		if (Y > 0 && getPiece(Y - 1, X).getType() != PieceType.VOID && getPiece(Y - 1, X).isFixed())
			count++;
		if (X > 0 && getPiece(Y, X - 1).getType() != PieceType.VOID && getPiece(Y, X - 1).isFixed())
			count++;
		return count;
	}

	/**
	 * Check if each piece has a neighbor
	 * @param
	 * @return false if a piece has no neighbors
	 */
	public boolean allPieceHaveANeighbor() {

		for (Piece[] ligne : this.getAllPieces()) {
			for (Piece p : ligne) {

				if (p.getType() != PieceType.VOID) {
					if (p.getType().getNumberOfConnectors() > numberOfNeighbors(p)) {
						return false;
					}
				}

			}
		}
		return true;

	}

	/**
	 * 
	 * @param piece
	 * @return the next piece after p, and null if there is no more
	 */
	public Piece getNextPiece(Piece p) {
		int i = p.getPosY();
		int j = p.getPosX();
		if (j < this.getWidth() - 1) {
			p = this.getPiece(i, j + 1);
		} else {
			if (i < this.getHeight() - 1) {
				p = this.getPiece(i + 1, 0);
			} else {
				return null;
			}

		}
		return p;
	}

	/**
	 * Return the next piece of the current piece right2left and bottom2top
	 * 
	 * @param p
	 *            the current piece
	 * @return the piece or null if p is the last piece
	 */
	public Piece getNextPieceInv(Piece p) {

		int i = p.getPosY();
		int j = p.getPosX();
		if (j > 0) {
			p = this.getPiece(i, j - 1);
		} else {
			if (i > 0) {
				p = this.getPiece(i - 1, this.getWidth()-1);
			} else {
				return null;
			}

		}

		return p;

	}

	/**
	 * 
	 * @param piece
	 * @param orientation
	 * @return true if a piece is connected to another connector
	 */
	public boolean isConnected(Piece piece, Orientation orientation) {
		int oppPieceY = orientation.getOpposedPieceCoordinates(piece)[0];// i
		int oppPieceX = orientation.getOpposedPieceCoordinates(piece)[1];// j
		if (piece.getType() == PieceType.VOID)
			return true;
		try {
			for (Orientation oppConnector : this.getPiece(oppPieceY, oppPieceX).getConnectors()) {
				if (oppConnector == orientation.getOpposedOrientation()) {
					return true;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return false;
	}

	/**
	 * Check if a piece is totally connected
	 * @param p
	 * @return true if all connectors of a piece is connected to other connectors
	 */
	public boolean isTotallyConnected(Piece p) {
		if (p.getType() != PieceType.VOID) {
			for (Orientation orientation : p.getConnectors()) {
				if (!this.isConnected(p, orientation)) {
					return false;
				}
			}
		}
		return true;
	}

	/**

	 * @param line
	 * @param column
	 * @return true if a connector of a piece is connected and it's position is valid
	 */
	public boolean isValidOrientation(int line, int column) {

		Piece tn = this.topNeighbor(this.getPiece(line, column));
		Piece ln = this.leftNeighbor(this.getPiece(line, column));
		Piece rn = this.rightNeighbor(this.getPiece(line, column));
		Piece bn = this.bottomNeighbor(this.getPiece(line, column));

		if (this.getPiece(line, column).getType() != PieceType.VOID) {
			if (line == 0) {
				if (column == 0) {
					if (this.getPiece(line, column).hasLeftConnector())
						return false;
				} else if (column == this.getWidth() - 1) {
					if (this.getPiece(line, column).hasRightConnector())
						return false;
				}
				if (this.getPiece(line, column).hasTopConnector())
					return false;
				if (!this.getPiece(line, column).hasRightConnector() && rn != null && rn.hasLeftConnector())
					return false;
				if (this.getPiece(line, column).hasRightConnector() && rn != null && !rn.hasLeftConnector())
					return false;
				if (!this.getPiece(line, column).hasBottomConnector() && bn != null && bn.hasTopConnector())
					return false;
				if (this.getPiece(line, column).hasBottomConnector() && bn != null && !bn.hasTopConnector())
					return false;

			} else if (line > 0 && line < this.getHeight() - 1) {
				if (column == 0) {
					if (this.getPiece(line, column).hasLeftConnector())
						return false;

				} else if (column == this.getWidth() - 1) {
					if (this.getPiece(line, column).hasRightConnector())
						return false;
				}

				if (!this.getPiece(line, column).hasRightConnector() && rn != null && rn.hasLeftConnector())
					return false;
				if (this.getPiece(line, column).hasRightConnector() && rn != null && !rn.hasLeftConnector())
					return false;
				if (!this.getPiece(line, column).hasBottomConnector() && bn != null && bn.hasTopConnector())
					return false;
				if (this.getPiece(line, column).hasBottomConnector() && bn != null && !bn.hasTopConnector())
					return false;

			} else if (line == this.getHeight() - 1) {
				if (column == 0) {
					if (this.getPiece(line, column).hasLeftConnector())
						return false;
				} else if (column == this.getWidth() - 1) {
					if (this.getPiece(line, column).hasRightConnector())
						return false;
				}
				if (this.getPiece(line, column).hasBottomConnector())
					return false;
				if (!this.getPiece(line, column).hasRightConnector() && rn != null && rn.hasLeftConnector())
					return false;
				if (this.getPiece(line, column).hasRightConnector() && rn != null && !rn.hasLeftConnector())
					return false;

			}
			if (this.getPiece(line, column).hasLeftConnector() && ln == null)
				return false;
			if (this.getPiece(line, column).hasTopConnector() && tn == null)
				return false;
			if (this.getPiece(line, column).hasRightConnector() && rn == null)
				return false;
			if (this.getPiece(line, column).hasBottomConnector() && bn == null)
				return false;
		}

		return true;
	}

	/**
	 * @param piece
	 * @return the left neighbor or null if no neighbor
	 */
	public Piece leftNeighbor(Piece piece) {

		if (piece.getPosX() > 0) {
			if (this.getPiece(piece.getPosY(), piece.getPosX() - 1).getType() != PieceType.VOID) {
				return this.getPiece(piece.getPosY(), piece.getPosX() - 1);
			}
		}
		return null;
	}

	/**

	 * @param p
	 * @return the top neighbor or null if no neighbor
	 */
	public Piece topNeighbor(Piece p) {

		if (p.getPosY() > 0) {
			if (this.getPiece(p.getPosY() - 1, p.getPosX()).getType() != PieceType.VOID) {
				return this.getPiece(p.getPosY() - 1, p.getPosX());
			}
		}
		return null;
	}

	/**
	 * 
	 * @param p
	 * @return the right neighbor or null if no neighbor
	 */
	public Piece rightNeighbor(Piece p) {

		if (p.getPosX() < this.getWidth() - 1) {
			if (this.getPiece(p.getPosY(), p.getPosX() + 1).getType() != PieceType.VOID) {
				return this.getPiece(p.getPosY(), p.getPosX() + 1);
			}
		}
		return null;
	}

	/**
	 * @param p
	 * @return the bottom neighbor or null if no neighbor
	 */
	public Piece bottomNeighbor(Piece p) {

		if (p.getPosY() < this.getHeight() - 1) {
			if (this.getPiece(p.getPosY() + 1, p.getPosX()).getType() != PieceType.VOID) {
				return this.getPiece(p.getPosY() + 1, p.getPosX());
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				s += DisplayUnicode.getUnicodeOfPiece(pieces[i][j].getType(), pieces[i][j].getOrientation());
			}
			s += "\n";
		}
		return s;
	}

}
