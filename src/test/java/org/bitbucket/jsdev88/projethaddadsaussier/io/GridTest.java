package org.bitbucket.jsdev88.projethaddadsaussier.io;

import static org.junit.Assert.*;

import org.junit.Test;


public class GridTest {

	Grid grid = new Grid(4, 3);

	@Test
	public void testGetWidth() {
		assertTrue(grid.getWidth() == 4);
	}

	@Test
	public void testGetheight() {
		assertTrue(grid.getHeight() == 3);
	}

	@Test
	public void testisBorderLine() {
		assertTrue(grid.isBorderLine(0, 1) == true);
	}

}