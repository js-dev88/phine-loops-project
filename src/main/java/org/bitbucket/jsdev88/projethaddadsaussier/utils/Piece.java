package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import java.util.LinkedList;

public class Piece {
	private int posX;//j
	private int posY;//i
	private PieceType type;
	private Orientation orientation;
	private LinkedList<Orientation> connectors;

	public Piece(int posY, int posX) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.VOID;
		this.orientation = type.getOrientation(Orientation.NORTH);
		this.connectors = type.setConnectorsList(Orientation.NORTH);

	}

	public Piece(int posY, int posX, PieceType type, Orientation orientation) {
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.orientation = type.getOrientation(orientation);
		this.connectors = type.setConnectorsList(orientation);
	}

	public Piece(int posY, int posX, int typeValue, int orientationValue) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.getTypefromValue(typeValue);
		this.orientation = type.getOrientation(Orientation.getOrifromValue(orientationValue));
		this.connectors = type.setConnectorsList(Orientation.getOrifromValue(orientationValue));
	}

	public int getPosX() { //get j
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() { //get i
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public PieceType getType() {
		return type;
	}

	public void setType(PieceType type) {
		this.type = type;
	}

	public void setOrientation(int orientationValue) {
		this.orientation = type.getOrientation(Orientation.getOrifromValue(orientationValue));
		this.connectors = type.setConnectorsList(this.orientation);
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public LinkedList<Orientation> getConnectors() {
		return connectors;
	}


	public boolean hasTopConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.NORTH) {
				return true;
			}
		}
		return false;
	}

	public boolean hasRightConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.EAST) {
				return true;
			}
		}
		return false;
	}

	public boolean hasBottomConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.SOUTH) {
				return true;
			}
		}
		return false;
	}

	public boolean hasLeftConnector() {
		for (Orientation ori : this.getConnectors()) {
			if (ori == Orientation.WEST) {
				return true;
			}
		}
		return false;
	}
	
	
	
	

	/**
	 * Turn the piece 90° on the right and redefine the connectors's position
	 */
	public void turn() {
		this.orientation = type.getOrientation(orientation.turn90());
		this.connectors = type.setConnectorsList(orientation);
	}

	@Override
	public String toString() {
		String s = "["+this.getPosX()+", "+this.getPosY()+"] "+ this.getType()+" ";
		for (Orientation ori : this.getConnectors()) {
			s += " " + ori.toString();
		}
		return s;
	}
	
	
	public static void main(String[] args) {
		Piece p = new Piece (0,0);
		p.setType(PieceType.LTYPE);
		p.setOrientation(2);
		System.out.println(p.toString());
		System.out.println(p.hasBottomConnector());
	}
}


