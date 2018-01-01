package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.*;

import java.util.Random;


import org.bitbucket.jsdev88.projethaddadsaussier.io.GUITEST;
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
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void generateLevel(String fileName, Grid inputGrid) throws IOException {
		// Generate a solution
		filledGrid = generateSolution(inputGrid);
		/* DEBUG */
		//writeGridOnFile("Solution.txt", inputGrid);
		//GUITEST.startGUITEST(filledGrid);
		//System.out.println(filledGrid.toString());
		shuffle(filledGrid);
		// Then we write the level on a file
		writeGridOnFile(fileName, inputGrid);
		//GUITEST.startGUITEST(filledGrid);
		//System.out.println(filledGrid.toString());

	}
	
	/**
	 * Write a  normalized file at the root of the jar
	 * @param fileName from the command line
	 * @param inputGrid
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void writeGridOnFile(String fileName, Grid inputGrid) throws IOException{
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
			writer.write(String.valueOf(inputGrid.getWidth()));
			writer.write(System.lineSeparator());
			writer.write(String.valueOf(inputGrid.getHeight()));
			writer.write(System.lineSeparator());
			for (int i = 0; i < inputGrid.getHeight(); i++) {
				for (int j = 0; j < inputGrid.getWidth(); j++) {
					writer.write(inputGrid.getPiece(i, j).getType().ordinal() + " "
							+ inputGrid.getPiece(i, j).getOrientation().ordinal());
					writer.write(System.lineSeparator());
				}
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error when encoding the file");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
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
			for (int i = 0; i < inputGrid.getHeight(); i++) {
				for (int j = 0; j < inputGrid.getWidth(); j++) {
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
	 * @param p piece instance (i,j) there is no type or orientation
	 */
	public static void affectPiece2Corner(int line, int column, Piece p, Grid inputGrid) {
		Random rdOrientation = new Random();

		if (line == 0 && column == 0) {
			//Avoid pieces with NORTH & EST Orientation 
			setPossiblePieceType(new PieceType[] {PieceType.VOID, PieceType.ONECONN, PieceType.LTYPE }, p);
			switch (p.getType()) {
			case ONECONN:
				p.setOrientation(rdOrientation.nextInt(2)+1); 
				break;
			case LTYPE:
				p.setOrientation(1);
			default:
				break;
			}

		} else if (line == 0 && column == inputGrid.getWidth() - 1) {
			//If the piece of the penultimate column has a right connector, we only choose a piece with WEST possible orientation 
			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				setPossiblePieceType(new PieceType[] {PieceType.ONECONN, PieceType.LTYPE}, p);
				switch (p.getType()) {
				case ONECONN: p.setOrientation(3);
					break;
				case LTYPE : p.setOrientation(2);
				default:
					break;
				}
			} else {
				setPossiblePieceType(new PieceType[] {PieceType.VOID, PieceType.ONECONN }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(2);
					break;
				default:
					break;
				}
			}

		}else if (line == inputGrid.getHeight() - 1 && column == 0) {
			//check if the upper case has a south connector and adjust
			if (inputGrid.getPiece(line-1, column).hasBottomConnector()) {
				setPossiblePieceType(new PieceType[] {PieceType.ONECONN, PieceType.LTYPE}, p);
				switch (p.getType()) {
				case ONECONN: p.setOrientation(0);
					break;
				case LTYPE : p.setOrientation(0);
				default:
					break;
				}
			} else {
				setPossiblePieceType(new PieceType[] {PieceType.VOID, PieceType.ONECONN }, p);
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
		if(line == 0 && column > 0 && column < inputGrid.getWidth()-1){
			
			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				setPossiblePieceType(new PieceType[] { PieceType.ONECONN, PieceType.BAR, PieceType.TTYPE,PieceType.LTYPE }, p);
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
				setPossiblePieceType(new PieceType[] {PieceType.VOID, PieceType.ONECONN, PieceType.LTYPE}, p);
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
				if(inputGrid.getPiece(line-1, column).hasBottomConnector()){
					setPossiblePieceType(new PieceType[] {PieceType.TTYPE, PieceType.LTYPE}, p);
					switch (p.getType()) {
					case LTYPE : p.setOrientation(3);
						break;
					default: p.setOrientation(0);
						break;
					}
				}else{
					setPossiblePieceType(new PieceType[] {PieceType.ONECONN, PieceType.BAR}, p);
					switch (p.getType()) {
					case ONECONN: p.setOrientation(3);
						break;
					default: p.setOrientation(1);
						break;
					}
				}
			}else if(inputGrid.getPiece(line-1, column).hasBottomConnector()){
				setPossiblePieceType(new PieceType[] {PieceType.ONECONN,PieceType.LTYPE}, p);
				p.setOrientation(0);
			}else{
				setPossiblePieceType(new PieceType[] {PieceType.VOID, PieceType.ONECONN}, p);
				switch (p.getType()) {
				case ONECONN : p.setOrientation(1);
					break;
				default: p.setOrientation(0);
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
		if(column == 0 && line > 0 && line < inputGrid.getHeight()-1){
			if (inputGrid.getPiece(line -1, column).hasBottomConnector()) {
				setPossiblePieceType(new PieceType[] {PieceType.ONECONN, PieceType.BAR, PieceType.TTYPE,PieceType.LTYPE}, p);
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
				setPossiblePieceType(new PieceType[] {PieceType.VOID, PieceType.ONECONN, PieceType.LTYPE}, p);
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
				if(inputGrid.getPiece(line, column-1).hasRightConnector()){
					setPossiblePieceType(new PieceType[] {PieceType.TTYPE,PieceType.LTYPE}, p);
					p.setOrientation(3);
				}else{
					setPossiblePieceType(new PieceType[] {PieceType.ONECONN, PieceType.BAR}, p);
					p.setOrientation(0);
				}
			}else if(inputGrid.getPiece(line, column-1).hasRightConnector()){
				setPossiblePieceType(new PieceType[] {PieceType.ONECONN, PieceType.LTYPE}, p);
				switch (p.getType()) {
				case ONECONN:p.setOrientation(3);
					break;
				default: p.setOrientation(2);
					break;
				}
			}else{
				setPossiblePieceType(new PieceType[] {PieceType.VOID, PieceType.ONECONN}, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(2);
					break;
				default: p.setOrientation(0);
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
				setPossiblePieceType(new PieceType[] {PieceType.TTYPE, PieceType.FOURCONN, PieceType.LTYPE}, p);
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
				setPossiblePieceType(new PieceType[] {PieceType.ONECONN, PieceType.BAR, PieceType.TTYPE,PieceType.LTYPE}, p);
				switch (p.getType()) {
				case TTYPE : p.setOrientation(1);
					break;
				default: p.setOrientation(0);
					break;
				}
			}
		}else if(inputGrid.getPiece(line, column-1).hasRightConnector()){
			setPossiblePieceType(new PieceType[] {PieceType.ONECONN, PieceType.BAR, PieceType.TTYPE,PieceType.LTYPE}, p);
			switch (p.getType()) {
			case ONECONN : p.setOrientation(3);
				break;
			case BAR : p.setOrientation(1);
				break;
			default: p.setOrientation(2);
				break;
			}
		}else{
			setPossiblePieceType(new PieceType[] {PieceType.VOID, PieceType.ONECONN, PieceType.LTYPE}, p);
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
	
	/**
	 * Choose randomly a possible piece type and set the type to the piece
	 * @param PossibleType
	 * @param p
	 */
	public static void setPossiblePieceType(PieceType[] PossibleType, Piece p) {
		Random rdType = new Random();
		int type = rdType.nextInt(PossibleType.length);
		p.setType(PossibleType[type]);
	}
	
	/**
	 * Shuffles the grid to build the level
	 * @param grid
	 * @return
	 */
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
			generateLevel("NotSolution.txt", new Grid(25,25));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error when encoding the file");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Can't generate the file");
		}
		
	}
}
