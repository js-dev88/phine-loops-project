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
						//TODO
						inputGrid.setPiece(i, j, new Piece(i, j, 0, 0));
					}

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
				p.setOrientation(rdOrientation.nextInt(2)); //@Julien, on veut pas plutôt un random entre 1 et 2 plutot qu'entre 0 et 1 ici ?
				break;
			case LTYPE:
				p.setOrientation(1);
			default:
				break;
			}

		} else if (line == 0 && column == inputGrid.getWidth() - 1) {
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
				p.setOrientation(3);
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
				default: 
					break;
				}
			}
		}else{
			//TODO
			inputGrid.setPiece(line, column, new Piece(line, column, 0, 0));
		}
		inputGrid.setPiece(line, column, p);
		
	}
	
	
	/**
	 * Affect piece which is on a border column (and not in the corner)
	 * 
	 * @param line
	 * @param column
	 * @param p piece instance (i,j) with no type or orientation
	 *            
	 */
	public static void affectPiece2BorderColumn(int line, int column, Piece p, Grid inputGrid) {
		Random rdOrientation = new Random();
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
				setPossiblePieceType(new int[] { 0,1, 5}, p);
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
