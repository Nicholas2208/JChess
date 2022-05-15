package com.nw.chess.engine.player;

import java.util.Collection;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.pieces.Piece;

public class BlackPlayer extends Player {
	
	public BlackPlayer(final Board board, 
			           final Collection<Move> playerLegals, 
			           final Collection<Move> opponentLegals) {
		super(board, playerLegals, opponentLegals);
		
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}

	@Override
    public String toString() {
        return Alliance.BLACK.toString();
    }
}
