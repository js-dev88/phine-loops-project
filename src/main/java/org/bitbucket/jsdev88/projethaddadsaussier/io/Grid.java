package org.bitbucket.jsdev88.projethaddadsaussier.io;



import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;

public class Grid {
	private int width;
	private int height;
	private int nbcc = -1;
	private Piece[][] pieces;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		pieces = new Piece[width][height];
	}

	// Constructor with specified number of connected component
	public Grid(int width, int height, int nbcc) {
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

	public Piece getPiece(int line, int column) {
		return this.pieces[line][column];
	}

	public void setPiece(int line, int column, Piece piece) {
		this.pieces[line][column] = piece;
	}

	public Piece[][] getAllPieces() {
		return pieces;
	}

	/**
	 * Check if a case is a corner
	 * 
	 * @param line
	 * @param column
	 * @return true if the case is a corner
	 */
	public boolean isCorner(int line, int column) {
		if (line == 0) {
			if (column == 0)
				return true;
			if (column == this.getHeight() - 1)
				return true;
			return false;
		} else if (line == this.getWidth() - 1) {
			if (column == 0)
				return true;
			if (column == this.getHeight() - 1)
				return true;
			return false;
		} else {
			return false;
		}
	}
	/**
	 * Check if a case is member of the first or the last line
	 * 
	 * @param line
	 * @param column
	 * @return true if the case is a corner
	 */
	public boolean isBorderLine(int line, int column) {
		if (line == 0 && column > 0 && column < this.getHeight()-1) {
			return true;
		} else if (line == this.getWidth() - 1 && column > 0 && column < this.getHeight()-1) {
			return true;
		}
		return false;

	}
	
	public boolean isBorderColumn(int line, int column) {
		if (column == 0 && line > 0 && line < this.getWidth()-1) {
			return true;
		} else if (column == this.getHeight() - 1 && line > 0 && line < this.getWidth()-1) {
			return true;
		}
		return false;

	}
	
	

	@Override
	public String toString() {
		
		String s = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				s += displayUnicode.getUnicodeOfPiece(pieces[i][j].getType(), pieces[i][j].getOrientation());
			}
			s += "\n";
		}
		return s;
	}

}
