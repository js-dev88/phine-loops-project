package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.*;

import java.util.Random;

import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;



public class Generator {
	
	/**
	 * @param output file name 
	 * @throws IOException - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 */
	public static void generateLevel(String fileName, Grid grid) throws IOException{

		//Generate a solution
		grid = generateSolution(grid);
		//Then we have to mix the solution to generate a level that is solvable 
		
		//Then we write the level on a file
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(fileName), "utf-8"))) {
			writer.write(String.valueOf(grid.getWidth()));
			writer.write(System.lineSeparator());
			writer.write(String.valueOf(grid.getHeight()));
			writer.write(System.lineSeparator());
			for(int i = 0 ; i < grid.getWidth() ; i++){
				for(int j = 0 ; j < grid.getHeight() ; j++){
					writer.write(grid.getPieces()[i][j].getType().ordinal()+" "+grid.getPieces()[i][j].getOrientation().ordinal());
					writer.write(System.lineSeparator());
				}
			}
		}
		
		
		    /*DEBUG*/
	        System.out.println(grid.toString());
		
			
	}
	
	public static Grid generateSolution(Grid grid){
		Random rdType = new Random();
		Random rdOrientation = new Random();
		for(int i = 0 ; i < grid.getWidth() ; i++){
			for(int j = 0 ; j < grid.getHeight() ; j++){
				grid.getPieces()[i][j] = new Piece(i,j,rdType.nextInt(6),rdOrientation.nextInt(4));
			}
		}
		/*if(grid.getNbcc() != null){
			//Generate a solution with a specific number of connected component
			for(int i = 0 ; i < grid.getWidth() ; i++){
				for(int j = 0 ; j < grid.getHeight() ; j++){
					//For each case, we choose a random piece (could be no piece)
					//We have to look to the position of the neighboor pieces
				}
			}
		}
		else{
			//Genrate a solution with a random number of connected component
			for(int i = 0 ; i < grid.getWidth() ; i++){
				for(int j = 0 ; j < grid.getHeight() ; j++){
					//For each case, we choose a random piece (could be no piece)
					//We have to look to the position of the neighboor pieces
				}
			}
		}*/
		shuffle(grid);
		return grid;
	}
	
	public static Grid shuffle(Grid grid){
		Random r = new Random();
		for(Piece[] i: grid.getPieces()){
			for(Piece j : i){
				j.setOrientation(r.nextInt(3));
			}
		}
		return grid;
	}
	public static void main(String[] args) {
		try {
			generateLevel("txt.txt", new Grid(8,8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
