package com.nw.chess.engine.player;

import java.util.Collection;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.board.Board;
import com.nw.chess.engine.board.Move;
import com.nw.chess.engine.pieces.Piece;

public class WhitePlayer extends Player {
	
	public WhitePlayer(final Board board, 
			           final Collection<Move> playerLegals, 
			           final Collection<Move> opponentLegals) {
		super(board, playerLegals, opponentLegals);
		
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
    public String toString() {
        return Alliance.WHITE.toString();
    }
}
