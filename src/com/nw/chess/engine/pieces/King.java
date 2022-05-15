package com.nw.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.BoardUtils;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.board.Move.*;

// King
public class King extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATES = {7, 9, -7, -9, 8, -8, 1, -1 };
	
	public King(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.KING, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : CANDIDATE_MOVE_COORDINATES) {
        		if(isFirstColumnExclusion(this.getPiecePosition(), offset) ||
        				isEighthColumnExclusion(this.getPiecePosition(), offset)) {
        			continue;
        		}
        		final int destinationCoord = this.getPiecePosition() + offset;
        		if(BoardUtils.isValidTileCoordinate(destinationCoord)) {
        		final Piece pieceAtDestination = board.getPiece(destinationCoord);
        		if(pieceAtDestination == null) {
        			legalMoves.add(new MajorMove(board, this, destinationCoord));
        		}else {
        			final Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
					 if (this.getPieceAlliance() != pieceAtDestinationAlliance) {
							legalMoves.add(new MajorAttackMove(board, this, destinationCoord, pieceAtDestination));
					  }
        		}
          }
        }
		return legalMoves;
	}
	
	private boolean isFirstColumnExclusion(final int piecePosition,
                                           final int offset) {
       return BoardUtils.FIRST_COLUMN.get(piecePosition) &&
             (offset == 7 || offset == -1 || offset == -9);
    }
	
	private boolean isEighthColumnExclusion(final int piecePosition, 
                                            final int offset) {
      return BoardUtils.EIGHTH_COLUMN.get(piecePosition) &&
             (offset == 9 ||  offset == 1 || offset == -7);
    }

	@Override
	public String toString() {
		return this.pieceType.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
}
