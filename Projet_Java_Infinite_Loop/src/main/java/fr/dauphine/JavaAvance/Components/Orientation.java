package fr.dauphine.JavaAvance.Components;

/**
 * 
 * Orientation of the piece enum
 * This Class shows all the possible orientations of the pieces
 * 
 */
public enum Orientation {
	//Each direction will implement those methods
	NORTH (0) {
		@Override
		public Orientation turn90() {
			return EAST;
		}

		@Override
		public int[] getOpposedPieceCoordinates(Piece p) {
			return new int[]{p.getPosY() - 1, p.getPosX()};
		}

		@Override
		public Orientation getOpposedOrientation() {
			return SOUTH;
		}
	}, EAST (1) {

		@Override
		public Orientation turn90() {
			return SOUTH;
		}

		@Override
		public int[] getOpposedPieceCoordinates(Piece p) {
			return new int[]{p.getPosY(), p.getPosX() + 1};
		}

		@Override
		public Orientation getOpposedOrientation() {
			return WEST;
		}
	}, SOUTH (2) {
		@Override
		public Orientation turn90() {
			return WEST;
		}
		@Override
		public int[] getOpposedPieceCoordinates(Piece p) {
			return new int[]{p.getPosY() + 1, p.getPosX()};
		}
		@Override
		public Orientation getOpposedOrientation() {
			return NORTH;
		}
	}, WEST (3) {
		@Override
		public Orientation turn90() {
			return NORTH;
		}
		@Override
		public int[] getOpposedPieceCoordinates(Piece p) {
			return new int[]{p.getPosY(), p.getPosX() - 1};
		}
		@Override
		public Orientation getOpposedOrientation() {
			return EAST;
		}
	};


	//declaring the variables
	private final int directions;

	//constructor
	Orientation(int directions) {
		this.directions = directions;
	}

	public static Orientation getOrientations(int directions) {
		switch (directions) {
		case 0 :
			return NORTH;
		case 1 : 
			return EAST;
		case 2 : 
			return SOUTH;
		case 3 :
			return WEST;
		default :
			return null;
		}
	}

	//getter of directions
	public int getDirections() {
		return this.directions;
	}
	/**
	 * return opposed piece coordinates
	 * @param p piece
	 * @return list int
	 */
	public abstract int[] getOpposedPieceCoordinates(Piece p);

	/**
	 * return ooposed orientation
	 * @return orientation
	 */
	public abstract Orientation getOpposedOrientation();

	/**
	 * turn the piece 90 degrees
	 * @return Orientation
	 */
	public abstract Orientation turn90(); 


}
