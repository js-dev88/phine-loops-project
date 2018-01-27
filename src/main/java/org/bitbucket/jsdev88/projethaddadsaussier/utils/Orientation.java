package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import java.util.HashMap;

/**
 * 
 * Orientation of the piece enum
 * 
 */
public enum Orientation {
	// Each orientation has a specific value and we can easily add a new
	// orientation
	// For each orientation we have an opposite
	NORTH(0) {
		@Override
		public Orientation getOpposedOrientation() {
			return SOUTH;
		}

		@Override
		public int[] getOpposedPieceCoordinates(Piece p) {
			return new int[] { p.getPosY() - 1, p.getPosX() };
		}
	},
	EAST(1) {
		@Override
		public Orientation getOpposedOrientation() {
			return WEST;
		}

		@Override
		public int[] getOpposedPieceCoordinates(Piece p) {
			return new int[] { p.getPosY(), p.getPosX() + 1 };
		}
	},
	SOUTH(2) {
		@Override
		public Orientation getOpposedOrientation() {
			return NORTH;
		}

		@Override
		public int[] getOpposedPieceCoordinates(Piece p) {
			return new int[] { p.getPosY() + 1, p.getPosX() };
		}
	},
	WEST(3) {
		@Override
		public Orientation getOpposedOrientation() {
			return EAST;
		}

		@Override
		public int[] getOpposedPieceCoordinates(Piece p) {
			return new int[] { p.getPosY(), p.getPosX() - 1 };
		}
	};

	private final int value;

	public int getValue() {
		return value;
	}

	private Orientation(int value) {
		this.value = value;
	}

	private static final HashMap<Integer, Orientation> mapValues = new HashMap<>();
	static {
		for (Orientation ori : values()) {
			mapValues.put(ori.getValue(), ori);
		}
	}

	// we can retrieve an orientation from the value
	public static Orientation getOrifromValue(int value) {
		final Orientation ori = (Orientation) mapValues.get(value);
		if (ori != null) {
			return ori;
		}
		throw new IllegalArgumentException("Orientation unknown : " + ori);
	}

	/**
	 * 
	 * @return the opposite orientation
	 */
	public abstract Orientation getOpposedOrientation();

	/**
	 * 
	 * @return the opposite Piece position
	 */
	public abstract int[] getOpposedPieceCoordinates(Piece p);

	/**
	 * Turn the piece of 90° on the right
	 * 
	 * @return the next value of the enum Source
	 *         https://codereview.stackexchange.com/questions/42817/direction-enum-class
	 */
	public Orientation turn90() {
		return values()[(getValue() + 1) % 4];
	}

}
