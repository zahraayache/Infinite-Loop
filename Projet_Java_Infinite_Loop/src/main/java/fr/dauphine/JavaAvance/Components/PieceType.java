package fr.dauphine.JavaAvance.Components;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	//Each type has a number of connectors and a specific value
	VOID(0) { //0 connectors
		public Orientation getOrientation(Orientation orientation) {
			return Orientation.NORTH;
		}

		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			return new LinkedList<>();
		}

		public ArrayList<Orientation> getListOfOrientationByDirections() {
			ArrayList<Orientation> myList = new ArrayList<>();
			myList.add(Orientation.NORTH);
			return myList;
		}
	},
	ONECONN(1) { //1 connector
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}

		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> connectors = new LinkedList<>();
			switch (orientation) {
			case NORTH -> connectors.add(Orientation.NORTH);
			case EAST -> connectors.add(Orientation.EAST);
			case WEST -> connectors.add(Orientation.WEST);
			case SOUTH -> connectors.add(Orientation.SOUTH);
			}
			return connectors;
		}

		public ArrayList<Orientation> getListOfOrientationByDirections() {
			ArrayList<Orientation> myListOfOrientations = new ArrayList<>();
			myListOfOrientations.add(Orientation.NORTH);
			myListOfOrientations.add(Orientation.EAST);
			myListOfOrientations.add(Orientation.SOUTH);
			myListOfOrientations.add(Orientation.WEST);
			return myListOfOrientations;
		}
	},
	BAR(2) { //2 connectors
		public Orientation getOrientation(Orientation orientation) {
			if (orientation == Orientation.NORTH || orientation == Orientation.SOUTH)
				return Orientation.NORTH;
			if (orientation == Orientation.EAST || orientation == Orientation.WEST)
				return Orientation.EAST;
			return null;
		}

		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> connectors = new LinkedList<>();
			switch (orientation) {
			case NORTH, SOUTH -> {
				connectors.add(Orientation.NORTH);
				connectors.add(Orientation.SOUTH);
			}
			case EAST, WEST -> {
				connectors.add(Orientation.EAST);
				connectors.add(Orientation.WEST);
			}
			}
			return connectors;
		}

		public ArrayList<Orientation> getListOfOrientationByDirections() {
			ArrayList<Orientation> myListOfOrientations = new ArrayList<>();
			myListOfOrientations.add(Orientation.NORTH);
			myListOfOrientations.add(Orientation.EAST);
			return myListOfOrientations;
		}
	},
	TTYPE(3) { //3 connectors
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}

		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> connectors = new LinkedList<>();
			switch (orientation) {
			case NORTH -> {
				connectors.add(Orientation.NORTH);
				connectors.add(Orientation.WEST);
				connectors.add(Orientation.EAST);
			}
			case EAST -> {
				connectors.add(Orientation.NORTH);
				connectors.add(Orientation.EAST);
				connectors.add(Orientation.SOUTH);
			}
			case SOUTH -> {
				connectors.add(Orientation.EAST);
				connectors.add(Orientation.SOUTH);
				connectors.add(Orientation.WEST);
			}
			case WEST -> {
				connectors.add(Orientation.NORTH);
				connectors.add(Orientation.WEST);
				connectors.add(Orientation.SOUTH);
			}
			}
			return connectors;
		}

		public ArrayList<Orientation> getListOfOrientationByDirections() {
			ArrayList<Orientation> myListOfOrientations = new ArrayList<>();
			myListOfOrientations.add(Orientation.NORTH);
			myListOfOrientations.add(Orientation.EAST);
			myListOfOrientations.add(Orientation.SOUTH);
			myListOfOrientations.add(Orientation.WEST);
			return myListOfOrientations;
		}
	},
	FOURCONN(4) { //4 connectors
		public Orientation getOrientation(Orientation orientation) {
			return Orientation.NORTH;
		}

		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> connectors = new LinkedList<>();
			connectors.add(Orientation.NORTH);
			connectors.add(Orientation.EAST);
			connectors.add(Orientation.SOUTH);
			connectors.add(Orientation.WEST);
			return connectors;
		}

		public ArrayList<Orientation> getListOfOrientationByDirections() {
			ArrayList<Orientation> myListOfOrientations = new ArrayList<>();
			myListOfOrientations.add(Orientation.NORTH);
			return myListOfOrientations;
		}
	},
	LTYPE(2) { //2 connectors type L
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}

		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> connectors = new LinkedList<>();
			switch (orientation) {
			case NORTH -> {
				connectors.add(Orientation.NORTH);
				connectors.add(Orientation.EAST);
			}
			case EAST -> {
				connectors.add(Orientation.EAST);
				connectors.add(Orientation.SOUTH);
			}
			case SOUTH -> {
				connectors.add(Orientation.SOUTH);
				connectors.add(Orientation.WEST);
			}
			case WEST -> {
				connectors.add(Orientation.WEST);
				connectors.add(Orientation.NORTH);
			}
			}
			return connectors;
		}

		public ArrayList<Orientation> getListOfOrientationByDirections() {
			ArrayList<Orientation> orientations = new ArrayList<>();
			orientations.add(Orientation.NORTH);
			orientations.add(Orientation.EAST);
			orientations.add(Orientation.SOUTH);
			orientations.add(Orientation.WEST);
			return orientations;
		}
	};

	private final int nbConnectors; //this is declared final bcs the pieces don't change their number of connectors during the game

	/**
	 * constructor of PieceType
	 * @param nbConnectors int
	 */
	PieceType(int nbConnectors) {
		this.nbConnectors = nbConnectors;
	}

	/**
	 * return pieceType from Direction
	 * @param direction int
	 * @return PieceType
	 */
	public static PieceType getTypeFromDirection(int direction) {
		return switch (direction) {
		case 0 -> PieceType.VOID;
		case 1 -> PieceType.ONECONN;
		case 2 -> PieceType.BAR;
		case 3 -> PieceType.TTYPE;
		case 4 -> PieceType.FOURCONN;
		case 5 -> PieceType.LTYPE;
		default -> throw new IllegalStateException("Unexpected value: " + direction);
		};
	}

	/**
	 * return number of connectors
	 * @return number of connectors
	 */
	public int getNumberOfConnectors() {
		return this.nbConnectors;
	}

	/**
	 * return orientations of connections
	 * @param orientation Orientation
	 * @return list of Orientations
	 */
	public abstract LinkedList<Orientation> setConnectorsList(Orientation orientation);

	/**
	 * return list of possible orientation
	 * @return list of Orientation
	 */
	public abstract ArrayList<Orientation> getListOfOrientationByDirections();
	/**
	 * return orientation of piece
	 * @param orientation Orientation
	 * @return Orientation
	 */
	public abstract Orientation getOrientation(Orientation orientation);





}
