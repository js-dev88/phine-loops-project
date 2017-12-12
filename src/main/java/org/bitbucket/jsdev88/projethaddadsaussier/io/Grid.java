package org.bitbucket.jsdev88.projethaddadsaussier.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;


public class Grid {
	private Integer width;
	private Integer height;
	private Integer nbcc;
	private Piece[][] pieces;
	
	public Grid(Integer width, Integer height){
		this.width = width;
		this.height = height;
		pieces = new Piece[width][height];
	}
	
	//Consctructor with specified number of connected component 
	public Grid(Integer width, Integer height, Integer nbcc){
		this.width = width;
		this.height = height;
		this.nbcc = nbcc;
		pieces = new Piece[width][height];
	}
	
	
	/**
	 * @param output file name 
	 * @throws IOException - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 */
	private void generateLevel(String fileName) throws IOException{
		FileOutputStream file = null;
		
		//Generate a solution
		generateSolution();
		//Then we have to mix the solution to generate a level that is solvable 
		
		//Then we write the level on a file
		try {
			file = new FileOutputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			/* if the file exists but is a directory rather than a regular file, 
			   does not exist but cannot be created, 
			   or cannot be opened for any other reason */
			e.printStackTrace();
		}
		
		file.write(this.width);
		file.write(this.height);
	
		
		file.close();
			
	}
	
	private void generateSolution(){
		if(nbcc != null){
			//Generate a solution with a specific number of connected component
			for(int i = 0 ; i < this.width ; i++){
				for(int j = 0 ; j < this.height ; j++){
					//For each case, we choose a random piece (could be no piece)
					//We have to look to the position of the neighboor pieces
				}
			}
		}
		else{
			//Genrate a solution with a random number of connected component
			for(int i = 0 ; i < this.width ; i++){
				for(int j = 0 ; j < this.height ; j++){
					//For each case, we choose a random piece (could be no piece)
					//We have to look to the position of the neighboor pieces
				}
			}
		}
	}
	
	
}
