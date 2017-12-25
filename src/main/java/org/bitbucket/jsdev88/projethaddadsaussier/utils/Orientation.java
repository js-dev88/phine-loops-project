package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import java.util.HashMap;


/**
 * 
 * Orientation of the piece enum
 * 
 */
public enum Orientation {
	//Each orientation has a specific value and we can easily add a new orientation
	NORTH(0),EAST(1),SOUTH(2),WEST(3);
	
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
		//we can retrieve an orientation from the value
		public static Orientation getOrifromValue(int value) {
			final Orientation ori = (Orientation) mapValues.get(value);
			if (ori != null) {
				return ori;
			}
			throw new IllegalArgumentException("Orientation unknown : " + ori);
		}
	 
	/**
	 * Turn the piece of 90° on the right
	 * @return the next value of the enum
	 * Source https://codereview.stackexchange.com/questions/42817/direction-enum-class
	 */
	 public Orientation turn90() {
	        return values()[(getValue() + 1) % 4];
	 }
	 
	
}
