package Chess.Pieces;

import java.util.List;
import Chess.Team;
import Chess.board.Board;
import Chess.board.BoardUtils;
import Chess.board.Move;

public abstract class Piece {
    // I will provide some brief documentation for all the piece sub classes here in the parent class
    //
    // Pieces have two mysterious arrays of int at the top of the file, these represent the pieces movement
    // vectors. for example the queen has the two arrays:
    //     private final static int[] potentialMovesX = {1, 1, -1, -1, 1, -1, 0, 0};
    //     private final static int[] potentialMovesY = {1, -1, 1, -1, 0, 0, 1, -1};
    // if you take the first element of both arrays you get the change in y and the change in x of the first move
    // of the queen. Each pairing of two elements in the arrays represents a potential move.
    //
    // I don't know if the hashCode() and equals() methods are up to date because I got pretty lazy doing all that
    // boring hash code stuff.
    //
    // Pieces are immutable and are only created by the board, or by other pieces (when the pieces are moved)
    //
    // the update method only really does anything for the pawn class because it needs it for the en passant
    // calculation other Pieces implement it just because I wanted it to be an abstract method.
    //
    // The x y coordinate system is only used by the pieces. All other parts of the chess engine just number
    // the squares 0-63, this is becuase the xy system is more convienent for move calculation, while a single
    // number is more convienent to store the squares in collections elsewhere.

    protected final int location;
    protected final int YCoord;
    protected final int XCoord;
    protected final Team color;
    protected final boolean firstMove;
    protected final boolean justJumped;
    protected final String type;

    Piece(final int location, final Team color, final boolean firstMove, final boolean justJumped, final String type) {
        this.location = location;
        XCoord = location % BoardUtils.NUM_SQUARES_PER_ROW;
        YCoord = location / BoardUtils.NUM_SQUARES_PER_ROW;
        this.color = color;
        this.firstMove = firstMove;
        this.justJumped = justJumped;
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        Piece otherPiece = (Piece) other;
        return     location   == otherPiece.getLocation()
                && type.equals(otherPiece.getType())
                && color      == otherPiece.getColor()
                && firstMove  == otherPiece.isFirstMove()
                && justJumped == otherPiece.justJumped();
    }

    @Override
    public int hashCode() {
        return    31 * type.hashCode()
                + 31 * color.hashCode()
                + 31 * location
                + 31 * (firstMove? 1 : 0)
                + 31 * (justJumped? 1 : 0);
    }
    public boolean isFirstMove() {
        return firstMove;
    }
    public boolean justJumped() {
        return justJumped;
    }

    public String getType() {
        return type;
    }
    public int getLocation() {
        return location;
    }

    public Team getColor() {
        return color;
    }

    public abstract Piece update(Piece source);

    public abstract Piece movePiece(Move move);

    public abstract List<Move> getLegalMoves(final Board board);
    public abstract String toString();
}
