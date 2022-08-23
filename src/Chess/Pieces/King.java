package Chess.Pieces;

import Chess.Team;
import Chess.board.Board;
import Chess.board.BoardUtils;
import Chess.board.Move;
import Chess.board.Square;

import java.util.ArrayList;
import java.util.List;

import static Chess.board.BoardUtils.validCoordinates;

public class King extends Piece{

    private final static int[] potentialMovesX = {1, 1, 1, 0, 0, -1, -1, -1};
    private final static int[] potentialMovesY = {1, 0, -1, 1, -1, 1, 0, -1};

    public King(final int location, final Team color, final boolean firstMove) {
        super(location, color, firstMove, false, "king");
    }

    @Override
    public Piece update(Piece source) {
        return new King(this.location, this.color, this.firstMove);
    }

    @Override
    public Piece movePiece(Move move) {
        return new King(move.getDestination(), this.getColor(), false);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int i = 0; i < potentialMovesX.length; i++) {
            int potentialXCoord = super.XCoord + potentialMovesX[i];
            int potentialYCoord = super.YCoord + potentialMovesY[i];
            if (validCoordinates(potentialXCoord, potentialYCoord)) {
                int potentialDest = potentialYCoord * BoardUtils.NUM_SQUARES_PER_ROW + potentialXCoord;
                Square potentialSquare = board.getSquare(potentialDest);
                if (!potentialSquare.squareOccupied()) {//tile is unoccupied)
                    legalMoves.add(new Move.StandardMove(board, this, potentialDest));
                } else if (potentialSquare.getPiece().color!=super.color) {//tile has opposite color piece
                    legalMoves.add(new Move.Capture(board, this, potentialDest, potentialSquare.getPiece()));
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        if (this.color == Team.White) {
            return "K";
        }
        return "k";
    }
}
