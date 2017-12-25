package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.*;

import java.util.Random;

import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

public class Generator {

	private static Grid filledGrid;

	/**
	 * @param output
	 *            file name
	 * @throws IOException
	 *             - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 */
	public static void generateLevel(String fileName, Grid inputGrid) throws IOException {
		// Generate a solution
		filledGrid = generateSolution(inputGrid);
		/* DEBUG */
		System.out.println(filledGrid.toString());
		shuffle(filledGrid);
		// Then we write the level on a file

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
			writer.write(String.valueOf(filledGrid.getWidth()));
			writer.write(System.lineSeparator());
			writer.write(String.valueOf(filledGrid.getHeight()));
			writer.write(System.lineSeparator());
			for (int i = 0; i < filledGrid.getWidth(); i++) {
				for (int j = 0; j < filledGrid.getHeight(); j++) {
					writer.write(filledGrid.getPiece(i, j).getType().ordinal() + " "
							+ filledGrid.getPiece(i, j).getOrientation().ordinal());
					writer.write(System.lineSeparator());
				}
			}
		}

		/* DEBUG */
		System.out.println(filledGrid.toString());

	}

	/**
	 * Sokution generator
	 * 
	 * @param grid
	 * @return a shuffled grid
	 */
	public static Grid generateSolution(Grid inputGrid) {
		if (inputGrid.getNbcc() == -1) {// Generate a solution with a random
										// number of connected component
			for (int i = 0; i < inputGrid.getWidth(); i++) {
				for (int j = 0; j < inputGrid.getHeight(); j++) {
					// check corners and use the tab of possible pieces
					if (inputGrid.isCorner(i, j)) {
						affectPiece2Corner(i, j, new Piece(i, j), inputGrid);
					} else if (inputGrid.isBorderLine(i, j)) {
						affectPiece2BorderLine(i, j, new Piece(i, j), inputGrid);
					} else if (inputGrid.isBorderColumn(i, j)){
						affectPiece2BorderColumn(i, j, new Piece(i, j), inputGrid);
					} else {
						affectCentralPieces(i, j, new Piece(i, j), inputGrid);
					}
					/* DEBUG */
					System.out.println("type :" +inputGrid.getPiece(i, j).getType()+"conc :"+inputGrid.getPiece(i, j) +"\n");

				}
			}
		} else {// Generate a solution with a specific number of connected
				// component

		}

		return inputGrid;

	}

	/**
	 * Affect a corner piece with restrictions
	 * 
	 * @param line
	 * @param column
	 * @param p
	 *            piece instance (i,j) there is no type or orientation
	 */
	public static void affectPiece2Corner(int line, int column, Piece p, Grid inputGrid) {
		Random rdOrientation = new Random();

		if (line == 0 && column == 0) {
			//Avoid pieces with NORTH & EST Orientation 
			setPossiblePieceType(new int[] { 0, 1, 5 }, p);
			switch (p.getType()) {
			case ONECONN:
				p.setOrientation(rdOrientation.nextInt(2)+1); //well done :) ?
				break;
			case LTYPE:
				p.setOrientation(1);
			default:
				break;
			}

		} else if (line == 0 && column == inputGrid.getHeight() - 1) {
			//If the piece of the penultimate column has a right connector, we only choose a piece with WEST possible orientation 
			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				setPossiblePieceType(new int[] {1, 5}, p);
				switch (p.getType()) {
				case ONECONN: p.setOrientation(3);
					break;
				case LTYPE : p.setOrientation(2);
				default:
					break;
				}
			} else {
				setPossiblePieceType(new int[] { 0, 1 }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(2);
					break;
				default:
					break;
				}
			}

		}else if (line == inputGrid.getWidth() - 1 && column == 0) {
			//check if the upper case has a south connector and adjust
			if (inputGrid.getPiece(line-1, column).hasBottomConnector()) {
				setPossiblePieceType(new int[] {1, 5}, p);
				switch (p.getType()) {
				case ONECONN: p.setOrientation(0);
					break;
				case LTYPE : p.setOrientation(0);
				default:
					break;
				}
			} else {
				setPossiblePieceType(new int[] { 0, 1 }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(1);
					break;
				default:
					break;
				}
			}
		}else{
			//bottom right case must adjust to the upper case and the left case
			if (inputGrid.getPiece(line-1, column).hasBottomConnector()) {
				if(inputGrid.getPiece(line, column-1).hasRightConnector()){
					p.setType(PieceType.LTYPE);
					p.setOrientation(3);
				}else{
					p.setType(PieceType.ONECONN);
					p.setOrientation(0);
				}
			}else if(inputGrid.getPiece(line, column-1).hasRightConnector()){
				p.setType(PieceType.ONECONN);
				p.setOrientation(3);
			}else{
				p.setType(PieceType.VOID);
				p.setOrientation(0);
			}
		}
		inputGrid.setPiece(line, column, p);

	}

	/**
	 * Affect piece which is on a border line (and not in the corner)
	 * 
	 * @param line
	 * @param column
	 * @param p piece instance (i,j) with no type or orientation
	 *            
	 */
	public static void affectPiece2BorderLine(int line, int column, Piece p, Grid inputGrid) {
		Random rdOrientation = new Random();
		//Case first line
		if(line == 0 && column > 0 && column < inputGrid.getHeight()-1){
			
			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				setPossiblePieceType(new int[] { 1, 2, 3, 5 }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(3);
					break;
				case BAR : p.setOrientation(1);
					break;
				case LTYPE: p.setOrientation(2);
					break;
				default: p.setOrientation(2);
					break;
				}
			}else{
				setPossiblePieceType(new int[] { 0,1, 5}, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(rdOrientation.nextInt(2) + 1);
					break;
				case LTYPE: p.setOrientation(1);
					break;
				default: p.setOrientation(0);
					break;
				}
			}
		// Case last line
		}else{
			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				setPossiblePieceType(new int[] { 1, 2, 3, 5 }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(3);
					break;
				case BAR : p.setOrientation(1);
					break;
				case LTYPE: p.setOrientation(3);
					break;
				default: p.setOrientation(0);
					break;
				}
			}else{
				setPossiblePieceType(new int[] { 0,1, 5}, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(rdOrientation.nextInt(2));
					break;
				case LTYPE: p.setOrientation(0);
					break;
				default: 
					break;
				}
			}
		}
		inputGrid.setPiece(line, column, p);
		
	}
	
	
	/**
	 * Affect piece which is on a border column (and not in the corner)
	 * 
	 * @param line
	 * @param column
	 * @param p piece instance (i,j) with no type or orientation
	 * @param grid
	 *            
	 */
	public static void affectPiece2BorderColumn(int line, int column, Piece p, Grid inputGrid) {
		Random rdOrientation = new Random();
		//Case first column
		if(column == 0 && line > 0 && line < inputGrid.getWidth()-1){
			if (inputGrid.getPiece(line -1, column).hasBottomConnector()) {
				setPossiblePieceType(new int[] { 1, 2, 3, 5 }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(0);
					break;
				case BAR : p.setOrientation(0);
					break;
				case LTYPE: p.setOrientation(0);
					break;
				default: p.setOrientation(1);
					break;
				}
			}else{
				setPossiblePieceType(new int[] { 0,1, 5}, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(rdOrientation.nextInt(2) + 1);
					break;
				case LTYPE: p.setOrientation(1);
					break;
				default: 
					break;
				}
			}
		//Case last column
		}else{
			if (inputGrid.getPiece(line -1, column).hasBottomConnector()) {
				setPossiblePieceType(new int[] { 1, 2, 3, 5 }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(0);
					break;
				case BAR : p.setOrientation(0);
					break;
				case LTYPE: p.setOrientation(3);
					break;
				default: p.setOrientation(3);
					break;
				}
			}else{
				setPossiblePieceType(new int[] { 0,1,5}, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(rdOrientation.nextInt(2) + 2);
					break;
				case LTYPE: p.setOrientation(2);
					break;
				default: 
					break;
				}
			}
		}
		inputGrid.setPiece(line, column, p);
		
	}
	
	/**
	 * Affect a central piece : must respect the connectors of the upper and the left piece
	 * @param line
	 * @param column
	 * @param p p piece instance (i,j) with no type or orientation
	 * @param inputGrid
	 */
	public static void affectCentralPieces(int line, int column, Piece p, Grid inputGrid){
		Random rdOrientation = new Random();
		if (inputGrid.getPiece(line-1, column).hasBottomConnector()) {
			if(inputGrid.getPiece(line, column-1).hasRightConnector()){
				setPossiblePieceType(new int[] {3, 4, 5}, p);
				switch (p.getType()) {
				case TTYPE : if(rdOrientation.nextInt(2) == 0)p.setOrientation(0);
							 else p.setOrientation(3);
					break;
				case LTYPE : p.setOrientation(3);
					break;
				default: p.setOrientation(0);
					break;
				}
			}else{
				setPossiblePieceType(new int[] {1, 2, 3, 5}, p);
				switch (p.getType()) {
				case TTYPE : p.setOrientation(1);
					break;
				default: p.setOrientation(0);
					break;
				}
			}
		}else if(inputGrid.getPiece(line, column-1).hasRightConnector()){
			setPossiblePieceType(new int[] {1, 2, 3, 5}, p);
			switch (p.getType()) {
			case ONECONN : p.setOrientation(3);
				break;
			case BAR : p.setOrientation(1);
				break;
			default: p.setOrientation(2);
				break;
			}
		}else{
			setPossiblePieceType(new int[] {0, 1, 5}, p);
			switch (p.getType()) {
			case ONECONN : p.setOrientation(rdOrientation.nextInt(2)+1);
				break;
			case LTYPE : p.setOrientation(1);
				break;
			default: p.setOrientation(0);
				break;
			}
		}
		inputGrid.setPiece(line, column, p);
	}
	
	
	public static void setPossiblePieceType(int[] cornerPossibleType, Piece p) {
		Random rdType = new Random();
		int type = rdType.nextInt(cornerPossibleType.length);
		p.setType(PieceType.getValueFromOrdinal(cornerPossibleType[type]));
	}

	public static Grid shuffle(Grid grid) {
		Random r = new Random();
		for (Piece[] i : grid.getAllPieces()) {
			for (Piece j : i) {
				j.setOrientation(r.nextInt(3));
			}
		}
		return grid;
	}

	public static void main(String[] args) {
		try {
			generateLevel("txt.txt", new Grid(8, 8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
