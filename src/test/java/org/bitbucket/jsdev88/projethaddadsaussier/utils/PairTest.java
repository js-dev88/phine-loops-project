package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class PairTest {

	@Test
	public void equalsTest() {
		Piece p = new Piece(0, 0, PieceType.FOURCONN, Orientation.NORTH);
		Pair<Piece, Orientation> pair1 = new Pair<>(p, Orientation.SOUTH);
		Pair<Piece, Orientation> pair2 = new Pair<>(p, Orientation.SOUTH);
		assertTrue(pair1.equals(pair2));
	}

}
