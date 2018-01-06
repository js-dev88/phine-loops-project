package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Pair;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

public class Solver {

	public static void main(String[] args) {

		try {
			long averageTime = 0;
			for (int i = 0; i < 30; i++) {
				Generator.generateLevel("NotSolution.txt", new Grid(10, 10));
				long start = System.currentTimeMillis();
				System.out.println(solveGrid("NotSolution.txt", "Solved.txt", "0"));
				long stop = System.currentTimeMillis();
				averageTime += (stop - start);
				System.out.println((stop - start));
				if ((stop - start) > 20000) {
					System.out.println("passed");
					break;
				}
			}
			System.out.println("Average time to runs 30 grid" + averageTime / 30 + " ms");

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { long start = System.currentTimeMillis();
		 * System.out.println(solveGrid("NotSolution.txt", "Solved.txt", "0"));
		 * long stop = System.currentTimeMillis(); System.out.println((stop -
		 * start)); } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	/**
	 * General function to call in first
	 * 
	 * @param inputFile
	 * @param ouputFile
	 * @param pieceChoiceMethod
	 *            default 0
	 * @return true if the grid is solved
	 * @throws IOException
	 */
	public static boolean solveGrid(String inputFile, String ouputFile, String pieceChoiceMethod) throws IOException {
		boolean rez = false;
		Grid grid = Checker.buildGrid(inputFile);
		//rez = naiveRecursiveSolver(0, 0, Objects.requireNonNull(grid));
		rez = solveIT(grid, pieceChoiceMethod);
		Generator.writeGridOnFile(ouputFile, grid);
		return rez;
	}

	/**
	 * Solver
	 * 
	 * @param grid
	 * @param pieceChoiceMethod
	 * @return true if the grid is resolved
	 */
	public static boolean solveIT(Grid grid, String pieceChoiceMethod) {
		Objects.requireNonNull(grid);

		if (Checker.isSolution(grid) == null)
			return true; // check if the grid is already solution
		if (!grid.allPieceHaveNeighbour())
			return false; // check if there is a piece with no neighbor
		Stack<Pair<Piece, Orientation>> pile;
		// first we create a pile with the frst possible piece and its
		// orientation
		pile = createStackLeft2Right(grid);

		while (!pile.isEmpty()) { // simulation of recursivity

			Pair<Piece, Orientation> currentPiece = pile.pop(); // depile
			currentPiece.getKey().setOrientation(currentPiece.getValue().getValue());// set
																						// the
																						// orientation
			grid.setPiece(currentPiece.getKey().getPosY(), currentPiece.getKey().getPosX(), currentPiece.getKey()); // update
																													// the
																													// grid
			Piece lastPiece = Checker.isSolution(grid);
			if (lastPiece == null) {
				/* DEBUG */
				// System.out.println( pile.toString());
				// System.out.println( grid.toString());
				return true;

			}
			// search the next piece to test
			pile = addPiece2StackLeft2Right(grid, pile, currentPiece.getKey(), lastPiece);

			/* DEBUG */
			// System.out.println( pile.toString());
			// System.out.println(grid.toString());
		}

		return false;

	}

	/**
	 * Add the next piece possible positions to the stack
	 * 
	 * @param grid
	 * @param pile
	 * @param currentpiece
	 * @param lastPiece
	 *            the last piece not connected
	 * @return
	 */
	public static Stack<Pair<Piece, Orientation>> addPiece2StackLeft2Right(Grid grid,
			Stack<Pair<Piece, Orientation>> pile, Piece currentpiece, Piece lastPiece) {

		if ((currentpiece.getPosX() > lastPiece.getPosX() && currentpiece.getPosY() == lastPiece.getPosY())
				|| currentpiece.getPosY() > lastPiece.getPosY()) {
			Piece tn = grid.topNeighbor(lastPiece);
			Piece ln = grid.leftNeighbor(lastPiece);
			Piece rn = grid.rightNeighbor(lastPiece);
			Piece bn = grid.bottomNeighbor(lastPiece);
			if (tn != null && ((lastPiece.hasTopConnector() && !tn.hasBottomConnector())
					|| (!lastPiece.hasTopConnector() && tn.hasBottomConnector()))) {
				return pile;
			}
			if (ln != null && ((lastPiece.hasLeftConnector() && !ln.hasRightConnector())
					|| (!lastPiece.hasLeftConnector() && ln.hasRightConnector()))) {
				return pile;
			}
			if (rn != null && ((lastPiece.hasRightConnector() && !rn.hasLeftConnector())
					|| (!lastPiece.hasRightConnector() && rn.hasLeftConnector()))) {
				return pile;
			}
			if (currentpiece.getPosX() > lastPiece.getPosX() && currentpiece.getPosY() == lastPiece.getPosY() + 1) {
				if (bn != null && ((lastPiece.hasBottomConnector() && !bn.hasTopConnector())
						|| (!lastPiece.hasBottomConnector() && bn.hasTopConnector()))) {
					return pile;
				}
			}
			if (currentpiece.getPosY() > lastPiece.getPosY() + 1) {
				Pair<Piece, Orientation> forty_two = pile.peek();
				while (forty_two.getKey().getPosX() > lastPiece.getPosX() + 1
						&& forty_two.getKey().getPosY() > lastPiece.getPosY() + 1) {
					forty_two = pile.pop();
				}
				return pile;
			}
		}
		Piece nextPiece = grid.getNextPiece(currentpiece);

		while (nextPiece != null && (nextPiece.getType() == PieceType.VOID || nextPiece.isFixed())) {
			nextPiece = grid.getNextPiece(nextPiece);
			if (nextPiece == null)
				break;
		}
		pile = checkAndAdd(nextPiece, grid, pile);

		return pile;
	}

	/**
	 * Filtering the possible orientations, ugly but works
	 * 
	 * @param nextPiece
	 * @param grid
	 * @param pile
	 * @return the updated stack
	 */
	public static Stack<Pair<Piece, Orientation>> checkAndAdd(Piece nextPiece, Grid grid,
			Stack<Pair<Piece, Orientation>> pile) {
		if (nextPiece == null)
			return pile;
		for (Orientation ori : nextPiece.getPossibleOrientations()) {
			nextPiece.setOrientation(ori.getValue());
			// check if it is a possible orientation
			if (grid.hasNeighbour(nextPiece)) {
				// check can connect to the left piece & upper piece
				Piece ln = grid.leftNeighbor(nextPiece);
				Piece tn = grid.topNeighbor(nextPiece);
				Piece rn = grid.rightNeighbor(nextPiece);
				Piece bn = grid.bottomNeighbor(nextPiece);

				if (ln != null && ln.hasRightConnector()) {
					if (tn != null && tn.hasBottomConnector()) {
						if (rn != null && rn.isFixed() && rn.hasLeftConnector()) {
							if (bn != null && bn.isFixed() && bn.hasTopConnector()) {
								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.EAST)
										&& nextPiece.getConnectors().contains(Orientation.SOUTH)
										&& nextPiece.getConnectors().contains(Orientation.NORTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							} else {
								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.EAST)
										&& nextPiece.getConnectors().contains(Orientation.NORTH)
										&& !nextPiece.getConnectors().contains(Orientation.SOUTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							}
						} else if (bn != null && bn.isFixed() && bn.hasTopConnector()) {
							if (nextPiece.getConnectors().contains(Orientation.WEST)
									&& nextPiece.getConnectors().contains(Orientation.SOUTH)
									&& nextPiece.getConnectors().contains(Orientation.NORTH)
									&& !nextPiece.getConnectors().contains(Orientation.EAST)) {
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}
						} else {
							if (bn != null && bn.isFixed() && !bn.hasTopConnector()) {
								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.NORTH)
										&& !nextPiece.getConnectors().contains(Orientation.SOUTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							} else if (rn != null && rn.isFixed() && !rn.hasLeftConnector()) {
								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.NORTH)
										&& !nextPiece.getConnectors().contains(Orientation.EAST)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}

							} else {

								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.NORTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}

							}
						}

					} else {
						if (rn != null && rn.isFixed() && rn.hasLeftConnector()) {
							if (bn != null && bn.isFixed() && bn.hasTopConnector()) {
								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.EAST)
										&& nextPiece.getConnectors().contains(Orientation.SOUTH)
										&& !nextPiece.getConnectors().contains(Orientation.NORTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							} else {
								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.EAST)
										&& !nextPiece.getConnectors().contains(Orientation.NORTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							}
						} else if (bn != null && bn.isFixed() && bn.hasTopConnector()) {
							if (nextPiece.getConnectors().contains(Orientation.WEST)
									&& nextPiece.getConnectors().contains(Orientation.SOUTH)
									&& !nextPiece.getConnectors().contains(Orientation.NORTH)) {
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}
						} else {
							if (rn != null && rn.isFixed() && !rn.hasLeftConnector()) {
								if (bn != null && bn.isFixed() && !bn.hasTopConnector()) {// modif
									if (nextPiece.getConnectors().contains(Orientation.WEST)
											&& !nextPiece.getConnectors().contains(Orientation.NORTH)
											&& !nextPiece.getConnectors().contains(Orientation.EAST)
											&& !nextPiece.getConnectors().contains(Orientation.SOUTH)) {
										pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
									}
								} else {
									if (nextPiece.getConnectors().contains(Orientation.WEST)
											&& !nextPiece.getConnectors().contains(Orientation.NORTH)
											&& !nextPiece.getConnectors().contains(Orientation.EAST)) {
										pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
									}
								}

							} else if (bn != null && bn.isFixed() && !bn.hasTopConnector()) {
								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& !nextPiece.getConnectors().contains(Orientation.NORTH)
										&& !nextPiece.getConnectors().contains(Orientation.SOUTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							} else {
								if (nextPiece.getConnectors().contains(Orientation.WEST)
										&& !nextPiece.getConnectors().contains(Orientation.NORTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							}

						}
					}
				} else if (tn != null && tn.hasBottomConnector()) {
					if (rn != null && rn.isFixed() && rn.hasLeftConnector()) {
						if (bn != null && bn.isFixed() && bn.hasTopConnector()) {
							if (nextPiece.getConnectors().contains(Orientation.NORTH)
									&& nextPiece.getConnectors().contains(Orientation.EAST)
									&& nextPiece.getConnectors().contains(Orientation.SOUTH)
									&& !nextPiece.getConnectors().contains(Orientation.WEST)) {
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}
						} else {// modif
							if (bn != null && bn.isFixed() && !bn.hasTopConnector()) {
								if (nextPiece.getConnectors().contains(Orientation.NORTH)
										&& nextPiece.getConnectors().contains(Orientation.EAST)
										&& !nextPiece.getConnectors().contains(Orientation.WEST)
										&& !nextPiece.getConnectors().contains(Orientation.SOUTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							} else {
								if (nextPiece.getConnectors().contains(Orientation.NORTH)
										&& nextPiece.getConnectors().contains(Orientation.EAST)
										&& !nextPiece.getConnectors().contains(Orientation.WEST)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							}

						}
					} else if (bn != null && bn.isFixed() && bn.hasTopConnector()) {
						if (rn != null && rn.isFixed() && !rn.hasLeftConnector()) {// modif
							if (nextPiece.getConnectors().contains(Orientation.NORTH)
									&& nextPiece.getConnectors().contains(Orientation.SOUTH)
									&& !nextPiece.getConnectors().contains(Orientation.WEST)
									&& !nextPiece.getConnectors().contains(Orientation.EAST)) {
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}
						} else {
							if (nextPiece.getConnectors().contains(Orientation.NORTH)
									&& nextPiece.getConnectors().contains(Orientation.SOUTH)
									&& !nextPiece.getConnectors().contains(Orientation.WEST)) {
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}
						}

					} else {
						if (nextPiece.getConnectors().contains(Orientation.NORTH)
								&& !nextPiece.getConnectors().contains(Orientation.WEST)) {
							pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
						}
					}
				} else {
					if (rn != null && rn.hasLeftConnector() && rn.isFixed()) {
						if (bn != null && bn.hasTopConnector() && bn.isFixed()) {
							if (!nextPiece.getConnectors().contains(Orientation.NORTH)
									&& !nextPiece.getConnectors().contains(Orientation.WEST)
									&& nextPiece.getConnectors().contains(Orientation.EAST)
									&& nextPiece.getConnectors().contains(Orientation.SOUTH)) {
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}
						} else {
							if (bn != null && bn.isFixed() && !bn.hasTopConnector()) {// modif
								if (!nextPiece.getConnectors().contains(Orientation.NORTH)
										&& !nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.EAST)
										&& !nextPiece.getConnectors().contains(Orientation.SOUTH)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							} else {
								if (!nextPiece.getConnectors().contains(Orientation.NORTH)
										&& !nextPiece.getConnectors().contains(Orientation.WEST)
										&& nextPiece.getConnectors().contains(Orientation.EAST)) {
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
								}
							}

						}

					} else if (bn != null && bn.hasTopConnector() && bn.isFixed()) {
						if (rn != null && rn.isFixed() && !rn.hasLeftConnector()) {
							if (!nextPiece.getConnectors().contains(Orientation.NORTH)
									&& !nextPiece.getConnectors().contains(Orientation.WEST)
									&& !nextPiece.getConnectors().contains(Orientation.EAST)
									&& nextPiece.getConnectors().contains(Orientation.SOUTH)) {
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}
						} else {
							if (!nextPiece.getConnectors().contains(Orientation.NORTH)
									&& !nextPiece.getConnectors().contains(Orientation.WEST)
									&& nextPiece.getConnectors().contains(Orientation.SOUTH)) {
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}
						}

					} else {// modify //important win 3s !!!!!!
						if (rn != null && rn.isFixed() && !rn.hasLeftConnector()) {
							if (!nextPiece.getConnectors().contains(Orientation.NORTH)
									&& !nextPiece.getConnectors().contains(Orientation.EAST)
									&& !nextPiece.getConnectors().contains(Orientation.WEST))
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
						} else if (bn != null && !bn.hasTopConnector() && bn.isFixed()) {
							if (!nextPiece.getConnectors().contains(Orientation.NORTH)
									&& !nextPiece.getConnectors().contains(Orientation.SOUTH)
									&& !nextPiece.getConnectors().contains(Orientation.WEST))
								pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
						} else {
							if (bn != null && !bn.hasTopConnector()) {
								if (!nextPiece.getConnectors().contains(Orientation.NORTH)
										&& !nextPiece.getConnectors().contains(Orientation.WEST))
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							} else {
								if (!nextPiece.getConnectors().contains(Orientation.NORTH)
										&& !nextPiece.getConnectors().contains(Orientation.WEST))
									pile.push(new Pair<Piece, Orientation>(nextPiece, ori));
							}

						}

					}

				}

			}

		}
		return pile;
	}

	/**
	 * insert the first piece with all the possibles orientations
	 * 
	 * @param grid
	 * @param pieceChoiceMethod
	 *            default 0
	 * @return a stack
	 */
	public static Stack<Pair<Piece, Orientation>> createStackLeft2Right(Grid grid) {
		fixPieceOnGrid(grid);

		Stack<Pair<Piece, Orientation>> pile = new Stack<>();
		Piece p = grid.getPiece(0, 0); // choix de gauche à droite
		while (p != null && (p.getType() == PieceType.VOID || p.isFixed())) {
			p = grid.getNextPiece(p);
		}
		checkAndAdd(p, grid, pile);
		return pile;

	}

	/**
	 * For each piece, we test if we can fix the piece
	 * 
	 * @param grid
	 */
	public static void fixPieceOnGrid(Grid grid) {
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				fixAparticularPiece(grid, grid.getPiece(i, j));
			}
		}
	}

	/**
	 * Deleteof the not possible orientations and fix the piece
	 * 
	 * @param grid
	 */
	public static void fixAparticularPiece(Grid grid, Piece p) {
		Stack<Piece> pileOfPiece2fix = new Stack<Piece>();

		if (p.getType() == PieceType.VOID || p.getType() == PieceType.FOURCONN) {
			p.setFixed(true);
		} else if (!p.isFixed() && p.getType().getNbConnectors() == grid.numberOfNeibours(p)) {
			while (!grid.hasNeighbour(p)) {
				p.turn();
			}
			p.setFixed(true);
			// System.out.println("1 :"+(p));
		} else {

			for (int i = 0; i < Orientation.values().length; i++) {
				if (!grid.hasNeighbour(p))
					p.deleteFromPossibleOrientation(p.getOrientation());
				p.turn();
			}

			if (p.getPossibleOrientations().size() == 1) {
				p.setOrientation(p.getPossibleOrientations().get(0).getValue());
				p.setFixed(true);
				// System.out.println("2 :"+(p));
			}

		}
		if (p.isFixed()) {
			for (Piece p2 : grid.listOfNeighbours(p)) {
				if (!pileOfPiece2fix.contains(p2) && !p2.isFixed())
					pileOfPiece2fix.add(p2);
			}
			fixNeighboursOnGrid(pileOfPiece2fix, grid);
		}

	}

	/**
	 * Fix the neighbors on the grid
	 * 
	 * @param grid
	 */
	public static void fixNeighboursOnGrid(Stack<Piece> pileOfPiece2fix, Grid grid) {
		while (!pileOfPiece2fix.isEmpty()) {
			Piece p = pileOfPiece2fix.pop();
			if (!p.isFixed() && p.getType().getNbConnectors() == grid.numberOfFixedNeibours(p)) {
				int i = 0;
				while (!grid.hasFixedNeighbour(p) && i < Orientation.values().length) {
					p.turn();
					i++;
				}
				if (grid.hasFixedNeighbour(p)) {
					p.setFixed(true);
					// System.out.println("Add :"+(p));
					for (Piece p2 : grid.listOfNeighbours(p)) {
						if (!pileOfPiece2fix.contains(p2) && !p2.isFixed())
							pileOfPiece2fix.add(p2);
					}
				}

			}

		}
	}
	
	/**
	 * Solver V2
	 * Naive, recursive 
	 * @param grid
	 * @param posX, posY (initially 0 & 0 because of naive)
	 * @return true if the grid is resolved
	 */
	public static boolean naiveRecursiveSolver(int posX, int posY, Grid grid){		
	
		if (posX == (grid.getHeight() - 1) && posY == (grid.getWidth() - 1)) {
			System.out.println(Checker.isSolution(grid));
			if (Checker.isSolution(grid) == null){
				return true;
			}
		}

		else if (posY == (grid.getWidth() - 1)) {
			Piece p = grid.getPiece(posX, posY);
			int orientationsNumber = switchOrientations(p.getType().getValue());
			for (int i = 0; i < orientationsNumber; i++) {
				System.out.println("piece avant :"+p);
				p.setOrientation(i);
				System.out.println("piece apres :"+p);
				naiveRecursiveSolver(posX+1, 0, grid);
			}
		}

		else {
			Piece p = grid.getPiece(posX, posY);
			int orientationsNumber = switchOrientations(p.getType().getValue());
			for (int i = 0; i < orientationsNumber; i++) {
				System.out.println("piece avant :"+p);
				p.setOrientation(i);
				System.out.println("piece apres :"+p);
				naiveRecursiveSolver(posX, posY+1, grid);
			}
		}

		return false;
		
	}
	
	/**
	 * number of possible orientations for a piece type
	 * 
	 * @param value of the piece type 
	 * @return number of orientations 
	 */
	public static int switchOrientations(int value) {

		if (value < 0 || value > 5)
			throw new IllegalArgumentException();
		switch (value) {
		case 0:
			return 1;
		case 1:
			return 4;
		case 2:
			return 2;
		case 3:
			return 4;
		case 4:
			return 1;
		case 5:
			return 4;
		default:
			return -1;
		}

	}

}
