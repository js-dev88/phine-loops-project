package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Pair;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;

public class Solver {
	public static boolean solveGrid(String inputFile, String ouputFile, String pieceChoiceMethod) throws IOException{
		boolean rez = false;
		Grid grid = Checker.buildGrid(inputFile);
		rez = solveIT(grid,pieceChoiceMethod);
		Generator.writeGridOnFile(ouputFile, grid);
		return rez;
	}
	
	public static boolean solveIT(Grid grid, String pieceChoiceMethod){
		Objects.requireNonNull(grid);
		Stack<Pair<Piece, Orientation>> pile = createStack(grid,pieceChoiceMethod);
		 while (!pile.isEmpty()) {
			  Pair<Piece, Orientation> currentPiece= pile.pop();
			  currentPiece.getKey().setOrientation(currentPiece.getValue().getValue());
			  grid.setPiece(currentPiece.getKey().getPosY(), currentPiece.getKey().getPosX(),currentPiece.getKey());
		      
		      if (Checker.isSolution(grid)) {
		    	  /*DEBUG*/
		    	  //System.out.println( pile.toString());
			      //System.out.println( grid.toString());
		        return true; 
		        
		      }
		      
		      addPiece2Stack(grid, pieceChoiceMethod, pile, currentPiece.getKey());
		      /*DEBUG*/
		      //System.out.println( pile.toString());
		      //System.out.println( grid.toString());	   
		  }
		 
		return false;
	}
	
	public static Stack<Pair<Piece, Orientation>> addPiece2Stack(Grid grid, String pieceChoiceMethod,Stack<Pair<Piece, Orientation>> pile, Piece currentpiece){
		switch(pieceChoiceMethod){
		//0 => choose next piece in the grid
		case "0":
			        Piece nextPiece = grid.getNextPiece(currentpiece);
					if(nextPiece != null){
				         for(Orientation ori : nextPiece.getType().getListOfPossibleOri()){
				        	 nextPiece.setOrientation(ori.getValue());
				        	 if(grid.hasNeighbour(nextPiece)){
				        		 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
				        	 }
				         }
					}
				
			    
			break;
		default:
			break;
		}
		return pile;
		
	}
	
	
	
	public static Stack<Pair<Piece, Orientation>> createStack(Grid grid, String pieceChoiceMethod){
		Stack<Pair<Piece, Orientation>> pile = new Stack<>();
		switch(pieceChoiceMethod){
		case "0":Piece p = grid.getPiece(0, 0);
		         for(Orientation ori : p.getType().getListOfPossibleOri()){
		        	 p.setOrientation(ori.getValue());
		        	 if(grid.hasNeighbour(p)){
		        		 pile.push(new Pair<Piece,Orientation>(p,ori));
		        	 }
		         }
			    
			break;
		default:
			break;
		}
		return pile;
		
	}
	public static void main(String[] args) {
		try {
			
			System.out.println(solveGrid("shuffledlvl.txt","Solved.txt","0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
