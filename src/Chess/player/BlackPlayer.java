package Chess.player;

import Chess.Pieces.Piece;
import Chess.Team;
import Chess.board.Board;
import Chess.board.BoardUtils;
import Chess.board.Move;

import java.util.ArrayList;
import java.util.Collection;

public class BlackPlayer extends Player{
    public BlackPlayer(Board board, Collection<Move> playersMoves, Collection<Move> opponentsMoves) {
        super(board, playersMoves, opponentsMoves);
    }

    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    public Team getColor() {
        return Team.Black;
    }

    public Player getOpponent() {
        return super.board.getWhitePlayer();
    }

    @Override
    public Collection<Move> calculateCastles() {
        Collection<Move> ans = new ArrayList<>();
        if (BoardUtils.NUM_SQUARES_PER_ROW < 8) {
            return ans;
        }

        if (playersKing.isFirstMove() && board.getSquare(7).squareOccupied()) {
            if (board.getSquare(7).getPiece().isFirstMove() && board.getSquare(7).getPiece().getType().equals("rook")) {
                if (       !Player.checkSquareAttacked(4, super.opponentsMoves)
                        && !Player.checkSquareAttacked(5, super.opponentsMoves)
                        && !Player.checkSquareAttacked(6, super.opponentsMoves)) {
                    if (!board.getSquare(5).squareOccupied() && !board.getSquare(6).squareOccupied()) {
                        ans.add(new Move.Castle(board, playersKing, 6, false));
                    }
                }
            }
        }
        if (playersKing.isFirstMove() && board.getSquare(0).squareOccupied()) {
            if (board.getSquare(0).getPiece().isFirstMove() && board.getSquare(0).getPiece().getType().equals("rook")) {
                if (       !Player.checkSquareAttacked(4, super.opponentsMoves)
                        && !Player.checkSquareAttacked(3, super.opponentsMoves)
                        && !Player.checkSquareAttacked(2, super.opponentsMoves)) {
                    if (!board.getSquare(3).squareOccupied() && !board.getSquare(2).squareOccupied() && !board.getSquare(1).squareOccupied()) {
                        ans.add(new Move.Castle(board, playersKing, 2, true));
                    }
                }
            }
        }
        return ans;
    }
}
