package com.nw.chess.engine.board;

import com.nw.chess.engine.board.Board.Builder;
import com.nw.chess.engine.pieces.Pawn;
import com.nw.chess.engine.pieces.Piece;

public abstract class Move {
	protected final Board board;
	protected final Piece movedPiece;
	protected final int destinationCoordinate;
	
	private Move(final Board board,
		     final Piece movedPiece,
		     final int destinationCoordinate) {
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	private Move(final Board board,
                 final int destinationCoordinate) {
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
   
     }
	
	public Board execute() {
		final Board.Builder builder = new Builder();
		this.board.currentPlayer().getActivePieces().stream().filter(piece -> !this.movedPiece.equals(piece)).forEach(builder::setPiece);
		this.board.currentPlayer().getOpponent().getActivePieces().forEach(builder::setPiece);
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		
		return builder.build();
	}

	public int getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
	
	public Piece getMovedPiece() {
        return this.movedPiece;
    }
	
	public int getCurrentCoordinate() {
        return this.movedPiece.getPiecePosition();
    }
	
	public boolean isAttack() {
        return false;
    }
	
	public enum MoveStatus {
		DONE {
			@Override
			public boolean isDone() {
				return true;
			}
		}, ILLEGAL_MOVE {
			@Override
			public boolean isDone() {
				return false;
			}
		}, LEAVES_PLAYER_IN_CHECK {
			@Override
			public boolean isDone() {
				return false;
			}
		};
		
		public abstract boolean isDone();
	}
	
	public static class MajorMove extends Move {
		public MajorMove(final Board board,
		                 final Piece pieceMoved,
		                 final int destinationCoordinate) {
	       super(board, pieceMoved, destinationCoordinate);
        }
	}
	
	public static class AttackMove extends Move {
        final Piece pieceAttacked;
		
		public AttackMove(final Board board,
				          final Piece pieceMoved,
				          final int destinationCoordinate,
				          final Piece pieceAttacked) {
			super(board, pieceMoved, destinationCoordinate);
			this.pieceAttacked = pieceAttacked;
		}
		
		@Override
	    public boolean isAttack() {
	        return true;
	    }
	}
	
	public static class MajorAttackMove extends AttackMove {
		public MajorAttackMove(final Board board,
                               final Piece pieceMoved,
                               final int destinationCoordinate,
                               final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }
	}

	public static class PawnMove extends Move {
		public PawnMove(final Board board,
                        final Piece pieceMoved,
                        final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }
	}
	
	public static class PawnJump extends Move {
		public PawnJump(final Board board,
                        final Pawn pieceMoved,
                        final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }
	}
	
	public static class PawnAttackMove extends AttackMove {
		public PawnAttackMove(final Board board,
                              final Piece pieceMoved,
                              final int destinationCoordinate,
                              final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }
	}
	
	private static class NullMove extends Move {
		
		private NullMove() {
            super(null, -1);
        }
		
		@Override
        public int getCurrentCoordinate() {
            return -1;
        }

        @Override
        public int getDestinationCoordinate() {
            return -1;
        }

        @Override
        public Board execute() {
            throw new RuntimeException("cannot execute null move!");
        }

        @Override
        public String toString() {
            return "Null Move";
        }
	}
	
	public static class MoveFactory {
		private static final Move NULL_MOVE = new NullMove();
		
		private MoveFactory() {
            throw new RuntimeException("Not instantiatable!");
        }

        public static Move getNullMove() {
            return NULL_MOVE;
        }
        
        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {
           for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                        move.getDestinationCoordinate() == destinationCoordinate) {
                        return move;
                }
           }
           return NULL_MOVE;
      }
	}
}
