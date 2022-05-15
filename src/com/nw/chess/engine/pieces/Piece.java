package com.nw.chess.engine.pieces;

import java.util.Collection;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.*;
import com.nw.chess.engine.board.Move;

public abstract class Piece {
	final PieceType pieceType;
	private final int piecePosition;
	private final Alliance pieceAlliance;

	public Piece(final PieceType type,
			     final Alliance pieceAlliance, 
			     final int piecePosition) {
		this.pieceType = type;
		this.pieceAlliance = pieceAlliance;
		this.piecePosition = piecePosition;
	}
	
	public int getPiecePosition() {
		return piecePosition;
	}

	public Alliance getPieceAlliance() {
		return pieceAlliance;
	}
	
	public PieceType getPieceType() {
        return this.pieceType;
    }
	
	public abstract Collection<Move> calculateLegalMoves(Board board);
	public abstract Piece movePiece(Move move);
	
	public enum PieceType {
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}
		}, BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}
		}, KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}
		}, PAWN("P") {
			@Override
			public boolean isKing() {
				return false;
			}
		}, QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}
		}, ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}
		};
		
		private final String pieceName;
		
		private PieceType(final String pieceName) {
			this.pieceName = pieceName;
		}

		@Override
        public String toString() {
            return this.pieceName;
        }
		 public abstract boolean isKing();
	}
}
