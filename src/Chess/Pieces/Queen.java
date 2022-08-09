package Chess.Pieces;

import Chess.Team;
import Chess.board.Board;
import Chess.board.BoardUtils;
import Chess.board.Move;
import Chess.board.Square;

import java.util.ArrayList;
import java.util.List;

import static Chess.board.BoardUtils.validCoordinates;

public class Queen extends Piece{

    private final static int[] potentialMovesX = {1, 1, -1, -1, 1, -1, 0, 0};
    private final static int[] potentialMovesY = {1, -1, 1, -1, 0, 0, 1, -1};

    public Queen(int location, Team color, final boolean firstMove) {
        super(location, color, firstMove, false, "queen");
    }

    @Override
    public Piece movePiece(Move move) {
        return new Queen(move.getDestination(), this.getColor(), false);
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for (int i = 0; i < potentialMovesX.length; i++) {
            int potentialXCoord = XCoord + potentialMovesX[i];
            int potentialYCoord = YCoord + potentialMovesY[i];
            while (validCoordinates(potentialXCoord, potentialYCoord)) {
                int potentialDest = potentialYCoord * 8 + potentialXCoord;
                Square potentialSquare = board.getSquare(potentialDest);
                if (!potentialSquare.squareOccupied()) {//tile is unoccupied)
                    legalMoves.add(new Move.StandardMove(board, this, potentialDest));
                } else {
                    if (potentialSquare.getPiece().color!=super.color) {//tile has opposite color piece
                        legalMoves.add(new Move.Capture(board, this, potentialDest, potentialSquare.getPiece()));
                    }
                    break;
                }
                potentialXCoord = potentialXCoord + potentialMovesX[i];
                potentialYCoord = potentialYCoord + potentialMovesY[i];
            }
        }
        return legalMoves;
    }

    @Override
    public String toString() {
        if (this.color == Team.White) {
            return "Q";
        }
        return "q";
    }
}
