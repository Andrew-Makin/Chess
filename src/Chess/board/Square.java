package Chess.board;

import Chess.Pieces.Piece;

import java.util.*;

// the square class is also immutable like pieces, boards, and moves
// it only stores the number square that it is and if it is occupied, it also stores the piece on it

// one optomization is that I pre-make all 64 empty squares and instead of constructing them each
// time I need one, I just get that empty square from the collection
public abstract class Square {

    protected final int squareID;

    private static final Map<Integer, EmptySquare> emptySquares = makeAllEmptySquares();

    public static Square makeSquare(final int squareID, final Piece occupant) {
        if (occupant == null) {
            return emptySquares.get(squareID);
        } else {
            return new OccupiedSquare(squareID, occupant);
        }
    }

    protected Square(final int squareID) {
        this.squareID = squareID;
    }

    private static Map<Integer, EmptySquare> makeAllEmptySquares() {

        final Map<Integer, EmptySquare> emptySquares = new HashMap<>();

        for (int i = 0; i < BoardUtils.NUM_SQUARES; i++) {
            emptySquares.put(i, new EmptySquare(i));
        }

        return Collections.unmodifiableMap(emptySquares);
    }

    public abstract boolean squareOccupied();

    public abstract Piece getPiece();

    public int getSquareID() {
        return this.squareID;
    }
}