package Chess.board;


import Chess.Pieces.Piece;
import Chess.Team;
import Chess.player.Player;

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int   destination;
    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board,
         final Piece movedPiece,
         final int destination) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destination = destination;
    }

    @Override
    public int hashCode() {
        return 31 * this.destination + 17 * movedPiece.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return getDestination() == otherMove.getDestination()
                && getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public int getCurrentLocation() {
        return movedPiece.getLocation();
    }

    public int getDestination() {
        return destination;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public abstract Board execute();
    public boolean isJump() {
        return false;
    }
    public boolean isCastle() {
        return false;
    }

    public static final class StandardMove extends Move {

        public StandardMove(Board board, Piece movedPiece, int destination) {
            super(board, movedPiece, destination);

        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();

            Player current = this.board.getCurrentPlayer();

            for(final Piece piece : current.getActivePieces()) {
                if (this.movedPiece.equals(piece)) {
                    builder.setPiece(piece.movePiece(this));
                } else {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : current.getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPlayerToMove(current.getOpponent().getColor());

            return builder.build();
        }
    }

    public static final class Capture extends Move {

        final Piece victim;

        public Capture(Board board, Piece movedPiece, int destination, final Piece victim) {
            super(board, movedPiece, destination);
            this.victim = victim;
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();

            Player current = this.board.getCurrentPlayer();

            for(final Piece piece : current.getActivePieces()) {
                if (this.movedPiece.equals(piece)) {
                    builder.setPiece(piece.movePiece(this));
                } else {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : current.getOpponent().getActivePieces()) {
                if (!piece.equals(victim)) {
                    builder.setPiece(piece);
                }
            }

            builder.setPlayerToMove(current.getOpponent().getColor());

            return builder.build();
        }
    }

    public static final class Jump extends Move {

        public Jump(Board board, Piece movedPiece, int destination) {
            super(board, movedPiece, destination);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();

            Player current = this.board.getCurrentPlayer();

            for(final Piece piece : current.getActivePieces()) {
                if (this.movedPiece.equals(piece)) {
                    builder.setPiece(piece.movePiece(this));
                } else {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : current.getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPlayerToMove(current.getOpponent().getColor());

            return builder.build();
        }

        @Override
        public boolean isJump() {
            return true;
        }
    }
    public static final class Castle extends Move {
        private final boolean queenside;

        public Castle(Board board, Piece movedPiece, int destination, boolean queenside) {
            super(board, movedPiece, destination);
            this.queenside = queenside;
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();

            Player current = this.board.getCurrentPlayer();
            Piece ourRook = null;
            if (current.getColor() == Team.White) {
                if (queenside) {
                    ourRook = board.getSquare(BoardUtils.NUM_SQUARES_PER_ROW * (BoardUtils.NUM_SQUARES_PER_ROW - 1)).getPiece();
                } else {
                    ourRook = board.getSquare(BoardUtils.NUM_SQUARES - 1).getPiece();
                }
            } else {
                if (queenside) {
                    ourRook = board.getSquare(0).getPiece();
                } else {
                    ourRook = board.getSquare(BoardUtils.NUM_SQUARES_PER_ROW - 1).getPiece();
                }
            }
            for(final Piece piece : current.getActivePieces()) {
                if (this.movedPiece.equals(piece)) {
                    builder.setPiece(piece.movePiece(this));
                } else if (piece.equals(ourRook)){
                    builder.setPiece(piece.movePiece(this));
                } else {
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : current.getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            builder.setPlayerToMove(current.getOpponent().getColor());

            return builder.build();
        }

        @Override
        public boolean isCastle() {
            return true;
        }
    }

    public static final class NullMove extends Move {

        private NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("CANNOT EXECUTE NULL MOVE");
        }
    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("don't instantiate a move factory");
        }

        public static Move createMove(final Board board,
                                      final int currentLocation,
                                      final int destination) {
            for (final Move move : board.getAllMoves()) {
                if (move.getCurrentLocation() == currentLocation &&
                    move.getDestination() == destination) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
