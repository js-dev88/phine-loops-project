package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {

	@Test
	public void turnTest() {
		Piece p = new Piece(0,0,1,0);
		p.turn();
		assertSame(Orientation.EAST, p.getOrientation());
	}

}
