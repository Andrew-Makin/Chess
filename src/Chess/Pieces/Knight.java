package Chess.Pieces;

import Chess.Team;
import Chess.board.Board;
import Chess.board.BoardUtils;
import Chess.board.Move;
import Chess.board.Square;

import java.util.ArrayList;
import java.util.List;

import static Chess.board.BoardUtils.NUM_SQUARES_PER_ROW;
import static Chess.board.BoardUtils.validCoordinates;
import static Chess.board.Move.*;

public class Knight extends Piece{

    private final static int[] potentialMovesX = {1, 1, -1, -1, 2, 2, -2, -2};
    private final static int[] potentialMovesY = {2, -2, 2, -2, 1, -1, 1, -1};



    public Knight(final int location, final Team color, final boolean firstMove) {
        super(location, color, firstMove, false, "knight");
    }

    @Override
    public Piece update(Piece source) {
        return new Knight(this.location, this.color, this.firstMove);
    }

    @Override
    public Piece movePiece(Move move) {
        return new Knight(move.getDestination(), this.getColor(), false);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int i = 0; i < potentialMovesX.length; i++) {
            int potentialXCoord = super.XCoord + potentialMovesX[i];
            int potentialYCoord = super.YCoord + potentialMovesY[i];
            if (validCoordinates(potentialXCoord, potentialYCoord)) {
                int potentialDest = potentialYCoord * NUM_SQUARES_PER_ROW + potentialXCoord;
                Square potentialSquare = board.getSquare(potentialDest);
                if (!potentialSquare.squareOccupied()) {//tile is unoccupied)
                    legalMoves.add(new StandardMove(board, this, potentialDest));
                } else if (potentialSquare.getPiece().color!=super.color) {//tile has opposite color piece
                    legalMoves.add(new Capture(board, this, potentialDest, potentialSquare.getPiece()));
                }
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        if (this.color == Team.White) {
            return "N";
        }
        return "n";
    }
}
