package org.bitbucket.jsdev88.projethaddadsaussier.io;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bitbucket.jsdev88.projethaddadsaussier.utils.Orientation;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;

/**
 * Singleton for building a correspondance table with type, orientation and
 * unicode piece The table is built only one time at the first call 
 * architecture source :
 * http://thecodersbreakfast.net/index.php?post/2008/02/25/26-de-la-bonne-implementation-du-singleton-en-java
 */
public class displayUnicode {
	private static HashMap<PieceType, HashMap<Orientation, String>> unicodeTable = new HashMap<PieceType, HashMap<Orientation, String>>();
	/**
	 * Private Constructor you can't build the table from outside
	 * 
	 */
	private displayUnicode() {
		buildTable();
	}

	/**
	 * Unique instance pre-initialized If not called, not built
	 */
	private static displayUnicode INSTANCE = null;

	/** Only one instance is built*/
	private static void instanciate() {
		if (INSTANCE == null) {
			INSTANCE = new displayUnicode();
		}
	}
	
	/**
	 * The table has the following type : pieceType  => orientation => unicode character
	 */
	private static void buildTable(){
		EnumSet<PieceType> pieceTypeSet = EnumSet.allOf(PieceType.class);
		for(PieceType pt : pieceTypeSet){
			unicodeTable.put(pt, new HashMap<Orientation,String>());
		}
		
		unicodeTable.get(PieceType.VOID).put(Orientation.NORTH," ");
		unicodeTable.get(PieceType.ONECONN).put(Orientation.NORTH,"\u2579");
		unicodeTable.get(PieceType.ONECONN).put(Orientation.EAST,"\u257A");
		unicodeTable.get(PieceType.ONECONN).put(Orientation.SOUTH,"\u257B");
		unicodeTable.get(PieceType.ONECONN).put(Orientation.WEST,"\u2578");
		unicodeTable.get(PieceType.BAR).put(Orientation.NORTH,"\u2502");
		unicodeTable.get(PieceType.BAR).put(Orientation.EAST,"\u2500");
		unicodeTable.get(PieceType.TTYPE).put(Orientation.NORTH,"\u2534");
		unicodeTable.get(PieceType.TTYPE).put(Orientation.EAST,"\u251C");
		unicodeTable.get(PieceType.TTYPE).put(Orientation.SOUTH,"\u252C");
		unicodeTable.get(PieceType.TTYPE).put(Orientation.WEST,"\u2524");
		unicodeTable.get(PieceType.FOURCONN).put(Orientation.NORTH,"\u253C");
		unicodeTable.get(PieceType.LTYPE).put(Orientation.NORTH,"\u2514");
		unicodeTable.get(PieceType.LTYPE).put(Orientation.EAST,"\u250C");
		unicodeTable.get(PieceType.LTYPE).put(Orientation.SOUTH,"\u2510");
		unicodeTable.get(PieceType.LTYPE).put(Orientation.WEST,"\u2518");
		
	}
	
	/**
	 * Access point
	 * @return a unicode character
	 */
	public static String getUnicodeOfPiece(PieceType pt, Orientation ori){
		instanciate();
		String unicode = unicodeTable.get(pt).get(ori);
		return unicode;
	}
	
	/**
	 * Main for testing purpose
	 * @param args
	 */
	public static void main(String[] args) {
		displayUnicode.buildTable();
		for(Entry<PieceType, HashMap<Orientation, String>> pt : unicodeTable.entrySet()){
			for(Entry<Orientation, String> o : pt.getValue().entrySet()){
				System.out.println("Piece : "+pt.getKey()+ " Orientation : "+o.getKey()+" Unicode : "+  o.getValue());
			}
		}
		System.out.println(displayUnicode.getUnicodeOfPiece(PieceType.ONECONN,Orientation.NORTH));
	}
	

}
