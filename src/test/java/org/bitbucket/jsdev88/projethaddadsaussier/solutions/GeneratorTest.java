package org.bitbucket.jsdev88.projethaddadsaussier.solutions;

import static org.junit.Assert.*;

import org.bitbucket.jsdev88.projethaddadsaussier.io.Grid;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.Piece;
import org.bitbucket.jsdev88.projethaddadsaussier.utils.PieceType;
import org.junit.Test;

public class GeneratorTest {

	@Test
	public void testaffectPiece2Corner() {
		Grid grid = new Grid(4, 3);
		Piece p = new Piece(0, 0);
		Generator.affectPiece2Corner(0, 0, p, grid);
		assertTrue(grid.getPiece(0, 0).getType() == PieceType.VOID || grid.getPiece(0, 0).getType() == PieceType.ONECONN
				|| grid.getPiece(0, 0).getType() == PieceType.LTYPE);
	}

}
