package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {

	@Test
	public void turnTest() {
		Piece p = new Piece(0,0,PieceType.getTypefromValue(1),Orientation.getOrifromValue(0));
		p.turn();
		assertSame(Orientation.EAST, p.getOrientation());
	}
	
	@Test
	public void getOrientationTest() {
		Piece p = new Piece(0,0,PieceType.VOID,Orientation.EAST);
		assertTrue(p.getOrientation() == Orientation.NORTH);
	}
	
	@Test
	public void getOrientationOrdinalTest() {
		Piece p = new Piece(0,0,PieceType.getTypefromValue(0),Orientation.getOrifromValue(3));
		assertTrue(p.getOrientation() == Orientation.NORTH);
	}

}
