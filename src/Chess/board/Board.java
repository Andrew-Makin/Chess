package Chess.board;

import Chess.Pieces.*;
import Chess.Team;
import Chess.player.BlackPlayer;
import Chess.player.Player;
import Chess.player.WhitePlayer;

import java.util.*;

public class Board {

    private final List<Square> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private final Player currentPlayer;

    private Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = findPieces(this.gameBoard, Team.White);
        this.blackPieces = findPieces(this.gameBoard, Team.Black);

        final Collection<Move> whiteMoves = calculateMoves(this.whitePieces);
        final Collection<Move> blackMoves = calculateMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteMoves, blackMoves);
        this.blackPlayer = new BlackPlayer(this, blackMoves, whiteMoves);
        this.currentPlayer = builder.nextToMove.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    private List<Move> calculateMoves(Collection<Piece> pieces) {
        List<Move> moves = new ArrayList<>();
        for (Piece piece : pieces) {
            moves.addAll(piece.getLegalMoves(this));
        }
        return moves;
    }

    private static List<Square> createGameBoard(Builder builder) {
        final List<Square> squares = new ArrayList<>();
        for (int i = 0; i < BoardUtils.NUM_SQUARES; i++) {
            squares.add(i, Square.makeSquare(i, builder.boardConfig.get(i)));
        }

        return squares;
    }

    private static List<Piece> findPieces(final List<Square> squares, final Team team) {
        List<Piece> activePieces = new ArrayList<>();
        for (Square square : squares) {
            if(square.squareOccupied()) {
                if (square.getPiece().getColor() == team) {
                    activePieces.add(square.getPiece());
                }
            }
        }
        return activePieces;
    }

    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        //Black
        builder.setPiece(new Rook  (0, Team.Black, true));
        builder.setPiece(new Knight(1, Team.Black, true));
        builder.setPiece(new Bishop(2, Team.Black, true));
        builder.setPiece(new Queen (3, Team.Black, true));
        builder.setPiece(new King  (4, Team.Black, true));
        builder.setPiece(new Bishop(5, Team.Black, true));
        builder.setPiece(new Knight(6, Team.Black, true));
        builder.setPiece(new Rook  (7, Team.Black, true));

        for (int i = 0; i < BoardUtils.NUM_SQUARES_PER_ROW; i++) {
            builder.setPiece(new Pawn(i + BoardUtils.NUM_SQUARES_PER_ROW, Team.Black, true, false));
        }

        //White
        builder.setPiece(new Rook  (56, Team.White, true));
        builder.setPiece(new Knight(57, Team.White, true));
        builder.setPiece(new Bishop(58, Team.White, true));
        builder.setPiece(new Queen (59, Team.White, true));
        builder.setPiece(new King  (60, Team.White, true));
        builder.setPiece(new Bishop(61, Team.White, true));
        builder.setPiece(new Knight(62, Team.White, true));
        builder.setPiece(new Rook  (63, Team.White, true));

        for (int i = 0; i < BoardUtils.NUM_SQUARES_PER_ROW; i++) {
            builder.setPiece(new Pawn(i + BoardUtils.NUM_SQUARES_PER_ROW * (BoardUtils.NUM_SQUARES_PER_ROW - 2), Team.White, true, false));
        }
        builder.setPlayerToMove(Team.White);
        return builder.build();
    }
    public Square getSquare(int squareID) {
        return gameBoard.get(squareID);
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    @Override
    // TODO: string builder could be used here to increase efficiency
    public String toString() {
        String ans = "";
        int i = 0;
        for (Square square : gameBoard) {
            if (square.squareOccupied()) {
                ans = ans + square.getPiece().toString();
            } else {
                ans = ans + square.toString();
            }
            ans = ans + " ";
            if (i % BoardUtils.NUM_SQUARES_PER_ROW == BoardUtils.NUM_SQUARES_PER_ROW - 1) {
                ans = ans + "\n";
            }
            i++;
        }
        return ans;
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    public Iterable<Move> getAllMoves() {
        List<Move> ans = new ArrayList<>();
        ans.addAll(this.whitePlayer.getLegalMoves());
        ans.addAll(this.blackPlayer.getLegalMoves());
        return ans;
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Team nextToMove;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }
        public Board build() {
            return new Board(this);
        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getLocation(), piece);
            return this;
        }

        public Builder setPlayerToMove(final Team nextToMove) {
            this.nextToMove = nextToMove;
            return this;
        }


    }
}
