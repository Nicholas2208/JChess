package com.nw.chess.engine;

import com.nw.chess.engine.player.BlackPlayer;
import com.nw.chess.engine.player.Player;
import com.nw.chess.engine.player.WhitePlayer;

public enum Alliance {
	
	WHITE {
		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public boolean isWhite() {
			return true;
		}

		@Override
		public int getDirection() {
			return UP_DIRECTION;
		}

		@Override
		public Player choosePlayerByAlliance(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return whitePlayer;
		}
		
		@Override
        public String toString() {
            return "White";
        }
	}, BLACK {
		@Override
		public boolean isBlack() {
			return true;
		}

		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public int getDirection() {
			return DOWN_DIRECTION;
		}

		@Override
		public Player choosePlayerByAlliance(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
			return blackPlayer;
		}
		
		@Override
        public String toString() {
            return "Black";
        }
	};

	public abstract int getDirection();
	public abstract boolean isBlack();
	public abstract boolean isWhite();
	public abstract Player choosePlayerByAlliance(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
	
	private static final int UP_DIRECTION = -1;
    private static final int DOWN_DIRECTION = 1;
}
