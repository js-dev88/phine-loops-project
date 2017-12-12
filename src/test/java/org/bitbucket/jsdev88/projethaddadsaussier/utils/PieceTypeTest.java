package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTypeTest {

	@Test
	public void getNbConnectorsTest() {
		Piece p = new Piece(0,0,1,0);
		assertTrue(p.getType().getNbConnectors() == 1);
	}

}
