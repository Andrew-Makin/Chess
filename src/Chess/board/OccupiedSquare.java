package Chess.board;

import Chess.Pieces.Piece;

public final class OccupiedSquare extends Square {

    private final Piece occupant;

    protected OccupiedSquare(final int squareID, final Piece occupant) {
        super(squareID);
        this.occupant = occupant;
    }

    @Override
    public boolean squareOccupied() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return occupant;
    }
}