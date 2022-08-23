package Chess.GUI;

import Chess.board.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameActionListener implements ActionListener {
    private Table table;

    public NewGameActionListener(Table table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Board board = Board.createStandardBoard();
        table.setDrawnBoard(board);
    }
}
