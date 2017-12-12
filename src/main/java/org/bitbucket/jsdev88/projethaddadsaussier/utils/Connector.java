package org.bitbucket.jsdev88.projethaddadsaussier.utils;

public class Connector {
	private int posX;
	private int posY;

	public Connector(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void setCoordinates(int newPosX, int newPosY){
		posX += newPosX;
		posY += newPosY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	
}
