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

public class Queen extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-7, -8, -9, -1, 1, 7, 8, 9 };
	
	public Queen(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.QUEEN, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : CANDIDATE_MOVE_COORDINATES) {
        	int destinationCoord = this.getPiecePosition();
        	while (true) {
        		if(isFirstColumnExclusion(this.getPiecePosition(), offset) ||
            			isEighthColumnExclusion(this.getPiecePosition(), offset)) {
            		break;
            	}
        		destinationCoord += offset;
        		if (!BoardUtils.isValidTileCoordinate(destinationCoord)) {
                    break;
                }else {
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

	private boolean isEighthColumnExclusion(final int piecePosition, final int offset) {
		return BoardUtils.EIGHTH_COLUMN.get(piecePosition) &&
				(offset == 1 || offset == 9 || offset == -7);
	}

	private boolean isFirstColumnExclusion(final int piecePosition, final int offset) {
		return BoardUtils.FIRST_COLUMN.get(piecePosition) &&
				(offset == -9 || offset == -1 || offset == 7);
	}

	@Override
	public String toString() {
		return this.pieceType.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
}
