package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrientationTest {

	@Test
	public void testTurn90() {
		assertSame(Orientation.WEST, Orientation.SOUTH.turn90());
	}

	@Test
	public void testGetOpposedOrientation() {
		assertSame(Orientation.WEST, Orientation.EAST.getOpposedOrientation());
	}

}
