package Chess.Pieces;

import java.util.Collection;
import java.util.List;
import Chess.Team;
import Chess.board.Board;
import Chess.board.BoardUtils;
import Chess.board.Move;
import Chess.board.Square;

public abstract class Piece {

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

    public abstract Piece movePiece(Move move);

    public abstract List<Move> getLegalMoves(final Board board);
    public abstract String toString();
}
