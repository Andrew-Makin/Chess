package Chess.player;

import Chess.Pieces.Piece;
import Chess.Team;
import Chess.board.Board;
import Chess.board.BoardUtils;
import Chess.board.Move;

import java.util.ArrayList;
import java.util.Collection;

public class WhitePlayer extends Player{
    public WhitePlayer(Board board, Collection<Move> playersMoves, Collection<Move> opponentsMoves) {
        super(board, playersMoves, opponentsMoves);
    }

    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    public Team getColor() {
        return Team.White;
    }

    public Player getOpponent() {
        return super.board.getBlackPlayer();
    }

    @Override
    public Collection<Move> calculateCastles() {
        Collection<Move> ans = new ArrayList<>();

        if (BoardUtils.NUM_SQUARES_PER_ROW < 8) {
            return ans;
        }

        if (playersKing.isFirstMove() && board.getSquare(63).squareOccupied()) {
            if (board.getSquare(63).getPiece().isFirstMove() && board.getSquare(63).getPiece().getType().equals("rook")) {
                if (       !Player.checkSquareAttacked(60, super.opponentsMoves)
                        && !Player.checkSquareAttacked(61, super.opponentsMoves)
                        && !Player.checkSquareAttacked(62, super.opponentsMoves)) {
                    if (!board.getSquare(61).squareOccupied() && !board.getSquare(62).squareOccupied()) {
                        ans.add(new Move.Castle(board, playersKing, 62, false));
                    }
                }
            }
        }
        if (playersKing.isFirstMove() && board.getSquare(56).squareOccupied()) {
            if (board.getSquare(56).getPiece().isFirstMove() && board.getSquare(56).getPiece().getType().equals("rook")) {
                if (       !Player.checkSquareAttacked(60, super.opponentsMoves)
                        && !Player.checkSquareAttacked(59, super.opponentsMoves)
                        && !Player.checkSquareAttacked(58, super.opponentsMoves)) {
                    if (!board.getSquare(59).squareOccupied() && !board.getSquare(58).squareOccupied() && !board.getSquare(57).squareOccupied()) {
                        ans.add(new Move.Castle(board, playersKing, 58, true));
                    }
                }
            }
        }
        return ans;
    }
}
