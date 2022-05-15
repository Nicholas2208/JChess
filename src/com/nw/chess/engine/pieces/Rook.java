package com.nw.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.BoardUtils;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.board.Move.MajorAttackMove;
import com.nw.chess.engine.board.Move.MajorMove;

public class Rook extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-8, 1, 8, 1 };
	
	public Rook(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.ROOK, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : CANDIDATE_MOVE_COORDINATES) {
        	int destinationCoord = this.getPiecePosition();
        	while (BoardUtils.isValidTileCoordinate(destinationCoord)) {
        		if(isColumnExclusion(this.getPiecePosition(), offset)) {
            		break;
            	}
        		destinationCoord += offset;
        		if (BoardUtils.isValidTileCoordinate(destinationCoord)) {
        			final Piece pieceAtDestination = board.getPiece(destinationCoord);
                	if(pieceAtDestination == null) {
                		legalMoves.add(new MajorMove(board, this, destinationCoord));
                	}else {
                		final Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
                		if(this.getPieceAlliance() != pieceAtDestinationAlliance) {
                			legalMoves.add(new MajorAttackMove(board, this, destinationCoord, pieceAtDestination));
                		}
                		break;
                	}
                }
        	}
        }
		return legalMoves;
	}

	private boolean isColumnExclusion(final int piecePosition, final int offset) {
		return (BoardUtils.FIRST_COLUMN.get(piecePosition) && offset == -1) ||
				(BoardUtils.EIGHTH_COLUMN.get(piecePosition) && offset == 1);
	}

	@Override
	public String toString() {
		return this.pieceType.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
}
