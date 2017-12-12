package org.bitbucket.jsdev88.projethaddadsaussier.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;


public class Grid {
	private Integer width;
	private Integer height;
	private Integer nbcc;
	private Piece[][] pieces;
	
	public Grid(Integer width, Integer height){
		this.width = width;
		this.height = height;
		pieces = new Piece[width][height];
	}
	
	//Consctructor with specified number of connected component 
	public Grid(Integer width, Integer height, Integer nbcc){
		this.width = width;
		this.height = height;
		this.nbcc = nbcc;
		pieces = new Piece[width][height];
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getNbcc() {
		return nbcc;
	}

	public void setNbcc(Integer nbcc) {
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
			for(int j=0; j< width; i++){
				s+= displayUnicode.getUnicodeOfPiece(pieces[i][j].getType(),pieces[i][j].getOrientation());
			}
			s+= "\n";
		}
		return s;
	}
	
	

	
	
}
