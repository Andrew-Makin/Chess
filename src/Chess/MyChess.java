package Chess;

import Chess.GUI.Table;
import Chess.board.Board;
import Chess.board.Move;
import Chess.board.MoveTransition;

import java.util.Collection;
import java.util.Random;

public class MyChess {

    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table table = new Table();
    }
}
