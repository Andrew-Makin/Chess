package Chess.Pieces;

import Chess.Team;
import Chess.board.Board;
import Chess.board.BoardUtils;
import Chess.board.Move;
import Chess.board.Square;

import java.util.ArrayList;
import java.util.List;

import static Chess.board.BoardUtils.*;
import static Chess.board.Move.*;

public class Pawn extends Piece{

    private final static int[] potentialMovesX = {0, 0, 1, -1};
    private final static int[] potentialMovesY = {1, 2, 1, 1};


    public Pawn(int location, Team color, boolean firstMove, boolean justJumped) {
        super(location, color, firstMove, justJumped, "pawn");
    }

    @Override
    public Piece movePiece(Move move) {
        if (move.getDestination()/ NUM_SQUARES_PER_ROW == 0 || move.getDestination()/NUM_SQUARES_PER_ROW == (NUM_SQUARES_PER_ROW - 1)) {
            // TODO: return promoted piece
            return new Queen(move.getDestination(), this.getColor(), false);
        }
        return new Pawn(move.getDestination(), this.getColor(), false, move.isJump());
    }

    @Override
    public List<Move> getLegalMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();


        for (int i = 0; i < potentialMovesX.length; i++) {
            int potentialXCoord = super.XCoord + potentialMovesX[i] * this.color.getDirection();
            int potentialYCoord = super.YCoord + potentialMovesY[i] * this.color.getDirection();
            if (validCoordinates(potentialXCoord, potentialYCoord)) {
                int potentialDest = potentialYCoord * NUM_SQUARES_PER_ROW + potentialXCoord;
                Square potentialSquare = board.getSquare(potentialDest);
                int enPassantVictimLocation = potentialDest + NUM_SQUARES_PER_ROW;
                if (i==0 && !potentialSquare.squareOccupied()) { // standard move
                    legalMoves.add(new StandardMove(board, this, potentialDest));
                } else if (i==1 && super.isFirstMove()
                        && !potentialSquare.squareOccupied()
                        && !board.getSquare(super.location + this.color.getDirection() * NUM_SQUARES_PER_ROW).squareOccupied()) { // first move jump
                    legalMoves.add(new Jump(board, this, potentialDest));
                } else if (potentialSquare.squareOccupied()){ // diagonal attack
                    if (potentialSquare.getPiece().color != this.color) {
                        legalMoves.add(new Capture(board, this, potentialDest, potentialSquare.getPiece()));
                    }
                } else if (enPassantVictimLocation < NUM_SQUARES && enPassantVictimLocation > 0){ // en passant square valid
                    Square victimSquare = board.getSquare(enPassantVictimLocation);
                    if (victimSquare.squareOccupied()) {
                        Piece victim = victimSquare.getPiece();
                        if (victim.color != this.color && victim.justJumped() && victim.type.equals("pawn")) {// possible en passant
                            legalMoves.add(new Capture(board, this, potentialDest, board.getSquare(enPassantVictimLocation).getPiece()));
                        }
                    }
                }
            }
        }


        return legalMoves;
    }

    @Override
    public String toString() {
        if (this.color == Team.White) {
            return "P";
        }
        return "p";
    }
}
