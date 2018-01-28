package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.*;

import java.util.Random;

import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

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
		// Generate a solution with a specific
		// number of connected component
		if (inputGrid.getNbcc() != -1) {
			int i = 0;
			int j = 0;
			int savei = 0;
			int savej = 0;

			int[] index;
			Grid[] inputGrids = divideGrid(inputGrid);
			for (Grid grid : inputGrids) {
				System.out.println("Grid width : " + grid.getWidth() + " / Grid height : " + grid.getHeight());
				filledGrid = generateSolution(grid);
				System.out.println("FilledGrid width : " + filledGrid.getWidth() + " / FilledGrid height : "
						+ filledGrid.getHeight());
				index = copyGrid(filledGrid, inputGrid, i, j);
				i = (index[0] + 1) % inputGrid.getHeight();
				j = (index[1] + 1) % inputGrid.getWidth();
				if (i == savei) {
					i = (savei + filledGrid.getHeight()) % inputGrid.getWidth();
					savei = i;
				} else if (j == savej) {
					j = (savej + filledGrid.getWidth()) % inputGrid.getWidth();
					savej = j;
				}

				System.out.println("i : " + i + " - j : " + j);
			}

		} else {
			// Generate a level with a random
			// number of connected component
			filledGrid = generateSolution(inputGrid);
			shuffle(filledGrid);
			// Then we write the level on a file
			writeGridOnFile(fileName, filledGrid);

		}

	}

	/**
	 * Write a normalized file at the root of the jar
	 * 
	 * @param fileName
	 *            from the command line
	 * @param inputGrid
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public static void writeGridOnFile(String fileName, Grid inputGrid) throws IOException {

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
	 * Solution generator
	 * 
	 * @param grid
	 * @return a shuffled grid
	 */
	public static Grid generateSolution(Grid inputGrid) {
		for (int i = 0; i < inputGrid.getHeight(); i++) {
			for (int j = 0; j < inputGrid.getWidth(); j++) {
				// check corners and use the tab of possible pieces
				if (inputGrid.isCorner(i, j)) {
					affectPiece2Corner(i, j, new Piece(i, j), inputGrid);
				} else if (inputGrid.isBorderLine(i, j)) {
					affectPiece2BorderLine(i, j, new Piece(i, j), inputGrid);
				} else if (inputGrid.isBorderColumn(i, j)) {
					affectPiece2BorderColumn(i, j, new Piece(i, j), inputGrid);
				} else {
					affectCentralPieces(i, j, new Piece(i, j), inputGrid);
				}

			}
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
			// Avoid pieces with NORTH & EST Orientation
			if (inputGrid.getNbcc() != -1) {
				p.setType(PieceType.LTYPE);
				p.setOrientation(1);
			} else {
				setPossiblePieceType(new PieceType[] { PieceType.VOID, PieceType.ONECONN, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(rdOrientation.nextInt(2) + 1);
					break;
				case LTYPE:
					p.setOrientation(1);
				default:
					break;
				}
			}
		} else if (line == 0 && column == inputGrid.getWidth() - 1) {

			// If the piece of the penultimate column has a right connector, we
			// only choose a piece with WEST possible orientation
			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				setPossiblePieceType(new PieceType[] { PieceType.ONECONN, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(3);
					break;
				case LTYPE:
					p.setOrientation(2);
				default:
					break;
				}
			} else {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.VOID }, p);
				else
					setPossiblePieceType(new PieceType[] { PieceType.VOID, PieceType.ONECONN }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(2);
					break;
				default:
					break;
				}
			}

		} else if (line == inputGrid.getHeight() - 1 && column == 0) {
			// check if the upper case has a south connector and adjust
			if (inputGrid.getPiece(line - 1, column).hasBottomConnector()) {
				setPossiblePieceType(new PieceType[] { PieceType.ONECONN, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(0);
					break;
				case LTYPE:
					p.setOrientation(0);
				default:
					break;
				}
			} else {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.VOID }, p);
				else
					setPossiblePieceType(new PieceType[] { PieceType.VOID, PieceType.ONECONN }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(1);
					break;
				default:
					break;
				}
			}
		} else {
			// bottom right case must adjust to the upper case and the left case
			if (inputGrid.getPiece(line - 1, column).hasBottomConnector()) {
				if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
					p.setType(PieceType.LTYPE);
					p.setOrientation(3);
				} else {
					p.setType(PieceType.ONECONN);
					p.setOrientation(0);
				}
			} else if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				p.setType(PieceType.ONECONN);
				p.setOrientation(3);
			} else {
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
	 * @param p
	 *            piece instance (i,j) with no type or orientation
	 * 
	 */
	public static void affectPiece2BorderLine(int line, int column, Piece p, Grid inputGrid) {
		Random rdOrientation = new Random();
		// Case first line
		if (line == 0 && column > 0 && column < inputGrid.getWidth() - 1) {

			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.BAR, PieceType.TTYPE }, p);
				else
					setPossiblePieceType(
							new PieceType[] { PieceType.ONECONN, PieceType.BAR, PieceType.TTYPE, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(3);
					break;
				case BAR:
					p.setOrientation(1);
					break;
				case LTYPE:
					p.setOrientation(2);
					break;
				default:
					p.setOrientation(2);
					break;
				}
			} else {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.VOID }, p);
				else
					setPossiblePieceType(new PieceType[] { PieceType.VOID, PieceType.ONECONN, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(rdOrientation.nextInt(2) + 1);
					break;
				case LTYPE:
					p.setOrientation(1);
					break;
				default:
					p.setOrientation(0);
					break;
				}
			}
			// Case last line
		} else {
			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				if (inputGrid.getPiece(line - 1, column).hasBottomConnector()) {
					setPossiblePieceType(new PieceType[] { PieceType.TTYPE, PieceType.LTYPE }, p);
					switch (p.getType()) {
					case LTYPE:
						p.setOrientation(3);
						break;
					default:
						p.setOrientation(0);
						break;
					}
				} else {
					setPossiblePieceType(new PieceType[] { PieceType.ONECONN, PieceType.BAR }, p);
					switch (p.getType()) {
					case ONECONN:
						p.setOrientation(3);
						break;
					default:
						p.setOrientation(1);
						break;
					}
				}
			} else if (inputGrid.getPiece(line - 1, column).hasBottomConnector()) {
				setPossiblePieceType(new PieceType[] { PieceType.ONECONN, PieceType.LTYPE }, p);
				p.setOrientation(0);
			} else {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.VOID }, p);
				else
					setPossiblePieceType(new PieceType[] { PieceType.VOID, PieceType.ONECONN }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(1);
					break;
				default:
					p.setOrientation(0);
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
	 * @param p
	 *            piece instance (i,j) with no type or orientation
	 * @param grid
	 * 
	 */
	public static void affectPiece2BorderColumn(int line, int column, Piece p, Grid inputGrid) {
		Random rdOrientation = new Random();
		// Case first column
		if (column == 0 && line > 0 && line < inputGrid.getHeight() - 1) {
			if (inputGrid.getPiece(line - 1, column).hasBottomConnector()) {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.BAR, PieceType.TTYPE }, p);
				else
					setPossiblePieceType(
							new PieceType[] { PieceType.ONECONN, PieceType.BAR, PieceType.TTYPE, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(0);
					break;
				case BAR:
					p.setOrientation(0);
					break;
				case LTYPE:
					p.setOrientation(0);
					break;
				default:
					p.setOrientation(1);
					break;
				}
			} else {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.VOID }, p);
				else
					setPossiblePieceType(new PieceType[] { PieceType.VOID, PieceType.ONECONN, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(rdOrientation.nextInt(2) + 1);
					break;
				case LTYPE:
					p.setOrientation(1);
					break;
				default:
					break;
				}
			}
			// Case last column
		} else {
			if (inputGrid.getPiece(line - 1, column).hasBottomConnector()) {
				if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
					setPossiblePieceType(new PieceType[] { PieceType.TTYPE, PieceType.LTYPE }, p);
					p.setOrientation(3);
				} else {
					setPossiblePieceType(new PieceType[] { PieceType.ONECONN, PieceType.BAR }, p);
					p.setOrientation(0);
				}
			} else if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				setPossiblePieceType(new PieceType[] { PieceType.ONECONN, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(3);
					break;
				default:
					p.setOrientation(2);
					break;
				}
			} else {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.VOID }, p);
				else
					setPossiblePieceType(new PieceType[] { PieceType.VOID, PieceType.ONECONN }, p);
				switch (p.getType()) {
				case ONECONN:
					p.setOrientation(2);
					break;
				default:
					p.setOrientation(0);
					break;
				}
			}
		}
		inputGrid.setPiece(line, column, p);

	}

	/**
	 * Affect a central piece : must respect the connectors of the upper and the
	 * left piece
	 * 
	 * @param line
	 * @param column
	 * @param p
	 *            p piece instance (i,j) with no type or orientation
	 * @param inputGrid
	 */
	public static void affectCentralPieces(int line, int column, Piece p, Grid inputGrid) {
		Random rdOrientation = new Random();
		if (inputGrid.getPiece(line - 1, column).hasBottomConnector()) {
			if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.TTYPE, PieceType.FOURCONN }, p);
				else
					setPossiblePieceType(new PieceType[] { PieceType.TTYPE, PieceType.FOURCONN, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case TTYPE:
					if (rdOrientation.nextInt(2) == 0)
						p.setOrientation(0);
					else
						p.setOrientation(3);
					break;
				case LTYPE:
					p.setOrientation(3);
					break;
				default:
					p.setOrientation(0);
					break;
				}
			} else {
				if (inputGrid.getNbcc() != -1)
					setPossiblePieceType(new PieceType[] { PieceType.BAR, PieceType.TTYPE, PieceType.LTYPE }, p);
				else
					setPossiblePieceType(
							new PieceType[] { PieceType.ONECONN, PieceType.BAR, PieceType.TTYPE, PieceType.LTYPE }, p);
				switch (p.getType()) {
				case TTYPE:
					p.setOrientation(1);
					break;
				default:
					p.setOrientation(0);
					break;
				}
			}
		} else if (inputGrid.getPiece(line, column - 1).hasRightConnector()) {
			if (inputGrid.getNbcc() != -1)
				setPossiblePieceType(new PieceType[] { PieceType.BAR, PieceType.TTYPE, PieceType.LTYPE }, p);
			else
				setPossiblePieceType(
						new PieceType[] { PieceType.ONECONN, PieceType.BAR, PieceType.TTYPE, PieceType.LTYPE }, p);
			switch (p.getType()) {
			case ONECONN:
				p.setOrientation(3);
				break;
			case BAR:
				p.setOrientation(1);
				break;
			default:
				p.setOrientation(2);
				break;
			}
		} else {
			if (inputGrid.getNbcc() != -1)
				setPossiblePieceType(new PieceType[] { PieceType.VOID }, p);
			else
				setPossiblePieceType(new PieceType[] { PieceType.VOID, PieceType.ONECONN, PieceType.LTYPE }, p);
			switch (p.getType()) {
			case ONECONN:
				p.setOrientation(rdOrientation.nextInt(2) + 1);
				break;
			case LTYPE:
				p.setOrientation(1);
				break;
			default:
				p.setOrientation(0);
				break;
			}
		}
		inputGrid.setPiece(line, column, p);
	}

	/**
	 * Choose randomly a possible piece type and set the type to the piece
	 * 
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
	 * 
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

	/**
	 * Divide the inputGrid into Grid[] containing nbcc*Grid
	 * 
	 * @param inputGrid
	 * @return Grid[]
	 */
	public static Grid[] divideGrid(Grid inputGrid) {
		int w = inputGrid.getWidth(); // width
		int h = inputGrid.getHeight(); // height
		int nbcc = inputGrid.getNbcc(); // nbcc
		Grid[] grids = new Grid[nbcc];
		Random r = new Random();

		if (nbcc % 2 == 0) { // pair number of cc
			for (int i = 0; i < nbcc; i = i + 2) {
				int hOrw = r.nextInt(2);
				if (i == 0) {// for the first two grids, we choose randomly
								// between height and width, and we divide it
					if (hOrw == 0) {
						w = Math.round((float)w / 2);
					} else {
						h = Math.round((float)h / 2);
					}
					grids[i] = new Grid(w, h, 1);
					grids[i + 1] = new Grid(w, h, 1);
					// DEBUG System.out.println("Grid "+i+" : w = "+w+" / h =
					// "+h);
					// DEBUG System.out.println("Grid "+(i+1)+" : w = "+w+" / h
					// = "+h);
				} else {// for the others grids, we divide the bigger one
					if (w < h) {
						h = Math.round((float)h / 2);
						grids[i - 2].setHeight(h);// we have to change the
													// height of the 2 grids
													// that we divided
						grids[i - 1].setHeight(h);
					} else {
						w = Math.round((float)w / 2);
						grids[i - 2].setWidth(h);// we have to change the width
													// of the 2 grids that we
													// divided
						grids[i - 1].setWidth(h);// NORMALEMENT C'EST W ICI,
													// MAIS QUAND JE METS H CA
													// COMPILE EN DONNANT DE LA
													// MERDE QUAND JE METS W CA
													// COMPILE PAS
					}
					grids[i] = new Grid(w, h, 1);
					grids[i + 1] = new Grid(w, h, 1);
					// DEBUG System.out.println("Grid "+(i-1)+" : w = "+w+" / h
					// = "+h);
					// DEBUG System.out.println("Grid "+(i-2)+" : w = "+w+" / h
					// = "+h);
					// DEBUG System.out.println("Grid "+i+" : w = "+w+" / h =
					// "+h);
					// DEBUG System.out.println("Grid "+(i+1)+" : w = "+w+" / h
					// = "+h);
				}
				/*
				 * Ici je voulais ajuster la dernière Grid qui devait remplir
				 * tout l'espace qui reste, mais bon galere un peu ... int width
				 * = widthCount(grids); System.out.println("width = "+width);
				 * if(width < inputGrid.getHeight()*inputGrid.getWidth()){ int
				 * missingCases = (inputGrid.getHeight()*inputGrid.getWidth()) -
				 * (width - grids[nbcc-1].getHeight()*grids[nbcc-1].getWidth());
				 * System.out.println("missing  cases = "+missingCases); if(w <
				 * h){ w = missingCases/h; grids[nbcc-1].setWidth(w); } else{ h
				 * = missingCases/w; grids[nbcc-1].setHeight(h); }
				 * System.out.println("grid "+(nbcc-1)+" / height : "+grids[nbcc
				 * -1].getHeight()+" width : "+grids[nbcc-1].getWidth()); }
				 */
			}
			// FIRST ALGO, don't work for big nbcc ..
			/*
			 * for(int i = 0; i < nbcc ; i++){ System.out.println("i = " +i);
			 * if(i == nbcc-1){ if(w != inputGrid.getWidth() && ((nbcc-1)*w <
			 * inputGrid.getWidth())){ w = inputGrid.getWidth() - ((nbcc-1)*w);
			 * } else if(h != inputGrid.getHeight() && ((nbcc-1)*h <
			 * inputGrid.getHeight())){ h = inputGrid.getHeight() -
			 * ((nbcc-1)*h); } } grids[i] = new Grid(w, h, 1);
			 * System.out.println("Grid "+i+" : w = "+w+" / h = "+h); }
			 */

		} else { // impair number of cc
			for (int i = 0; i < nbcc - 1; i = i + 2) {
				int hOrw = r.nextInt(2);

				if (hOrw == 0) { // randomly choose between h or w to divide
					w = w / 2;
				} else {
					h = h / 2;
				}
			}
			for (int i = 0; i < nbcc - 1; i++) {
				// DEBUG System.out.println("Grid "+i+" : w = "+w+" / h = "+h);
				grids[i] = new Grid(w, h, 1);
			}
			if (w != inputGrid.getWidth() && ((nbcc - 1) * w < inputGrid.getWidth())) {
				w = inputGrid.getWidth() - ((nbcc - 1) * w);
			} else if (h != inputGrid.getHeight() && ((nbcc - 1) * h < inputGrid.getHeight())) {
				h = inputGrid.getHeight() - ((nbcc - 1) * h);
			}
			grids[nbcc - 1] = new Grid(w, h, 1);
			System.out.println("Grid " + (nbcc - 1) + " : w = " + w + " / h = " + h);
		}

		return grids;
	}

	/**
	 * Copy the contents of the filledGrids into the inputGrid
	 * 
	 * @param filledGrid,
	 *            inputGrid, line & column
	 * @return int[] index of the last line & column that we filled
	 */
	public static int[] copyGrid(Grid filledGrid, Grid inputGrid, int i, int j) {
		Piece p;
		int hmax = inputGrid.getHeight();
		int wmax = inputGrid.getWidth();

		if (inputGrid.getHeight() != filledGrid.getHeight())
			hmax = filledGrid.getHeight() + i; // we must adjust hmax to have the height of the original grid
		if (inputGrid.getWidth() != filledGrid.getWidth())
			wmax = filledGrid.getWidth() + j;

		int tmpi = 0;// temporary variable to stock the last index
		int tmpj = 0;

		// DEBUG System.out.println("copyGrid : i =" + i + " & j = " + j);
		// DEBUG System.out.println("hmax = " + hmax + " - wmax = " + wmax);
		for (int x = i; x < hmax; x++) {
			for (int y = j; y < wmax; y++) {
				// DEBUG System.out.println("x = " + x + " - y = " + y);
				p = filledGrid.getPiece(x - i, y - j);
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(p);
				inputGrid.setPiece(x, y, new Piece(x, y, p.getType(), p.getOrientation()));
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(inputGrid.getPiece(x, y));
				tmpj = y;
			}
			tmpi = x;
		}
		System.out.println("tmpi =" + tmpi + " & tmpj = " + tmpj);
		return new int[] { tmpi, tmpj };
	}

	/*
	 * public static int widthCount(Grid[] grids){ int somme = 0; int cpt = 0;
	 * for(Grid grid : grids){
	 * System.out.println("grid : "+cpt+" / width : "+(grid.getHeight()*grid.
	 * getWidth())); somme = somme + (grid.getHeight()*grid.getWidth()); cpt++;
	 * } return somme; }
	 */
	/*public static void main(String[] args) {
		try {
			generateLevel("NotSolution.txt", new Grid(10, 10, 4));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error when encoding the file");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Can't generate the file");
		}

	}*/
}