package Chess.board;

import Chess.Pieces.Piece;
import Chess.Team;

public final class EmptySquare extends Square {

    protected EmptySquare(final int squareID) {
        super(squareID);
    }

    @Override
    public boolean squareOccupied() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return null;
    }

    public String toString() {
        return "-";
    }
}