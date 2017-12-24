package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import java.util.LinkedList;

public class Piece {
	private int posX;
	private int posY;
	private PieceType type;
	private Orientation orientation;
	private LinkedList<Orientation> connectors;

	
	public Piece(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.VOID;
		this.orientation = type.getOrientation(Orientation.NORTH);
		this.connectors = type.getConectorsList(Orientation.NORTH);

	}
	public Piece(int posX, int posY, PieceType type, Orientation orientation) {
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		this.orientation = type.getOrientation(orientation);
		this.connectors = type.getConectorsList(orientation);
	}
	
	
	public Piece(int posX, int posY, int typeOrdinal, int orientationOrdinal) {
		this.posX = posX;
		this.posY = posY;
		this.type = PieceType.getValueFromOrdinal(typeOrdinal);
		this.orientation = type.getOrientation(Orientation.getValueFromOrdinal(orientationOrdinal));
		this.connectors = type.getConectorsList(Orientation.getValueFromOrdinal(orientationOrdinal));
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

	public void setPosX(int posX) {
		this.posX = posX;
	}



	public void setPosY(int posY) {
		this.posY = posY;
	}



	public void setType(PieceType type) {
		this.type = type;
	}



	public void setOrientation(int ordinal) {
		this.orientation = type.getOrientation(Orientation.getValueFromOrdinal(ordinal));
		this.connectors = type.getConectorsList(Orientation.getValueFromOrdinal(ordinal));
	}



	public Orientation getOrientation() {
		return orientation;
	}
	
	public LinkedList<Orientation> getConnectors() {
		return connectors;
	}

	public void setConnectors(Orientation orientation) {
		this.connectors = type.getConectorsList(orientation);
	}
	
	
	public  boolean hasTopConnector(){
		for(Orientation ori : this.getConnectors()){
			if(ori == Orientation.NORTH){
				return true;
			}	
		}
		return false;
	}
	public  boolean hasRightConnector(){
		for(Orientation ori : this.getConnectors()){
			if(ori == Orientation.EAST){
				return true;
			}	
		}
		return false;
	}
	
	public  boolean hasBottomConnector(){
		for(Orientation ori : this.getConnectors()){
			if(ori == Orientation.SOUTH){
				return true;
			}	
		}
		return false;
	}
	public  boolean hasLeftConnector(){
		for(Orientation ori : this.getConnectors()){
			if(ori == Orientation.WEST){
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * Turn the piece 90° on the right and redefine the connectors's position
	 */
	public void turn(){
		orientation = orientation.turn();
		connectors = type.getConectorsList(orientation);
	}



	
	
	
	
	
	
	
	
	
	
}
