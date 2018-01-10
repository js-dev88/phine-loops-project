package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Predicate;

import org.bitbucket.jsdev88.projethaddadsaussier.io.GUITEST;
import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Pair;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

public class Solver {

	public static void main(String[] args) {

		try {
			long averageTime = 0;

			for (int i = 0; i < 1; i++) {
				Generator.generateLevel("NotSolution.txt", new Grid(100, 100));
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
		  try { long start = System.currentTimeMillis();
		  System.out.println(solveGrid("NotSolution.txt", "Solved.txt", "0"));

		  long stop = System.currentTimeMillis(); System.out.println((stop -
		  start)); } catch (IOException e) { // TODO Auto-generated catch block
		 e.printStackTrace(); }*/
		 

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
		//System.out.println(grid.toString());
		if (Checker.isSolution(grid) == null)
			return true; // check if the grid is already solution
		if (!grid.allPieceHaveNeighbour())
			return false; // check if there is a piece with no neighbor
		ArrayDeque<Pair<Piece, Orientation>> pile;
		// first we create a pile with the frst possible piece and its
		// orientation
		pile = createStackLeft2Right(grid);
		if (Checker.isSolution(grid) == null)
			return true;
		
		//System.out.println( pile.toString());
		//System.out.println(grid.toString());
		
		Pair<Piece, Orientation> currentPiece;
		while (!pile.isEmpty()) { // simulation of recursivity
			
			currentPiece = pile.pop(); 
			
			currentPiece.getKey().setOrientation(currentPiece.getValue().getValue());// set
																						// the
																						// orientation
			grid.setPiece(currentPiece.getKey().getPosY(), currentPiece.getKey().getPosX(), currentPiece.getKey()); // update
																													// the
																													// grid
			
			Piece lastPiece = Checker.isSolution(grid);
			if (lastPiece == null) {
				/* DEBUG */
				//System.out.println( pile.toString());
				//System.out.println( grid.toString());
				return true;

			}
			
			
			pile = addPiece2StackLeft2Right(grid, pile, currentPiece.getKey(), lastPiece);
			
			
			//Pair<Piece, Orientation> firstPick = pile.peek();
			
			
			/*if(firstPick.getKey().getPosY() < currentPiece.getKey().getPosY() || firstPick.getKey().getPosX() < currentPiece.getKey().getPosX() ){
				final Pair<Piece, Orientation> temp = firstPick; 
				pile.removeIf((Pair<Piece, Orientation> p) -> p.getKey().getPosY() > temp.getKey().getPosY()-1);
				System.out.println("retour");
			}*/
			/*System.out.println(currentPiece);
			System.out.println(firstPick);
			System.out.println(pile);*/
			// search the next piece to test
			
		
			/* DEBUG */

			 System.out.println( pile.toString());

			//System.out.println(grid.toString());
			 //GUITEST.startGUITEST(grid);
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
	public static ArrayDeque<Pair<Piece, Orientation>> addPiece2StackLeft2Right(Grid grid,
			ArrayDeque<Pair<Piece, Orientation>> pile, Piece currentpiece, Piece lastPiece) {
		
		Piece tn = grid.topNeighbor(currentpiece);
		Piece ln = grid.leftNeighbor(currentpiece);
		Piece rn = grid.rightNeighbor(currentpiece);
		Piece bn = grid.bottomNeighbor(currentpiece);
		
		
		
		if(currentpiece.hasRightConnector() && rn != null && !rn.hasLeftConnector() && rn.isFixed())return pile;
		if(currentpiece.hasLeftConnector() && ln != null && !ln.hasRightConnector())return pile;
		if(currentpiece.hasTopConnector() && tn != null && !tn.hasBottomConnector())return pile;
		if(currentpiece.hasBottomConnector() && bn != null && !bn.hasTopConnector() && bn.isFixed())return pile;
		
		if(!currentpiece.hasRightConnector() && rn != null && rn.hasLeftConnector() && rn.isFixed())return pile;
		if(!currentpiece.hasLeftConnector() && ln != null && ln.hasRightConnector())return pile;
		if(!currentpiece.hasTopConnector() && tn != null && tn.hasBottomConnector())return pile;
		if(!currentpiece.hasBottomConnector() && bn != null && bn.hasTopConnector() && bn.isFixed())return pile;
		
		if(currentpiece.hasRightConnector() && rn == null) return pile;
		if(currentpiece.hasLeftConnector() && ln == null) return pile;
		if(currentpiece.hasTopConnector() && tn == null) return pile;
		if(currentpiece.hasBottomConnector() && bn == null) return pile;
		
		

		Piece nextPiece = grid.getNextPiece(currentpiece);

		while (nextPiece != null && (nextPiece.getType() == PieceType.VOID || nextPiece.isFixed())) {
			nextPiece = grid.getNextPiece(nextPiece);
			if (nextPiece == null)
				break;
		}
		
		
		for (Orientation ori : nextPiece.getPossibleOrientations()) {
			nextPiece.setOrientation(ori.getValue());
			pile = checkAndAdd(nextPiece, grid, pile);
		}
			

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
	public static ArrayDeque<Pair<Piece, Orientation>> checkAndAdd(Piece nextPiece, Grid grid,
			ArrayDeque<Pair<Piece, Orientation>> pile) {
		
		
		
		Piece ln = grid.leftNeighbor(nextPiece);
		Piece tn = grid.topNeighbor(nextPiece);
		Piece rn = grid.rightNeighbor(nextPiece);
		Piece bn = grid.bottomNeighbor(nextPiece);
		
		
			Pair<Piece, Orientation> p = new Pair<Piece, Orientation>(nextPiece, nextPiece.getOrientation());
			
			if (grid.hasNeighbour(nextPiece) && !pile.contains(p)) {
				
				
				
				if(nextPiece.hasLeftConnector() && ln != null && !ln.hasRightConnector())return pile;
				if(nextPiece.hasTopConnector() && tn != null && !tn.hasBottomConnector())return pile;
				if(nextPiece.hasRightConnector() && rn != null && !rn.hasLeftConnector() && rn.isFixed())return pile;
				if(nextPiece.hasBottomConnector() && bn != null && !bn.hasTopConnector() && bn.isFixed())return pile;
				
				if(!nextPiece.hasRightConnector() && rn != null && rn.hasLeftConnector() && rn.isFixed())return pile;
				if(!nextPiece.hasLeftConnector() && ln != null && ln.hasRightConnector())return pile;
				if(!nextPiece.hasTopConnector() && tn != null && tn.hasBottomConnector())return pile;
				if(!nextPiece.hasBottomConnector() && bn != null && bn.hasTopConnector() && bn.isFixed())return pile;
				
				if(nextPiece.hasRightConnector() && rn == null) return pile;
				if(nextPiece.hasLeftConnector() && ln == null) return pile;
				if(nextPiece.hasTopConnector() && tn == null) return pile;
				if(nextPiece.hasBottomConnector() && bn == null) return pile;
				
				
				
				pile.push(p);
				
				
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
	public static ArrayDeque<Pair<Piece, Orientation>> createStackLeft2Right(Grid grid) {
		fixPieceOnGrid(grid);

		ArrayDeque<Pair<Piece, Orientation>> pile = new ArrayDeque<>();
		Piece p = grid.getPiece(0, 0); // choix de gauche à droite
		while (p != null && (p.getType() == PieceType.VOID || p.isFixed())) {
			p = grid.getNextPiece(p);
		}
	    if(p==null) return pile;
		//System.out.println("check "+p);
		for (Orientation ori : p.getPossibleOrientations()) {
			p.setOrientation(ori.getValue());
			pile = checkAndAdd(p, grid, pile);
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
		long start = System.currentTimeMillis();
		  
		 
		for (int i = 0; i < grid.getHeight(); i++) {
			for (int j = 0; j < grid.getWidth(); j++) {
				fixAparticularPiece(grid, grid.getPiece(i, j));
			}
		}
		  long stop = System.currentTimeMillis();
		  //System.out.println((stop -
				//  start));
		 /* for (int i = 0; i < grid.getHeight(); i++) {
				for (int j = 0; j < grid.getWidth(); j++) {
					System.out.println("Pice : "+grid.getPiece(i, j)+ "isfixed : "+grid.getPiece(i, j).isFixed());
				}
			}*/
	}

	/**
	 * Deleteof the not possible orientations and fix the piece
	 * 
	 * @param grid
	 */
	public static void fixAparticularPiece(Grid grid, Piece p) {
		ArrayDeque<Piece> pileOfPiece2fix = new ArrayDeque<Piece>();
		
		if(!p.isFixed()){
			if (p.getType() == PieceType.VOID || p.getType() == PieceType.FOURCONN) {
				p.setFixed(true);
				//System.out.println("fix : void / fourconn : "+ p);
			} else {

				Piece ln = grid.leftNeighbor(p);
				Piece tn = grid.topNeighbor(p);
				Piece rn = grid.rightNeighbor(p);
				Piece bn = grid.bottomNeighbor(p);
				for (int i = 0; i < Orientation.values().length; i++) {
					if ((ln != null && ln.isFixed() && ln.hasRightConnector() && !p.hasLeftConnector())
					  || (tn != null && tn.isFixed() && tn.hasBottomConnector() && !p.hasTopConnector())
					  || (rn != null && rn.isFixed() && rn.hasLeftConnector() && !p.hasRightConnector())
					  || (bn != null && bn.isFixed() && bn.hasTopConnector() && !p.hasBottomConnector())
					  || (ln != null && ln.isFixed() && !ln.hasRightConnector() && p.hasLeftConnector())
					  || (tn != null && tn.isFixed() && !tn.hasBottomConnector() && p.hasTopConnector())
					  || (rn != null && rn.isFixed() && !rn.hasLeftConnector() && p.hasRightConnector())
					  || (bn != null && bn.isFixed() && !bn.hasTopConnector() && p.hasBottomConnector())
					  || (ln == null &&  p.hasLeftConnector())
					  || (tn == null &&  p.hasTopConnector())
					  || (rn == null &&  p.hasRightConnector())
					  || (bn == null &&  p.hasBottomConnector()) 
					  ){
						//System.out.println("delete : "+p+" orientation : "+p.getOrientation());
						p.deleteFromPossibleOrientation(p.getOrientation());
					}	
						p.turn();
				}

			}
			if(p.getPossibleOrientations().size() != 0)
				p.setOrientation(p.getPossibleOrientations().get(0).getValue());
		}
		if (p.getPossibleOrientations().size() == 1) {
			//System.out.println("Size == 1 " + p);
			p.setOrientation(p.getPossibleOrientations().get(0).getValue());
			p.setFixed(true);
			
		}
		if (p.isFixed()) {
			for (Piece p2 : grid.listOfNeighbours(p)) {
				if (!pileOfPiece2fix.contains(p2) && !p2.isFixed())
					pileOfPiece2fix.add(p2);
			}
			  fixNeighboursOnGrid(pileOfPiece2fix,grid);
		}

	}

	/**
	 * Fix the neighbors on the grid
	 * 
	 * @param grid
	 */
	public static void fixNeighboursOnGrid(ArrayDeque<Piece> pileOfPiece2fix, Grid grid) {
		while (!pileOfPiece2fix.isEmpty()) {
			Piece p = pileOfPiece2fix.pop();
			 if(!p.isFixed()){
				
				 Piece ln = grid.leftNeighbor(p);
					Piece tn = grid.topNeighbor(p);
					Piece rn = grid.rightNeighbor(p);
					Piece bn = grid.bottomNeighbor(p);
					for (int i = 0; i < Orientation.values().length; i++) {
						if ((ln != null && ln.isFixed() && ln.hasRightConnector() && !p.hasLeftConnector())
						  || (tn != null && tn.isFixed() && tn.hasBottomConnector() && !p.hasTopConnector())
						  || (rn != null && rn.isFixed() && rn.hasLeftConnector() && !p.hasRightConnector())
						  || (bn != null && bn.isFixed() && bn.hasTopConnector() && !p.hasBottomConnector())
						  || (ln != null && ln.isFixed() && !ln.hasRightConnector() && p.hasLeftConnector())
						  || (tn != null && tn.isFixed() && !tn.hasBottomConnector() && p.hasTopConnector())
						  || (rn != null && rn.isFixed() && !rn.hasLeftConnector() && p.hasRightConnector())
						  || (bn != null && bn.isFixed() && !bn.hasTopConnector() && p.hasBottomConnector())
						  || (ln == null &&  p.hasLeftConnector())
						  || (tn == null &&  p.hasTopConnector())
						  || (rn == null &&  p.hasRightConnector())
						  || (bn == null &&  p.hasBottomConnector())
						  ){
							//System.out.println("2passes delete : "+p+" orientation : "+p.getOrientation());
							p.deleteFromPossibleOrientation(p.getOrientation());
						}	
							p.turn();
					}

				}
			 if(p.getPossibleOrientations().size() != 0)
				p.setOrientation(p.getPossibleOrientations().get(0).getValue());
				if (p.getPossibleOrientations().size() == 1) {
					//System.out.println("2eme passeSize == 1 " + p);
					p.setOrientation(p.getPossibleOrientations().get(0).getValue());
					p.setFixed(true);
					
				}
				if (p.isFixed()) {
					for (Piece p2 : grid.listOfNeighbours(p)) {
						if (!pileOfPiece2fix.contains(p2) && !p2.isFixed())
							pileOfPiece2fix.add(p2);
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
		Piece p;
		
		if (Checker.isSolution(grid) == null) {
			//GUITEST.startGUITEST(grid);
			return true; // check if the grid is already solution
		}
		if (!grid.allPieceHaveNeighbour())
			return false; // check if there is a piece with no neighbor
	
		if (posX == (grid.getHeight() - 1) && posY == (grid.getWidth() - 1)) {
			p = grid.getPiece(posX, posY);
			int orientationsNumber = switchOrientations(p.getType().getValue());
			for (int i = 0; i < orientationsNumber; i++) {
				p.setOrientation(i);
				if (Checker.isSolution(grid) == null){
					//System.out.println(grid);
					return true;
				}
			}
			//System.out.println(grid);
			System.out.println(Checker.isSolution(grid));
			return false;
		}
		else{
			if (posY == (grid.getWidth() - 1)) {
				p = grid.getPiece(posX, posY);
				int orientationsNumber = switchOrientations(p.getType().getValue());
				for (int i = 0; i < orientationsNumber; i++) {
					//System.out.println("piece avant 1:"+grid.getPiece(posX, posY));
					p.setOrientation(i);
					//System.out.println("piece apres 1:"+grid.getPiece(posX, posY));
					//System.out.println("valide : "+grid.isValidOrientation(posX, posY));
					if(grid.isValidOrientation(posX, posY)){
						//System.out.println(grid);
						if(naiveRecursiveSolver(posX+1, 0, grid)) return true;
					}	
					//System.out.println(grid);
				}
				return false;
			}

			else {
				p = grid.getPiece(posX, posY);
				int orientationsNumber = switchOrientations(p.getType().getValue());
				for (int i = 0; i < orientationsNumber; i++) {
					//System.out.println("piece avant 2:"+grid.getPiece(posX, posY));
					p.setOrientation(i);
					//System.out.println("piece apres 2:"+grid.getPiece(posX, posY));
					//System.out.println("valide : "+grid.isValidOrientation(posX, posY));
					if(grid.isValidOrientation(posX, posY)){
						//System.out.println(grid);
						if(naiveRecursiveSolver(posX, posY+1, grid)) return true;
					}
					//System.out.println(grid);
				}
				return false;
			}
		}	
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
