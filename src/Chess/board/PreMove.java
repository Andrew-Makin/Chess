package Chess.board;

public class PreMove {

    private final Board preMoveBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public PreMove(Board preMoveBoard, Move move, MoveStatus moveStatus) {
        this.preMoveBoard = preMoveBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getPreMoveBoard() {
        return this.preMoveBoard;
    }
}
