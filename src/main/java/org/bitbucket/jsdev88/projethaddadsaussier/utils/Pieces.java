package org.bitbucket.jsdev88.projethaddadsaussier.utils;

public class Pieces {
	private int posX;
	private int posY;
	private PieceType type;
	private Orientation orientation;
	
	public Pieces(int posX, int posY, PieceType type, Orientation orientation) {
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.orientation = orientation;
	}
	
	public Pieces(int posX, int posY, int typeOrdinal, int orientationOrdinal) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.getValueFromOrdinal(typeOrdinal);
		this.orientation = Orientation.getValueFromOrdinal(orientationOrdinal);
	}
	
	
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public PieceType getType() {
		return type;
	}

	public Orientation getOrientation() {
		return orientation;
	}
	
	/**
	 * Turn the piece 90° on the right
	 */
	public void turn(){
		orientation = orientation.turn();
	}
	
	
	
	
	
}
