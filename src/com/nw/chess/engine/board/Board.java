package com.nw.chess.engine.board;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.nw.chess.engine.Alliance;
import com.nw.chess.engine.pieces.*;
import com.nw.chess.engine.player.*;

public class Board {
	private  WhitePlayer whitePlayer;
    private  BlackPlayer blackPlayer;
    private final Player currentPlayer;
	private final Map<Integer, Piece> boardConfig;
	private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    
    private Board(final Builder builder) {
    	this.boardConfig = Collections.unmodifiableMap(builder.boardConfig);
    	this.whitePieces = calculateActivePieces(builder, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(builder, Alliance.BLACK);
        final Collection<Move> whiteStandardMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardMoves = calculateLegalMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteStandardMoves, blackStandardMoves);
        this.blackPlayer = new BlackPlayer(this, blackStandardMoves, whiteStandardMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayerByAlliance(this.whitePlayer, this.blackPlayer);
        
    }
    
    @Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
    	for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
    		final String tileText = prettyPrint(this.boardConfig.get(i));
    		builder.append(String.format("%3s", tileText));
			if ((i + 1) % 8 == 0) {
                builder.append("\n");
            }
    	}
    	return builder.toString();
    }
    
    private static String prettyPrint(final Piece piece) {
		 if(piece != null) {
	            return piece.getPieceAlliance().isBlack() ?
	                   piece.toString().toLowerCase() : piece.toString();
	     }
	     return "-";
	 }
    
    public static Board createStandardBoard() {
    	final Builder builder = new Builder();
    	// Black Layout
    	builder.setPiece(new Rook(Alliance.BLACK, 0));
    	builder.setPiece(new Knight(Alliance.BLACK, 1));
    	builder.setPiece(new Bishop(Alliance.BLACK, 2));
    	builder.setPiece(new Queen(Alliance.BLACK, 3));
    	builder.setPiece(new King(Alliance.BLACK, 4));
    	builder.setPiece(new Bishop(Alliance.BLACK, 5));
    	builder.setPiece(new Knight(Alliance.BLACK, 6));
    	builder.setPiece(new Rook(Alliance.BLACK, 7));
    	builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
    	// White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
    	builder.setPiece(new Knight(Alliance.WHITE, 57));
    	builder.setPiece(new Bishop(Alliance.WHITE, 58));
    	builder.setPiece(new Queen(Alliance.WHITE, 59));
    	builder.setPiece(new King(Alliance.WHITE, 60));
    	builder.setPiece(new Bishop(Alliance.WHITE, 61));
    	builder.setPiece(new Knight(Alliance.WHITE, 62));
    	builder.setPiece(new Rook(Alliance.WHITE, 63));
    	
    	builder.setMoveMaker(Alliance.WHITE);
    	
    	return builder.build();
    }
    
    private static Collection<Piece> calculateActivePieces(final Builder builder,
                                                           final Alliance alliance) {
         return builder.boardConfig.values().stream()
                .filter(piece -> piece.getPieceAlliance() == alliance)
                                        .collect(Collectors.toList());
    }
    
    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
        return pieces.stream().flatMap(piece -> piece.calculateLegalMoves(this).stream())
                      .collect(Collectors.toList());
    }
    
    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }
    
    public Collection<Piece> getAllPieces() {
        return Stream.concat(this.whitePieces.stream(),
                             this.blackPieces.stream()).collect(Collectors.toList());
    }
    
    public Piece getPiece(final int coordinate) {
        return this.boardConfig.get(coordinate);
    }
    
    public Player currentPlayer() {
        return this.currentPlayer;
    }
    
    public WhitePlayer whitePlayer() {
        return this.whitePlayer;
    }

    public BlackPlayer blackPlayer() {
        return this.blackPlayer;
    }
    
    public Collection<Move> getAllLegalMoves() {
		return Stream.concat(this.whitePlayer.getLegalMoves().stream(),
                             this.blackPlayer.getLegalMoves().stream()).collect(Collectors.toList());
    }
    
    public static class Builder {
    	Map<Integer, Piece> boardConfig;
    	Alliance nextMoveMaker;
    	
    	public Builder() {
			this.boardConfig = new HashMap<>(32, 1.0f);
		}
    	
    	public Board build() {
            return new Board(this);
        }
    	
    	public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }
    	
    	public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }
    }

}
