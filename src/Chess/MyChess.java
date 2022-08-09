package Chess;

import Chess.board.Board;

public class MyChess {

    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
    }
}
