package Chess;

import Chess.board.Board;
import Chess.board.Move;
import Chess.board.MoveTransition;

import java.util.Collection;
import java.util.Random;

public class MyChess {

    public static void main(String[] args) {
        int j = 0;
        Board board = Board.createStandardBoard();
        while (j < 20) {
            Collection<Move> moves = board.getCurrentPlayer().getLegalMoves();
            Move move;

            Random r = new Random();

            int i;

            i = r.nextInt(moves.stream().toList().size());
            move = moves.stream().toList().get(i);

            MoveTransition mt = board.getCurrentPlayer().makeMove(move);

            board = mt.getTransitionBoard();
            System.out.println(board);
            j++;
        }
    }
}
