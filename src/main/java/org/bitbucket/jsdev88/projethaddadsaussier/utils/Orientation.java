package org.bitbucket.jsdev88.projethaddadsaussier.utils;
/**
 * 
 * Orientation of the piece enum
 * 
 */
public enum Orientation {
	NORTH,EAST,SOUTH,WEST;
	 
	/**
	 * Turn the piece of 90° on the right
	 * @return the next value of the enum
	 * Source https://codereview.stackexchange.com/questions/42817/direction-enum-class
	 */
	 public Orientation turn() {
	        return values()[(ordinal() + 1) % 4];
	 }
	 
	 /**
	  * Standardize the access of the value from the ordinal of the enum
	  * @param ordinal value of the orientation
	  * @return the corresponding value
	  */
	 public static Orientation getValueFromOrdinal(Integer ordinal) throws IllegalArgumentException{
		 if( ordinal < 0 || ordinal >= 4){
			 throw new IllegalArgumentException("Ordinal of Orientation is out of bound : "+ordinal);
		 }
		 return values()[ordinal]; 

	 }
}
