package Chess;

import Chess.board.Board;
import Chess.board.Move;
import Chess.board.MoveTransition;

import java.util.Collection;
import java.util.Random;

public class MyChess {

    public static void main(String[] args) {
        for (int q = 0; q < 100; q++) {
            int j = 0;
            Board board = Board.createStandardBoard();
            while (j < 50) {
                Collection<Move> moves = board.getCurrentPlayer().getLegalMoves();
                Move move;

                Random r = new Random();

                int i;
                MoveTransition mt;
                int k = 0;
                do {
                    i = r.nextInt(moves.stream().toList().size());
                    move = moves.stream().toList().get(i);
                    if (move.isCastle()) {
                        System.out.println("we castled!");
                    }
                    mt = board.getCurrentPlayer().makeMove(move);
                    k++;
                    if (k > 10000) {
                        System.out.println("no available moves");
                        System.out.println(q);
                        return;
                    }
                } while (!mt.getMoveStatus().isDone());
                board = mt.getTransitionBoard();
                System.out.println(board);
                j++;
            }
        }
    }
}
