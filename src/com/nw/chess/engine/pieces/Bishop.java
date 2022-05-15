package com.nw.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.BoardUtils;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.board.Move.*;

public class Bishop extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-7, -9, 7, 9 };

	public Bishop(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.BISHOP, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for (final int offset : CANDIDATE_MOVE_COORDINATES) {
			int destinationCoord = this.getPiecePosition();
			while (BoardUtils.isValidTileCoordinate(destinationCoord)) {
				if (isFirstColumnExclusion(offset, destinationCoord) ||
	                    isEighthColumnExclusion(offset, destinationCoord)) {
	                    break;
	            }
				destinationCoord += offset;
				if (BoardUtils.isValidTileCoordinate(destinationCoord)) {
					final Piece pieceAtDestination = board.getPiece(destinationCoord);
					if (pieceAtDestination == null) {
						legalMoves.add(new MajorMove(board, this, destinationCoord));
					}else {
						final Alliance destinationPieceAlliance = pieceAtDestination.getPieceAlliance();
						if(this.getPieceAlliance() != destinationPieceAlliance) {
							legalMoves.add(new MajorAttackMove(board, this, destinationCoord, pieceAtDestination));
						}
						break;
					}
				}
			}
		}
		
		return legalMoves;
	}
	
	private boolean isEighthColumnExclusion(final int offset,
			                                final int piecePosition) {
        return BoardUtils.EIGHTH_COLUMN.get(piecePosition) &&
                (offset == -7 ||  offset == 9);
    }
	
	private boolean isFirstColumnExclusion(final int offset,
			                               final int piecePosition) {
		return BoardUtils.FIRST_COLUMN.get(piecePosition) &&
				(offset == -9 || offset == 7);
	}

	@Override
	public String toString() {
		return this.pieceType.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
}
