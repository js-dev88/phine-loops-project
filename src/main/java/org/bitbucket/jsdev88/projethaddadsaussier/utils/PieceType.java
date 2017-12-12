package org.bitbucket.jsdev88.projethaddadsaussier.utils;


/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	
	VOID(0), ONECONN(1), BAR(2), TTYPE(3), FOURCONN(4), LTYPE(2);

	private final int nbConnectors;


	private PieceType(int nbConnectors) {
		this.nbConnectors = nbConnectors;
	}
	/**
	 * 
	 * @return the piece's number of connectors
	 */
	public int getNbConnectors() {
		return nbConnectors;
	}
	

	/**
	 * Standardize the access of the value from the ordinal of the enum
	 * 
	 * @param ordinal
	 *            value of the orientation
	 * @return the corresponding value
	 */
	public static PieceType getValueFromOrdinal(Integer ordinal) throws IllegalArgumentException {
		if (ordinal < 0 || ordinal > 5) {
			throw new IllegalArgumentException("Ordinal of Orientation is out of bound");
		}
		return values()[ordinal];

	}

}
