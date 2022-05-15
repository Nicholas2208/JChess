package com.chess.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterables;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.Board.Builder;
import com.nw.chess.engine.board.BoardUtils;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.board.Move.MoveFactory;
import com.nw.chess.engine.board.MoveTransition;
import com.nw.chess.engine.pieces.Piece;

public class TestBoard {

//	@Test
//	public void initialBoard() {
//		final Board board = Board.createStandardBoard();
//		System.out.println(board);
//		assertEquals(board.currentPlayer().getLegalMoves().size(), 20);
//		assertEquals(board.currentPlayer().getOpponent().getLegalMoves().size(), 20);
//		assertFalse(board.currentPlayer().isInCheck());
//		assertFalse(board.currentPlayer().isInCheckMate());
//		
//		assertEquals(board.currentPlayer(), board.whitePlayer());
//        assertEquals(board.currentPlayer().getOpponent(), board.blackPlayer());
//        assertFalse(board.currentPlayer().getOpponent().isInCheck());
//        assertFalse(board.currentPlayer().getOpponent().isInCheckMate());
//        
//        assertTrue(board.whitePlayer().toString().equals("White"));
//        assertTrue(board.blackPlayer().toString().equals("Black"));
//		
//		final Iterable<Piece> allPieces = board.getAllPieces();
//
//		final Iterable<Move> allMoves = Iterables.concat(board.whitePlayer().getLegalMoves(), board.blackPlayer().getLegalMoves());
//		for(final Move move : allMoves) {
//            assertFalse(move.isAttack());
//		}
//		
//		assertEquals(Iterables.size(allMoves), 40);
//		assertEquals(Iterables.size(allPieces), 32);
//		assertEquals(board.getPiece(35), null);
//		
//	}
	
	@Test
    public void testPlainKingMove() {
		final Board board = Board.createStandardBoard();
		
		final Move move = MoveFactory.createMove(board,
				                                 BoardUtils.getCoordinateAtPosition("e1"),
                                                 BoardUtils.getCoordinateAtPosition("f1"));
		final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
		
		assertEquals(moveTransition.getTransitionMove(), move);
		assertEquals(moveTransition.getFromBoard(), board);
        //assertEquals(moveTransition.getToBoard().currentPlayer(), moveTransition.getToBoard().blackPlayer());
	}

}
