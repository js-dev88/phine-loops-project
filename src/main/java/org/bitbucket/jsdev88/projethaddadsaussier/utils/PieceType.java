package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	// Each Type has a number of connectors and a specific value
	VOID(0, 0) {
		@Override
		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> conList = new LinkedList<>();
			conList.add(orientation);
			return conList;
		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return Orientation.NORTH;
		}

		@Override
		public ArrayList<Orientation> getListOfPossibleOri() {
			ArrayList<Orientation> posList = new ArrayList<>();
			posList.add(Orientation.NORTH);
			return posList;
		}
	},
	ONECONN(1, 1) {
		@Override
		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> conList = new LinkedList<>();
			conList.add(orientation);
			return conList;

		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}

		@Override
		public ArrayList<Orientation> getListOfPossibleOri() {
			ArrayList<Orientation> posList = new ArrayList<>();
			for (Orientation ori : Orientation.values()) {
				posList.add(ori);
			}
			return posList;
		}
	},
	BAR(2, 2) {
		@Override
		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> conList = new LinkedList<>();
			conList.add(orientation);
			conList.add(Orientation.getOrifromValue((orientation.getValue() + 2) % 4));
			return conList;
		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			if (orientation == Orientation.SOUTH)
				return Orientation.NORTH;
			if (orientation == Orientation.WEST)
				return Orientation.EAST;
			return orientation;
		}

		@Override
		public ArrayList<Orientation> getListOfPossibleOri() {
			ArrayList<Orientation> posList = new ArrayList<>();
			posList.add(Orientation.NORTH);
			posList.add(Orientation.EAST);
			return posList;
		}
	},
	TTYPE(3, 3) {
		@Override
		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> conList = new LinkedList<>();
			conList.add(Orientation.getOrifromValue((orientation.getValue() + 3) % 4));
			conList.add(orientation);
			conList.add(Orientation.getOrifromValue((orientation.getValue() + 1) % 4));
			return conList;

		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}

		@Override
		public ArrayList<Orientation> getListOfPossibleOri() {
			ArrayList<Orientation> posList = new ArrayList<>();
			for (Orientation ori : Orientation.values()) {
				posList.add(ori);
			}
			return posList;
		}
	},
	FOURCONN(4, 4) {
		@Override
		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> conList = new LinkedList<>();
			conList.add(orientation);
			conList.add(Orientation.getOrifromValue((orientation.getValue() + 1) % 4));
			conList.add(Orientation.getOrifromValue((orientation.getValue() + 2) % 4));
			conList.add(Orientation.getOrifromValue((orientation.getValue() + 3) % 4));
			return conList;

		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return Orientation.NORTH;
		}

		@Override
		public ArrayList<Orientation> getListOfPossibleOri() {
			ArrayList<Orientation> posList = new ArrayList<>();
			for (Orientation ori : Orientation.values()) {
				posList.add(ori);
			}
			return posList;
		}
	},
	LTYPE(2, 5) {
		@Override
		public LinkedList<Orientation> setConnectorsList(Orientation orientation) {
			LinkedList<Orientation> conList = new LinkedList<>();
			conList.add(orientation);
			conList.add(Orientation.getOrifromValue((orientation.getValue() + 1) % 4));
			return conList;

		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}

		@Override
		public ArrayList<Orientation> getListOfPossibleOri() {
			ArrayList<Orientation> posList = new ArrayList<>();
			for (Orientation ori : Orientation.values()) {
				posList.add(ori);
			}
			return posList;
		}
	};

	private final int nbConnectors;
	private final int value;

	private PieceType(int nbConnectors, int value) {
		this.nbConnectors = nbConnectors;
		this.value = value;
	}

	/**
	 * 
	 * @return the piece's number of connectors
	 */
	public int getNbConnectors() {
		return nbConnectors;
	}

	public int getValue() {
		return value;
	}

	/**
	 * Set the list of possible connectors from a specific orientatiob
	 * 
	 * @param orientation
	 * @return Linked List of the piece's connectors
	 */
	public abstract LinkedList<Orientation> setConnectorsList(Orientation orientation);

	/**
	 * get the orientation available for the type
	 * 
	 * @param orientation
	 * @return
	 */
	public abstract Orientation getOrientation(Orientation orientation);

	/**
	 * get the list of possible orientation
	 * 
	 * @param orientation
	 * @return a list of possible orientations for the pice type
	 */
	public abstract ArrayList<Orientation> getListOfPossibleOri();

	/**
	 * We can't access to a piece type with the ordinal value
	 * 
	 * @param value
	 * @return the transcodification table
	 */
	private static final HashMap<Integer, PieceType> mapValues = new HashMap<>();
	static {
		for (PieceType pt : values()) {
			mapValues.put(pt.getValue(), pt);
		}
	}

	/**
	 * Retrieves the type from a value
	 * 
	 * @param value
	 * @return
	 */
	public static PieceType getTypefromValue(int value) {
		final PieceType pt = (PieceType) mapValues.get(value);
		if (pt != null) {
			return pt;
		}
		throw new IllegalArgumentException("Type unknown : " + pt);
	}

}
