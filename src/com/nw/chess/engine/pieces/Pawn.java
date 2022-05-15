package com.nw.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.BoardUtils;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.board.Move.*;

public class Pawn extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATES = {7, 8, 16, 9};
	
	public Pawn(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.PAWN, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int offset : CANDIDATE_MOVE_COORDINATES) {
        	int destinationCoord = this.getPiecePosition() + 
    				(this.getPieceAlliance().getDirection() * offset);
        	if (!BoardUtils.isValidTileCoordinate(destinationCoord)) {
                continue;
            }
        		if(offset == 8) {
        			if(board.getPiece(destinationCoord) == null) {
        				legalMoves.add(new MajorMove(board, this, destinationCoord));
        			}
        		}
                else if(offset == 16 && 
                		((BoardUtils.SECOND_ROW.get(this.getPiecePosition()) &&
                				this.getPieceAlliance().isBlack()) ||
                				(BoardUtils.SEVENTH_ROW.get(this.getPiecePosition()) &&
                						this.getPieceAlliance().isWhite()))) {
                	final int behindCandidateDestinationCoordinate = 
    						this.getPiecePosition() + (this.getPieceAlliance().getDirection() * 8);
                	if(board.getPiece(behindCandidateDestinationCoordinate) == null &&
         				   board.getPiece(destinationCoord) == null) {
         					legalMoves.add(new PawnJump(board, this, destinationCoord));
         				}
        		}
        		else if(offset == 7 &&
        				!((BoardUtils.EIGHTH_COLUMN.get(this.getPiecePosition()) && this.getPieceAlliance().isWhite()) ||
        			     (BoardUtils.FIRST_COLUMN.get(this.getPiecePosition()) && this.getPieceAlliance().isBlack()))) {
        			final Piece pieceAtDestination = board.getPiece(destinationCoord);
        			if(pieceAtDestination != null) {
        				final Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
        				if(this.getPieceAlliance() != pieceAtDestinationAlliance) {
        					legalMoves.add(new PawnAttackMove(board,
        							                          this, 
        							                          destinationCoord, 
        							                          pieceAtDestination));
        				}
        			}
        		}
        		else if(offset == 9 &&
        				!((BoardUtils.FIRST_COLUMN.get(this.getPiecePosition()) && this.getPieceAlliance().isWhite()) ||
        	             (BoardUtils.EIGHTH_COLUMN.get(this.getPiecePosition()) && this.getPieceAlliance().isBlack()))) {
        			final Piece pieceAtDestination = board.getPiece(destinationCoord);
        			if(pieceAtDestination != null) {
        				final Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
        				if(this.getPieceAlliance() != pieceAtDestinationAlliance) {
        					legalMoves.add(new PawnAttackMove(board,
        							                          this, 
        							                          destinationCoord, 
        							                          pieceAtDestination));
        				}
        			}
        		}
        }
        
		return legalMoves;
	}

	@Override
	public String toString() {
		return this.pieceType.toString();
	}

	@Override
	public Piece movePiece(Move move) {
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
}
