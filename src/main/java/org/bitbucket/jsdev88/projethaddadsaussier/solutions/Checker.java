package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;


public class Checker {
	
	/**
	 * Take a file in input and return the corresponding Grid
	 * @param inputFile String from the command line
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Grid buildGrid(String inputFile) throws IOException{

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "utf-8"))) {
			
			 int width = Integer.parseInt(reader.readLine());//read the grid width
			 int height = Integer.parseInt(reader.readLine());//read the grid height
			 
			 Grid grid = new Grid(width, height);
			 int typePiece;
			 int orientation;
			 
			 for(int i =0; i < height; i++){//populate the grid
				 for(int j = 0; j< width; j++){
					 String[] tab = reader.readLine().trim().split("\\s+");
					 typePiece = Integer.valueOf(tab[0]);
					 orientation = Integer.valueOf(tab[1]);;
					 try{
						 grid.setPiece(i, j, new Piece(i,j,typePiece, orientation));
					 }catch(NullPointerException e){
						 throw new IOException("File is not in the correct format");
					 }
				 }
			 }

			 return grid;
			
		}catch(NumberFormatException e){
			throw new IOException("File is not in the correct format");
		}
	}

	/**
	 * Check if a grid is Solution
	 * @param inputFile String from command line
	 * @return true if al pieces are connected
	 * @throws IOException
	 */
	public static boolean isSolution(String inputFile)throws IOException{
		Grid grid2Test = buildGrid(inputFile);
		for(Piece[] linep : grid2Test.getAllPieces()){
			for(Piece p : linep){
				if(!grid2Test.isTotallyConnected(p)) return false;
			}
		}
		return true;
	}
	
	public static boolean isSolution(Grid grid2Test){
		for(Piece[] linep : grid2Test.getAllPieces()){
			for(Piece p : linep){
				if(!grid2Test.isTotallyConnected(p)) return false;
			}
		}
		return true;
	}
	
}
