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

	public static boolean solveGrid(String inputFile, String ouputFile, String pieceChoiceMethod) throws IOException{
		boolean rez = false;
		Grid grid = Checker.buildGrid(inputFile); //load the file and build the grid
		rez = solveIT(grid,pieceChoiceMethod); //solve
		Generator.writeGridOnFile(ouputFile, grid); //write the file
		return rez; //return the result
	}
	
	public static boolean solveIT(Grid grid, String pieceChoiceMethod){
		Objects.requireNonNull(grid);
		
		if (Checker.isSolution(grid)==null) return true; //check if the grid is already solution
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
			      
				  Piece lastPiece = Checker.isSolution(grid);
			      if (lastPiece == null) {
			    	  /*DEBUG*/
			    	  //System.out.println( pile.toString());
				      //System.out.println( grid.toString());
			        return true; 
			        
			      }
			      //if the grid is not solution,we had the next piece possible orientations on the stack
			      switch(pieceChoiceMethod){
			      
					case "0" :  /*if(currentPiece.getKey().getPosY() == lineCheck+2){
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
								}*/
								pile = addPiece2StackLeft2Right(grid, pile, currentPiece.getKey(), lastPiece);
								
						break;
					default :    pile =addPiece2StackLeft2Right(grid, pile, currentPiece.getKey(), lastPiece);
						break;
					}
			     
			    
			      //if there is a null value, so the stack contains a piece witch is not possible to connect
			     /* if(pile.contains(null)){
			    	  return false;
			      }*/
			      /*DEBUG*/
			      /*System.out.println( pile.toString());
			      System.out.println( grid.toString());	   */
			  }
			 
			return false;
			
		}
		 
	
	
	public static Stack<Pair<Piece, Orientation>> addPiece2StackLeft2Right(Grid grid,Stack<Pair<Piece, Orientation>> pile, Piece currentpiece, Piece lastPiece){
			
		     if(!(lastPiece.getPosY() < currentpiece.getPosY()) || !(lastPiece.getPosX() < currentpiece.getPosX())){
			 Piece nextPiece = grid.getNextPiece(currentpiece);
		        while(nextPiece != null && (nextPiece.getType() == PieceType.VOID || nextPiece.isFixed())){
		        	nextPiece =  grid.getNextPiece(nextPiece);
		        }
		        if(nextPiece != null){
		        	pile = checkAndAdd(nextPiece,grid,pile);
		        }
		     }else{
		    	 Pair<Piece, Orientation> forty_two = pile.peek();
		    	// System.out.println("here");
		    	 while((forty_two.getKey().getPosY() > lastPiece.getPosY() +1 )&&  forty_two.getKey().getPosX()> lastPiece.getPosX()+1){
		    		 
		    		 forty_two = pile.pop();
		    		 /*System.out.println(lastPiece);
		    		 System.out.println(forty_two.toString());*/
		    	 }
		     }
		
	    		          
		return pile;
		
	}
	
	public static Stack<Pair<Piece, Orientation>> checkAndAdd(Piece nextPiece, Grid grid, Stack<Pair<Piece, Orientation>> pile){
		for(Orientation ori : nextPiece.getType().getListOfPossibleOri()){
       	 nextPiece.setOrientation(ori.getValue());
       	 //check if it is a possible orientation
       	 if(grid.hasNeighbour(nextPiece)){
       		//check can connect to the left piece & upper piece
       		 Piece ln = grid.leftNeighbor(nextPiece);
       		 Piece tn = grid.topNeighbor(nextPiece);
       		 Piece rn = grid.rightNeighbor(nextPiece);
       		 Piece bn = grid.bottomNeighbor(nextPiece);
       		 
       			 if(ln != null && ln.hasRightConnector()){
		        			
	        			 if(tn != null && tn.hasBottomConnector()){
	        				 if(nextPiece.getConnectors().contains(Orientation.WEST ) && 
	        						 nextPiece.getConnectors().contains(Orientation.NORTH)){
		        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
		        			 }
	        			 }else{
	        				 if(nextPiece.getConnectors().contains(Orientation.WEST)  && !nextPiece.getConnectors().contains(Orientation.NORTH)){
		        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
		        			 }
	        			 }
	        		 }else if(tn != null && tn.hasBottomConnector()){
	        			 if(nextPiece.getConnectors().contains(Orientation.NORTH) && !nextPiece.getConnectors().contains(Orientation.WEST)){
	        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
	        			 }
	        		 }else{
	        			if(rn != null && rn.hasLeftConnector()&& rn.isFixed()){
	        				 if(!nextPiece.getConnectors().contains(Orientation.NORTH) &&
	        						 !nextPiece.getConnectors().contains(Orientation.WEST)&&
	        						 nextPiece.getConnectors().contains(Orientation.EAST)){
		        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
		        			 }
	        			 }else if(bn != null && bn.hasTopConnector() && bn.isFixed()){
	        				 if(!nextPiece.getConnectors().contains(Orientation.NORTH) &&
	        						 !nextPiece.getConnectors().contains(Orientation.WEST)&&
	        						 nextPiece.getConnectors().contains(Orientation.SOUTH)){
		        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
		        			 }
	        			
	        		 }else{
	        				 if(!nextPiece.getConnectors().contains(Orientation.NORTH) &&
	        			
	        					 !nextPiece.getConnectors().contains(Orientation.WEST))
	        				 pile.push(new Pair<Piece,Orientation>(nextPiece,ori));
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
		fixPieceOnGrid(grid);
		
		Stack<Pair<Piece, Orientation>> pile = new Stack<>();
		Piece p = grid.getPiece(0, 0); // choix de gauche à droite
		         while(p != null && (p.getType() == PieceType.VOID || p.isFixed())){ //first piece witch is not a void piece
		        	 p =  grid.getNextPiece(p);
		         }
		         checkAndAdd(p,grid,pile);
		return pile;
		
	}
	
	/*public static boolean lineIsConnected(int line, Grid grid){
		Piece[][] pieces  = grid.getAllPieces();
			for(Piece p : pieces[lineCheck]){
				if(!grid.isTotallyConnected(p)){
					pieceNotConnected = p;
					return false;
				}
			}
		
		return true;
		
	}*/
	public static void main(String[] args) {
		try {
			System.out.println(solveGrid("NotSolution.txt","Solved.txt","0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void fixPieceOnGrid(Grid grid){
		for(int i = 0; i < grid.getHeight(); i++){
			for(int j=0; j < grid.getWidth(); j++){
				fixAparticularPiece(grid,grid.getPiece(i, j));
				
			}
		}
	}
	
	public static void fixAparticularPiece(Grid grid,Piece p){
		Stack<Piece> pileOfPiece2fix = new Stack<Piece>();
			if(!p.isFixed() && p.getType().getNbConnectors() == grid.numberOfNeibours(p)
					&& p != null && p.getType() != PieceType.VOID){
				while(!grid.hasNeighbour(p)){
					p.turn();
					
				}
				p.setFixed(true);
				//System.out.println(p);
				for(Piece p2 : grid.listOfNeighbours(p)){
					if(!pileOfPiece2fix.contains(p2)) pileOfPiece2fix.addAll(0, grid.listOfNeighbours(p));
				}
			}
			fixNeighboursOnGrid( pileOfPiece2fix,grid);
	}
	
	public static void fixNeighboursOnGrid(Stack<Piece> pileOfPiece2fix, Grid grid){
		while(!pileOfPiece2fix.isEmpty()){
			Piece p = pileOfPiece2fix.pop();
			if(!p.isFixed() && p.getType().getNbConnectors() == grid.numberOfFixedNeibours(p)){
				int i=0;
				while(!grid.hasFixedNeighbour(p) && i < Orientation.values().length){
					p.turn();
					i++;
				}
				if(grid.hasFixedNeighbour(p)){
					p.setFixed(true);
					//System.out.println("Add :"+(p));
					for(Piece p2 : grid.listOfNeighbours(p)){
						if(!pileOfPiece2fix.contains(p2)) pileOfPiece2fix.addAll(0, grid.listOfNeighbours(p));
					}
				}
				
			}
			
		}
	}
	
	
}
