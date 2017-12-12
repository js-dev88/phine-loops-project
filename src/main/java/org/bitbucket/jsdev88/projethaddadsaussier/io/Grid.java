package org.bitbucket.jsdev88.projethaddadsaussier.io;

import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;


public class Grid {
	private int width;
	private int height;
	private int nbcc;
	private Piece[][] pieces;
	
	public Grid(int width, int height){
		this.width = width;
		this.height = height;
		pieces = new Piece[width][height];
	}
	
	//Consctructor with specified number of connected component 
	public Grid(int width, int height, int nbcc){
		this.width = width;
		this.height = height;
		this.nbcc = nbcc;
		pieces = new Piece[width][height];
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Integer getNbcc() {
		return nbcc;
	}

	public void setNbcc(int nbcc) {
		this.nbcc = nbcc;
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public void setPieces(Piece[][] pieces) {
		this.pieces = pieces;
	}
	
	@Override
	public String toString() {
		String s ="";
		for(int i=0; i< height; i++){
			for(int j=0; j< width; j++){
				s+= displayUnicode.getUnicodeOfPiece(pieces[i][j].getType(),pieces[i][j].getOrientation());
			}
			s+= "\n";
		}
		return s;
	}
	
	
	
}
