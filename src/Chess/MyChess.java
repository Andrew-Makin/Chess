package Chess;

import Chess.board.Board;
import Chess.board.Move;
import Chess.board.MoveTransition;

import java.util.Collection;
import java.util.Random;

public class MyChess {

    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        Collection<Move> moves = board.getCurrentPlayer().getLegalMoves();
        Move move;

        Random r = new Random();

        move = moves.stream().toList().get(0);
        MoveTransition mt = board.getCurrentPlayer().makeMove(move);
        board = mt.getTransitionBoard();
        System.out.println(board);
    }
}
