package com.nw.chess.engine.player;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.board.Move.MoveStatus;
import com.nw.chess.engine.board.MoveTransition;
import com.nw.chess.engine.pieces.King;
import com.nw.chess.engine.pieces.Piece;

import static java.util.stream.Collectors.collectingAndThen;

public abstract class Player {
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	protected final boolean isInCheck;
	
	public Player(Board board, 
			      Collection<Move> playerLegals, 
			      final Collection<Move> opponentLegals) {
		this.board = board;
		this.playerKing = establishKing();
		this.isInCheck = !calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentLegals).isEmpty();
		this.legalMoves = Collections.unmodifiableCollection(playerLegals);
	}
	
	public boolean isInCheck() {
        return this.isInCheck;
    }
	
	public King getPlayerKing() {
        return this.playerKing;
    }

    private King establishKing() {
        return (King) getActivePieces().stream()
                                       .filter(piece -> piece.getPieceType().isKing())
                                       .findAny()
                                       .orElseThrow(RuntimeException::new);
    }
	
	public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }
	
	public boolean isInCheckMate() {
	    return this.isInCheck && !hasEscapeMoves();
	}

	public boolean isInStaleMate() {
	    return !this.isInCheck && !hasEscapeMoves();
	}
	
	private boolean hasEscapeMoves() {
        return this.legalMoves.stream()
                              .anyMatch(move -> makeMove(move)
                              .getMoveStatus().isDone());
    }
	
	static Collection<Move> calculateAttacksOnTile(final int tile,
                                                   final Collection<Move> moves) {
       return moves.stream()
          .filter(move -> move.getDestinationCoordinate() == tile)
          .collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
   }
	
	public MoveTransition makeMove(final Move move) {
		if (!this.legalMoves.contains(move)) {
            return new MoveTransition(this.board, this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
		final Board transitionedBoard = move.execute();
		if(transitionedBoard.currentPlayer().getOpponent().isInCheck()) {
            return new MoveTransition(this.board, this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
		return transitionedBoard.currentPlayer().getOpponent().isInCheck() ?
                new MoveTransition(this.board, this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK) :
                new MoveTransition(this.board, transitionedBoard, move, MoveStatus.DONE);
	}
	
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
}
