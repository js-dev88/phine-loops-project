package org.bitbucket.jsdev88.projethaddadsaussier.io;



import java.util.ArrayList;

import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

public class Grid {
	private int width;  //j
	private int height; //i
	private int nbcc = -1;
	private Piece[][] pieces;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		pieces = new Piece[height][width];
	}

	// Constructor with specified number of connected component
	public Grid(int width, int height, int nbcc) {
		this.width = width;
		this.height = height;
		this.nbcc = nbcc;
		pieces = new Piece[height][width];
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
			if (column == this.getWidth() - 1)
				return true;
			return false;
		} else if (line == this.getHeight() - 1) {
			if (column == 0)
				return true;
			if (column == this.getWidth() - 1)
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
		if (line == 0 && column > 0 && column < this.getWidth()-1) {
			return true;
		} else if (line == this.getHeight() - 1 && column > 0 && column < this.getWidth()-1) {
			return true;
		}
		return false;

	}
	/**
	 * Check if a case is member of the first or the last column
	 * 
	 * @param line
	 * @param column
	 * @return true if the case is a corner
	 */
	public boolean isBorderColumn(int line, int column) {
		if (column == 0 && line > 0 && line < this.getHeight()-1) {
			return true;
		} else if (column == this.getWidth() - 1 && line > 0 && line < this.getHeight()-1) {
			return true;
		}
		return false;

	}
	/**
	 * Check if a piece has a neighbour for its connectors for one orientation
	 * @param p piece
	 * @return true if there is a neighbour for all connectors
	 */
	public boolean hasNeighbour(Piece p){
		for(Orientation ori : p.getConnectors()){
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];//i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];//j
			try{
				if(this.getPiece(oppPieceY,  oppPieceX).getType() == PieceType.VOID){
					return false;
				}
				
			}catch(ArrayIndexOutOfBoundsException e){
				return  false;
			}
		}
		return true;
		
	}
	
	public boolean hasFixedNeighbour(Piece p){
		boolean bool = false;
		for(Orientation ori : p.getConnectors()){
			bool =false;
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];//i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];//j
			try{
				Piece neigh = this.getPiece(oppPieceY,  oppPieceX);
				if(neigh.getType() == PieceType.VOID || !neigh.isFixed()){
					return false;
				}
				if(neigh.isFixed()){
					for(Orientation oriOppPiece : neigh.getConnectors()){
						if(ori == oriOppPiece.getOpposedOrientation()){
							bool = true;
						}
					}
					if(!bool){
						return false;
					}
					
				}
			}catch(ArrayIndexOutOfBoundsException e){
				return  false;
			}
		}
		return bool;
	}
	
	public ArrayList<Piece> listOfNeighbours(Piece p){
		ArrayList<Piece> lp = new ArrayList<>();
		for(Orientation ori : p.getConnectors()){
			int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];//i
			int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];//j
			if(this.getPiece(oppPieceY,  oppPieceX).getType() != PieceType.VOID){
					lp.add(this.getPiece(oppPieceY,  oppPieceX));
			}
			
				
		}
		return lp ;
	}
	
	
	
	public int numberOfNeibours(Piece p){
		int X = p.getPosX();
		int Y = p.getPosY();
		int count =0;
		if(Y < this.getHeight()-1 && getPiece(Y+1,  X).getType() != PieceType.VOID) count ++;
		if(X < this.getWidth()-1 && getPiece(Y,  X+1).getType() != PieceType.VOID) count ++;
		if(Y > 0 && getPiece(Y-1, X ).getType() != PieceType.VOID) count ++;
		if(X > 0 && getPiece(Y,  X-1).getType() != PieceType.VOID) count ++;
		return count;
	}
	
	public int numberOfFixedNeibours(Piece p){
		int X = p.getPosX();
		int Y = p.getPosY();
		int count =0;
		
		if(Y < this.getHeight()-1 && getPiece(Y+1,  X).getType() != PieceType.VOID && getPiece(Y+1,  X).isFixed()) count ++;
		if(X < this.getWidth()-1 && getPiece(Y,  X+1).getType() != PieceType.VOID && getPiece(Y,  X+1).isFixed()) count ++;
		if(Y > 0 && getPiece(Y-1, X ).getType() != PieceType.VOID && getPiece(Y-1, X ).isFixed()) count ++;
		if(X > 0 && getPiece(Y,  X-1).getType() != PieceType.VOID && getPiece(Y,  X-1).isFixed()) count ++;
		return count;
	}
	
	/**
	 * Check if all pieces have neighbours even if we don't know the orientation
	 * @param p
	 * @return
	 */
	public boolean allPieceHaveNeighbour(){
		Piece[][] tab = this.getAllPieces();
		for(Piece[] ligne : tab){
			for(Piece p : ligne){
			
				if(p.getType() != PieceType.VOID){
						if(p.getType().getNbConnectors() > numberOfNeibours(p)){
						return false;
					}
				}
				
				
			}
		}
		return true;
		
	}
	
	public Piece getNextPiece(Piece p){
		
			int i = p.getPosY();
			int j = p.getPosX();
			if(j < this.getWidth()-1){
				p =  this.getPiece(i,j+1);
			}else{
				if(i < this.getHeight()-1){
					p  = this.getPiece(i+1, 0);
				}else{
					return null;
				}
				
			}
		
		return p ;
		
	}
	
	/**
	 * Check if a piece is connected
	 * 
	 * @param line
	 * @param column
	 * @return true if a connector of a piece is connected
	 */
	public boolean isConnected(Piece p, Orientation ori){
		int oppPieceY = ori.getOpposedPieceCoordinates(p)[0];//i
		int oppPieceX = ori.getOpposedPieceCoordinates(p)[1];//j
		if(p.getType() == PieceType.VOID)
			return true;
		try{
			for(Orientation oppConnector :this.getPiece(oppPieceY,  oppPieceX).getConnectors()){
				if(oppConnector == ori.getOpposedOrientation()){
				return true;
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
		return false;
	}
	
	/**
	 * Check if a piece is connected
	 * 
	 * @param line
	 * @param column
	 * @return true if a connector of a piece is connected
	 */
	public boolean isTotallyConnected(Piece p){
		if(p.getType() != PieceType.VOID){
			for(Orientation connector : p.getConnectors()){
				if(!this.isConnected(p, connector)){
					return false;
				}
			}
		}
		return true;
	}
	
	public Piece leftNeighbor(Piece p){
		
		if(p.getPosX()> 0){
			if(this.getPiece(p.getPosY(), p.getPosX()-1).getType() != PieceType.VOID){
				return this.getPiece(p.getPosY(), p.getPosX()-1);
			}
		}
		return null;
	}
	public Piece topNeighbor(Piece p){
		
		if(p.getPosY()> 0){
			if(this.getPiece(p.getPosY()-1, p.getPosX()).getType() != PieceType.VOID){
				return this.getPiece(p.getPosY()-1, p.getPosX());
			}
		}
		return null;
	}
	public Piece rightNeighbor(Piece p){
		
		if(p.getPosX()< this.getWidth()-1){
			if(this.getPiece(p.getPosY(), p.getPosX()+1).getType() != PieceType.VOID){
				return this.getPiece(p.getPosY(), p.getPosX()+1);
			}
		}
		return null;
	}
	
	public Piece bottomNeighbor(Piece p){
		
		if(p.getPosY()< this.getHeight()-1){
			if(this.getPiece(p.getPosY()+1, p.getPosX()).getType() != PieceType.VOID){
				return this.getPiece(p.getPosY()+1, p.getPosX());
			}
		}
		return null;
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
