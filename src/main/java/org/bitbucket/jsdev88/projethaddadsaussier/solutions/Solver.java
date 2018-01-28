package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Objects;
import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Pair;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

public class Solver {

	
	public static void main(String[] args) {
	 
	 /*try { 
		  long averageTime = 0;  
		  for (int i = 0; i < 20; i++) { Generator.generateLevel("NotSolution.txt", new Grid(50, 50));
		  long start = System.currentTimeMillis();
		  System.out.println(solveGrid("NotSolution.txt", "Solved.txt", 0,1)); long
		  stop = System.currentTimeMillis(); averageTime += (stop - start);
		  System.out.println((stop - start)); if ((stop - start) > 20000) {
		  System.out.println("passed"); break; } }
		  System.out.println("Average time to runs 30 grid" + averageTime / 30 + " ms");
	  } catch (IOException e) { 
		  e.printStackTrace(); 
	  }*/

	/*try { long start = System.currentTimeMillis();
	 System.out.println(solveGrid("NotSolution.txt", "Solved.txt", 0,1));
	  
	  long stop = System.currentTimeMillis(); System.out.println((stop -
	  start)); } catch (IOException e) { 
	 e.printStackTrace(); 
	 }*/
	 

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
	public static boolean solveGrid(String inputFile, String ouputFile, Integer pieceChoiceMethod, Integer nbThread) throws IOException {
		boolean rez = false;
		Grid grid = Checker.buildGrid(inputFile);
	
		switch(pieceChoiceMethod){
		case 0 : rez = solveIT(grid, pieceChoiceMethod);
			     Generator.writeGridOnFile(ouputFile, grid);
			break;
		case 1 : rez = naiveRecursiveSolver(grid.getHeight()-1, grid.getWidth()-1, Objects.requireNonNull(grid));
			     Generator.writeGridOnFile(ouputFile, grid);
		    break;
		case 2 : rez = solveIT(grid, pieceChoiceMethod);
	             Generator.writeGridOnFile(ouputFile, grid);
	        break; 
		default : rez = solveIT(grid, pieceChoiceMethod);
			      Generator.writeGridOnFile(ouputFile, grid);
			break;
		}

		return rez;	
	}

	/**
	 * Solver
	 * 
	 * @param grid
	 * @param pieceChoiceMethod
	 * @return true if the grid is resolved
	 * @throws IOException 
	 */
	public static boolean solveIT(Grid grid, Integer pieceChoiceMethod) throws IOException {
		Objects.requireNonNull(grid);
		//System.out.println(grid.toString());
		if (Checker.isSolution(grid))
			return true; // check if the grid is already solution
		if (!grid.allPieceHaveNeighbour())
			return false; // check if there is a piece with no neighbor
		//fix the pieces
		fixPieceOnGrid(grid);
		ArrayDeque<Pair<Piece, Orientation>> pile;
		// first we create a pile with the first possible piece and its
		// orientation
		
		switch(pieceChoiceMethod){
		case 0 : pile = createStackLeft2Right(grid);
			break;
		case 2 : pile = createStackRight2Left(grid);
		    break;
		default : pile = createStackLeft2Right(grid);
			break;
		}
		
		
		
		if (Checker.isSolution(grid)) {
			//System.out.println(grid.toString());
			return true;
		}

		 //System.out.println( pile.toString());

		Pair<Piece, Orientation> currentPiece;
		while (!pile.isEmpty()) { // simulation of recursivity

			currentPiece = pile.pop();
			// set the orientation
			currentPiece.getKey().setOrientation(currentPiece.getValue().getValue());
			// update the grid
			grid.setPiece(currentPiece.getKey().getPosY(), currentPiece.getKey().getPosX(), currentPiece.getKey()); 
			
			if (Checker.isSolution(grid)) {
				/* DEBUG */
				// System.out.println( pile.toString());
				//System.out.println(grid.toString());
				return true;

			}
			switch(pieceChoiceMethod){
			case 0 : pile = addPiece2StackLeft2Right(grid, pile, currentPiece.getKey());
				break;
			case 2 : pile = addPiece2StackRight2Left(grid, pile, currentPiece.getKey());
			    break;
			default : pile = addPiece2StackLeft2Right(grid, pile, currentPiece.getKey());
			break;
			}
		
			/* DEBUG */
			//System.out.println( pile.toString());
			//System.out.println(grid.toString());
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
	 * @throws IOException 
	 */
	public static ArrayDeque<Pair<Piece, Orientation>> addPiece2StackLeft2Right(Grid grid,
			ArrayDeque<Pair<Piece, Orientation>> pile, Piece currentpiece) throws IOException {

		if(isEligible2PileLeft2Right(grid, currentpiece)){
			Piece nextPiece = grid.getNextPiece(currentpiece);

			while (nextPiece != null && (nextPiece.getType() == PieceType.VOID || nextPiece.isFixed())) {
				nextPiece = grid.getNextPiece(nextPiece);
				if (nextPiece == null)
					break;
			}
			
			try{
				for (Orientation ori : nextPiece.getPossibleOrientations()) {
					nextPiece.setOrientation(ori.getValue());
					pile = checkAndAddLeft2Right(nextPiece, grid, pile);
				}
			}catch(NullPointerException e){
				throw new IOException("Findbugs was right");
			}
		}
		return pile;
	}

	/**
	 * Filtering the possible orientations algo 1
	 * 
	 * @param nextPiece
	 * @param grid
	 * @param pile
	 * @return the updated stack
	 */
	public static ArrayDeque<Pair<Piece, Orientation>> checkAndAddLeft2Right(Piece nextPiece, Grid grid,
			ArrayDeque<Pair<Piece, Orientation>> pile) {


		Pair<Piece, Orientation> p = new Pair<Piece, Orientation>(nextPiece, nextPiece.getOrientation());

		if (grid.hasNeighbour(nextPiece) && !pile.contains(p)) {

			if(isEligible2PileLeft2Right(grid,nextPiece ))
				pile.push(p);

		}

		return pile;
	}

	/**
	 * insert the first piece with all the possibles orientations
	 * 
	 * @param grid
	 * 
	 * @return a stack
	 */
	public static ArrayDeque<Pair<Piece, Orientation>> createStackLeft2Right(Grid grid) {
		ArrayDeque<Pair<Piece, Orientation>> pile = new ArrayDeque<>();
		Piece p = grid.getPiece(0, 0); 
		while (p != null && (p.getType() == PieceType.VOID || p.isFixed())) {
			p = grid.getNextPiece(p);
		}
		if (p == null)
			return pile;
		// System.out.println("check "+p);
		for (Orientation ori : p.getPossibleOrientations()) {
			p.setOrientation(ori.getValue());
			pile = checkAndAddLeft2Right(p, grid, pile);
		}
		checkAndAddLeft2Right(p, grid, pile);
		return pile;
	}
	
	/**
	 * FIltering the pile algorithm 1
	 * @param grid
	 * @param currentpiece
	 * @return true if the piece is eligible
	 */
	public static boolean isEligible2PileLeft2Right(Grid grid,Piece currentpiece){
		Piece ln = grid.leftNeighbor(currentpiece);
		Piece tn = grid.topNeighbor(currentpiece);
		Piece rn = grid.rightNeighbor(currentpiece);
		Piece bn = grid.bottomNeighbor(currentpiece);
		if (currentpiece.hasLeftConnector() && ln != null && !ln.hasRightConnector())
			return false;
		if (currentpiece.hasTopConnector() && tn != null && !tn.hasBottomConnector())
			return false;
		if (currentpiece.hasRightConnector() && rn != null && !rn.hasLeftConnector() && rn.isFixed())
			return false;
		if (currentpiece.hasBottomConnector() && bn != null && !bn.hasTopConnector() && bn.isFixed())
			return false;

		if (!currentpiece.hasRightConnector() && rn != null && rn.hasLeftConnector() && rn.isFixed())
			return false;
		if (!currentpiece.hasLeftConnector() && ln != null && ln.hasRightConnector())
			return false;
		if (!currentpiece.hasTopConnector() && tn != null && tn.hasBottomConnector())
			return false;
		if (!currentpiece.hasBottomConnector() && bn != null && bn.hasTopConnector() && bn.isFixed())
			return false;

		if (currentpiece.hasRightConnector() && rn == null)
			return false;
		if (currentpiece.hasLeftConnector() && ln == null)
			return false;
		if (currentpiece.hasTopConnector() && tn == null)
			return false;
		if (currentpiece.hasBottomConnector() && bn == null)
			return false;
		
		return true;
	}
	
	/**
	 * insert the first piece with all the possibles orientations algorithm 2
	 * 
	 * @param grid
	 * 
	 * @return a stack
	 */
	public static ArrayDeque<Pair<Piece, Orientation>> createStackRight2Left(Grid grid) {
		ArrayDeque<Pair<Piece, Orientation>> pile = new ArrayDeque<>();
		Piece p = grid.getPiece(grid.getWidth()-1, grid.getHeight()-1); 
		while (p != null && (p.getType() == PieceType.VOID || p.isFixed())) {
			p = grid.getNextPieceInv(p);
		}
		if (p == null)
			return pile;
		// System.out.println("check "+p);
		for (Orientation ori : p.getPossibleOrientations()) {
			p.setOrientation(ori.getValue());
			pile = checkAndAddRight2Left(p, grid, pile);
		}
		checkAndAddRight2Left(p, grid, pile);
		return pile;
	}
	/**
	 * Filtering the possible orientations algorithm 2
	 * 
	 * @param nextPiece
	 * @param grid
	 * @param pile
	 * @return the updated stack
	 */
	public static ArrayDeque<Pair<Piece, Orientation>> checkAndAddRight2Left(Piece nextPiece, Grid grid,
			ArrayDeque<Pair<Piece, Orientation>> pile) {
		Pair<Piece, Orientation> p = new Pair<Piece, Orientation>(nextPiece, nextPiece.getOrientation());
		if (grid.hasNeighbour(nextPiece) && !pile.contains(p)) {
			
			if(isEligible2PileRight2Left(grid, nextPiece)){
				pile.push(p);
			}	
		}
		return pile;
	}
	
	/**
	 * Add the next piece possible positions to the stack algorithm 2
	 * 
	 * @param grid
	 * @param pile
	 * @param currentpiece
	 * @param lastPiece
	 *            the last piece not connected
	 * @return
	 * @throws IOException 
	 */
	public static ArrayDeque<Pair<Piece, Orientation>> addPiece2StackRight2Left(Grid grid,
			ArrayDeque<Pair<Piece, Orientation>> pile, Piece currentpiece) throws IOException {

		if(isEligible2PileRight2Left(grid, currentpiece)){
			Piece nextPiece = grid.getNextPieceInv(currentpiece);

			while (nextPiece != null && (nextPiece.getType() == PieceType.VOID || nextPiece.isFixed())) {
				nextPiece = grid.getNextPieceInv(nextPiece);
				if (nextPiece == null)
					break;
			}
			
			try{
				for (Orientation ori : nextPiece.getPossibleOrientations()) {
					nextPiece.setOrientation(ori.getValue());
					pile = checkAndAddRight2Left(nextPiece, grid, pile);
				}
			}catch(NullPointerException e){
				throw new IOException("Findbugs was right");
			}
		}
		return pile;
	}
	
	/**
	 * FIltering the pile
	 * @param grid
	 * @param currentpiece
	 * @return true if the piece is eligible
	 */
	public static boolean isEligible2PileRight2Left(Grid grid,Piece currentpiece){
		Piece ln = grid.leftNeighbor(currentpiece);
		Piece tn = grid.topNeighbor(currentpiece);
		Piece rn = grid.rightNeighbor(currentpiece);
		Piece bn = grid.bottomNeighbor(currentpiece);
		if (currentpiece.hasLeftConnector() && ln != null && !ln.hasRightConnector() && ln.isFixed())	return false;
		if (currentpiece.hasTopConnector() && tn != null && !tn.hasBottomConnector() && tn.isFixed())	return false;
		if (currentpiece.hasRightConnector() && rn != null && !rn.hasLeftConnector() )	return false;
		if (currentpiece.hasBottomConnector() && bn != null && !bn.hasTopConnector() )	return false;
		if (!currentpiece.hasRightConnector() && rn != null && rn.hasLeftConnector() )	return false;
		if (!currentpiece.hasLeftConnector() && ln != null && ln.hasRightConnector() && ln.isFixed())	return false;
		if (!currentpiece.hasTopConnector() && tn != null && tn.hasBottomConnector()  && tn.isFixed())	return false;
		if (!currentpiece.hasBottomConnector() && bn != null && bn.hasTopConnector() ) return false;
		if (currentpiece.hasRightConnector() && rn == null)return false;
		if (currentpiece.hasLeftConnector() && ln == null)	return false;
		if (currentpiece.hasTopConnector() && tn == null)	return false;
		if (currentpiece.hasBottomConnector() && bn == null)	return false;
		
		return true;
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
		ArrayDeque<Piece> pileOfPiece2fix = new ArrayDeque<Piece>();

		if (!p.isFixed()) {
			if (p.getType() == PieceType.VOID || p.getType() == PieceType.FOURCONN) {
				p.setFixed(true);
				// System.out.println("fix : void / fourconn : "+ p);
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
							|| (ln == null && p.hasLeftConnector()) || (tn == null && p.hasTopConnector())
							|| (rn == null && p.hasRightConnector()) || (bn == null && p.hasBottomConnector())) {
						// System.out.println("delete : "+p+" orientation :
						// "+p.getOrientation());
						p.deleteFromPossibleOrientation(p.getOrientation());
					}
					p.turn();
				}

			}
			if (p.getPossibleOrientations().size() != 0)
				p.setOrientation(p.getPossibleOrientations().get(0).getValue());
		}
		if (p.getPossibleOrientations().size() == 1) {
			// System.out.println("Size == 1 " + p);
			p.setOrientation(p.getPossibleOrientations().get(0).getValue());
			p.setFixed(true);

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
	public static void fixNeighboursOnGrid(ArrayDeque<Piece> pileOfPiece2fix, Grid grid) {
		while (!pileOfPiece2fix.isEmpty()) {
			Piece p = pileOfPiece2fix.pop();
			if (!p.isFixed()) {

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
							|| (ln == null && p.hasLeftConnector()) || (tn == null && p.hasTopConnector())
							|| (rn == null && p.hasRightConnector()) || (bn == null && p.hasBottomConnector())) {
						// System.out.println("2passes delete : "+p+"
						// orientation : "+p.getOrientation());
						p.deleteFromPossibleOrientation(p.getOrientation());
					}
					p.turn();
				}

			}
			if (p.getPossibleOrientations().size() != 0)
				p.setOrientation(p.getPossibleOrientations().get(0).getValue());
			if (p.getPossibleOrientations().size() == 1) {
				// System.out.println("2eme passeSize == 1 " + p);
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
	 * Solver V2 Naive, recursive
	 * 
	 * @param grid
	 * @param posX,
	 *            posY (initially 0 & 0 because of naive)
	 * @return true if the grid is resolved
	 */
	public static boolean naiveRecursiveSolver(int posX, int posY, Grid grid) {
		Piece p;

		if (Checker.isSolution(grid)) {
			// GUITEST.startGUITEST(grid);
			return true; // check if the grid is already solution
		}
		if (!grid.allPieceHaveNeighbour())
			return false; // check if there is a piece with no neighbor

		if (posX == 0 && posY == 0) {
			p = grid.getPiece(posX, posY);
			int orientationsNumber = switchOrientations(p.getType().getValue());
			for (int i = 0; i < orientationsNumber; i++) {
				p.setOrientation(i);
				if (Checker.isSolution(grid)) {
					// System.out.println(grid);
					return true;
				}
			}
			// System.out.println(grid);
			//System.out.println(Checker.isSolution(grid));
			// sGUITEST.startGUITEST(grid);
			return false;
		} else {
			if (posY == 0) {
				p = grid.getPiece(posX, posY);
				int orientationsNumber = switchOrientations(p.getType().getValue());
				for (int i = 0; i < orientationsNumber; i++) {
					// System.out.println("piece avant 1:"+grid.getPiece(posX,
					// posY));
					p.setOrientation(i);
					// System.out.println("piece apres 1:"+grid.getPiece(posX,
					// posY));
					// System.out.println("valide :
					// "+grid.isValidOrientation(posX, posY));
					if (grid.isValidOrientation(posX, posY)) {
						// System.out.println(grid);
						if (naiveRecursiveSolver(posX - 1, grid.getWidth() - 1, grid))
							return true;
					}
					// System.out.println(grid);
				}
				return false;
			}

			else {
				p = grid.getPiece(posX, posY);
				int orientationsNumber = switchOrientations(p.getType().getValue());
				for (int i = 0; i < orientationsNumber; i++) {
					// System.out.println("piece avant 2:"+grid.getPiece(posX,
					// posY));
					p.setOrientation(i);
					// System.out.println("piece apres 2:"+grid.getPiece(posX,
					// posY));
					// System.out.println("valide :
					// "+grid.isValidOrientation(posX, posY));
					if (grid.isValidOrientation(posX, posY)) {
						// System.out.println(grid);
						if (naiveRecursiveSolver(posX, posY - 1, grid))
							return true;
					}
					// System.out.println(grid);
				}
				return false;
			}
		}
	}

	/**
	 * number of possible orientations for a piece type
	 * 
	 * @param value
	 *            of the piece type
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
