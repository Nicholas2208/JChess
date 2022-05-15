package com.nw.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.BoardUtils;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.board.Move.*;

public class Knight extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17 };
	
	public Knight(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.KNIGHT, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : CANDIDATE_MOVE_COORDINATES) {
        	if(isFirstColumnExclusion(this.getPiecePosition(), offset) ||
					   isSecondColumnExclusion(this.getPiecePosition(), offset) ||
		               isSeventhColumnExclusion(this.getPiecePosition(), offset) ||
		               isEighthColumnExclusion(this.getPiecePosition(), offset)){
				continue;
			}
        	final int destinationCoord = this.getPiecePosition() + offset;
        	if(BoardUtils.isValidTileCoordinate(destinationCoord)) {
        		final Piece pieceAtDestination = board.getPiece(destinationCoord);
        		if (pieceAtDestination == null) {
        			legalMoves.add(new MajorMove(board, this, destinationCoord));
        		}else {
        			final Alliance pieceAtDestinationAllegiance = pieceAtDestination.getPieceAlliance();
					if(this.getPieceAlliance() != pieceAtDestinationAllegiance) {
						legalMoves.add(new MajorAttackMove(board, this, destinationCoord, pieceAtDestination));
					}
        		}
        	}
        }
		
		return legalMoves;
	}

	private boolean isEighthColumnExclusion(final int piecePosition, 
			                                final int offset) {
		return BoardUtils.EIGHTH_COLUMN.get(piecePosition) &&
				(offset == -6 ||  offset == -15 || offset == 10 || offset == 17);
	}

	private boolean isSeventhColumnExclusion(final int piecePosition, 
			                                 final int offset) {
		return BoardUtils.SEVENTH_COLUMN.get(piecePosition) &&
				                  (offset == -6 || offset == 10);
	}

	private boolean isSecondColumnExclusion(final int piecePosition, 
			                                final int offset) {
		return BoardUtils.SECOND_COLUMN.get(piecePosition) &&
				                    (offset == -10 || offset == 6);
	}

	private boolean isFirstColumnExclusion(final int piecePosition,
			                               final int offset) {
		return BoardUtils.FIRST_COLUMN.get(piecePosition) &&
				(offset == -17 || offset == -10 || offset == 6 ||  offset == 15);
	}

	@Override
	public String toString() {
		return this.pieceType.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
}
