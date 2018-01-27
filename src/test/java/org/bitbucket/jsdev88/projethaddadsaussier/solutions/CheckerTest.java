package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class CheckerTest {

	@Test
	public void testIsSolution() throws IOException {
		assertTrue(Checker.isSolution(CheckerTest.class.getResource("Solution.txt").getPath()));

	}

	@Test
	public void testIsNotSolution() throws IOException {
		assertFalse(Checker.isSolution(CheckerTest.class.getResource("NotSolution.txt").getPath()));
	}

	@Test(expected = IOException.class)
	public void testFailWidth() throws IOException {
		assertFalse(Checker.isSolution(CheckerTest.class.getResource("corruptWidth.txt").getPath()));
	}

	@Test(expected = IOException.class)
	public void testFailHeight() throws IOException {
		assertFalse(Checker.isSolution(CheckerTest.class.getResource("corruptHeight.txt").getPath()));
	}

	@Test(expected = IOException.class)
	public void testFailPiece() throws IOException {
		assertFalse(Checker.isSolution(CheckerTest.class.getResource("corruptPiece.txt").getPath()));
	}

}
