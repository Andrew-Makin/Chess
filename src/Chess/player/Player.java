package Chess.player;

import Chess.Pieces.King;
import Chess.Pieces.Piece;
import Chess.Team;
import Chess.board.Board;
import Chess.board.Move;
import Chess.board.MoveStatus;
import Chess.board.PreMove;

import java.util.Collection;

// The player class is where castles are calculated, they are the only move not calculated by a piece
// this is because the castle requires a lot more information about board state than any other
// move, also it moves two pieces.
public abstract class Player {

    protected final Board board;
    protected final King playersKing;
    protected final Collection<Move> playersMoves;
    protected final Collection<Move> opponentsMoves;
    protected final boolean isInCheck;
    public Player(Board board, Collection<Move> playersMoves, Collection<Move> opponentsMoves) {

        this.board = board;
        this.playersKing = findKing();
        this.playersMoves = playersMoves;
        this.opponentsMoves = opponentsMoves;
        this.isInCheck = checkSquareAttacked(playersKing.getLocation(), opponentsMoves);
        playersMoves.addAll(calculateCastles());
    }

    public King getKing() {
        return this.playersKing;
    }
    private King findKing() {
        for (Piece piece : getActivePieces()) {
            if (piece.getType().equals("king")) {
                return (King) piece;
            }
        }
        throw new RuntimeException("no king on board");
    }

    public Collection<Move> getLegalMoves() {
        return this.playersMoves;
    }

    public static boolean checkSquareAttacked(int squareID, Collection<Move> opponentsMoves) {
        for (Move move : opponentsMoves) {
            if (move.getDestination() == squareID) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLegalMove(final Move move) {
        return this.playersMoves.contains(move);
    }


    public PreMove makeMove(final Move move) {
        if(!checkLegalMove(move)) {
            return new PreMove(this.board, move, MoveStatus.Illegal);
        }

        final Board transitionBoard = move.execute();
        if (Player.checkSquareAttacked(transitionBoard.getCurrentPlayer().getOpponent().getKing().getLocation(), transitionBoard.getCurrentPlayer().getLegalMoves())) {
            return new PreMove(this.board, move, MoveStatus.InCheck);
        }
        return new PreMove(transitionBoard, move, MoveStatus.Done);
    }
    public abstract Collection<Piece> getActivePieces();
    public abstract Team getColor();
    public abstract Player getOpponent();
    public abstract Collection<Move> calculateCastles();
}
