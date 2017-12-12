package org.bitbucket.jsdev88.projethaddadsaussier.utils;
/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	VOID,ONECONN,BAR,TTYPE,FOURCONN,LTYPE;
	
		
		 /**
		  * Standardize the access of the value from the ordinal of the enum
		  * @param ordinal value of the orientation
		  * @return the corresponding value
		  */
		 public static PieceType getValueFromOrdinal(Integer ordinal) throws IllegalArgumentException{
			 if( ordinal < 0 || ordinal > 5 ){
				 throw new IllegalArgumentException("Ordinal of Orientation is out of bound");
			 }
			 return values()[ordinal]; 

		 }
	
}
