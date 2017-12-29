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
	private static int lineCheck = 0;
	private static Piece pieceNotConnected;
	public static boolean solveGrid(String inputFile, String ouputFile, String pieceChoiceMethod) throws IOException{
		boolean rez = false;
		Grid grid = Checker.buildGrid(inputFile); //load the file and build the grid
		rez = solveIT(grid,pieceChoiceMethod); //solve
		Generator.writeGridOnFile(ouputFile, grid); //write the file
		return rez; //return the result
	}
	
	public static boolean solveIT(Grid grid, String pieceChoiceMethod){
		Objects.requireNonNull(grid);
		
		if (Checker.isSolution(grid)) return true; //check if the grid is already solution
		if(!grid.allPieceHaveNeighbour()) return false; //=> à améliorer /  check if there is a piece with no neighbor
		Stack<Pair<Piece, Orientation>> pile;
		switch(pieceChoiceMethod){
		case "0" : pile = createStackLeft2Right(grid);// create the stack with the first piece (0,0)
			break;
		default :  pile = createStackLeft2Right(grid);
			break;
		}
			while (!pile.isEmpty()) {
				  Pair<Piece, Orientation> currentPiece = pile.pop(); //on depile (LIFO)
				  currentPiece.getKey().setOrientation(currentPiece.getValue().getValue()); //set the orientation of the pair to the piece
				  grid.setPiece(currentPiece.getKey().getPosY(), currentPiece.getKey().getPosX(),currentPiece.getKey()); //modify the grid with inserting the piece
			      
				  
			      if (Checker.isSolution(grid)) {
			    	  /*DEBUG*/
			    	  //System.out.println( pile.toString());
				      //System.out.println( grid.toString());
			        return true; 
			        
			      }
			      //if the grid is not solution,we had the next piece possible orientations on the stack
			      switch(pieceChoiceMethod){
			      
					case "0" :  if(currentPiece.getKey().getPosY() == lineCheck+2){
									if(lineIsConnected(lineCheck,grid)){
										lineCheck++;
										addPiece2StackLeft2Right(grid, pile, currentPiece.getKey());
									}else{
										Pair<Piece, Orientation> pairNotConnected;
										do{
											pairNotConnected = pile.pop();
										}while(pairNotConnected.getKey() != pieceNotConnected);

										addPiece2StackLeft2Right(grid, pile, pairNotConnected.getKey());
									}
								}else{
									addPiece2StackLeft2Right(grid, pile, currentPiece.getKey());// create the stack with the first piece (0,0)
								}
								
						break;
					default :    addPiece2StackLeft2Right(grid, pile, currentPiece.getKey());
						break;
					}
			     
			    
			      //if there is a null value, so the stack contains a piece witch is not possible to connect
			     /* if(pile.contains(null)){
			    	  return false;
			      }*/
			      /*DEBUG*/
			     //System.out.println( pile.toString());
			     // System.out.println( grid.toString());	   
			  }
			 
			return false;
			
		}
		 
	
	
	public static Stack<Pair<Piece, Orientation>> addPiece2StackLeft2Right(Grid grid,Stack<Pair<Piece, Orientation>> pile, Piece currentpiece){
			        Piece nextPiece = grid.getNextPiece(currentpiece);
			       
			        while(nextPiece != null && nextPiece.getType() == PieceType.VOID){
			        	nextPiece =  grid.getNextPiece(nextPiece);
			        }
			        if(nextPiece != null){
			        	
				         for(Orientation ori : nextPiece.getType().getListOfPossibleOri()){
				        	 nextPiece.setOrientation(ori.getValue());
				        	 //check if it is a possible orientation
				        	 if(grid.hasNeighbour(nextPiece)){
				        		//check can connect to the left piece & upper piece
				        		 if(currentpiece.hasRightConnector()){
				        			
				        			 if(nextPiece.getPosY() != 0 && 
				        					 grid.getPiece(nextPiece.getPosY()-1, nextPiece.getPosX()).hasBottomConnector()){
				        				 if(nextPiece.getConnectors().contains(Orientation.WEST ) && 
				        						 nextPiece.getConnectors().contains(Orientation.NORTH)){
					        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
					        			 }
				        			 }else{
				        				 if(nextPiece.getConnectors().contains(Orientation.WEST)  && !nextPiece.getConnectors().contains(Orientation.NORTH)){
					        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
					        			 }
				        			 }
				        		 }else if(nextPiece.getPosY() != 0 && 
				        				 grid.getPiece(nextPiece.getPosY()-1, nextPiece.getPosX()).hasBottomConnector()){
				        			 if(nextPiece.getConnectors().contains(Orientation.NORTH) && !nextPiece.getConnectors().contains(Orientation.WEST)){
				        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
				        			 }
				        		 }else{
				        			 if(!nextPiece.getConnectors().contains(Orientation.NORTH) && !nextPiece.getConnectors().contains(Orientation.WEST)){
				        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
				        			 }
				        			
				        		 }
				        		
				        	 }
				         }
				        
			        }
		return pile;
		
	}
	
	
	/**
	 * insert the first piece with all the possibles orientations
	 * @param grid
	 * @param pieceChoiceMethod default 0
	 * @return a stack 
	 */
	public static Stack<Pair<Piece, Orientation>> createStackLeft2Right(Grid grid){
		Stack<Pair<Piece, Orientation>> pile = new Stack<>();
		Piece p = grid.getPiece(0, 0); // choix de gauche à droite
		         while(p.getType() == PieceType.VOID){ //first piece witch is not a void piece
		        	 p =  grid.getNextPiece(p);
		         }
		         for(Orientation ori : p.getType().getListOfPossibleOri()){
		        	 p.setOrientation(ori.getValue());
		        	 //if it's possible to connect the piece
		        	 if(grid.hasNeighbour(p)){
		        		 pile.push(new Pair<Piece,Orientation>(p,ori));
		        	 }
		         }
		return pile;
		
	}
	
	public static boolean lineIsConnected(int line, Grid grid){
		Piece[][] pieces  = grid.getAllPieces();
			for(Piece p : pieces[lineCheck]){
				if(!grid.isTotallyConnected(p)){
					pieceNotConnected = p;
					return false;
				}
			}
		
		return true;
		
	}
	public static void main(String[] args) {
		try {
			System.out.println(solveGrid("NotSolution.txt","Solved.txt","0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
