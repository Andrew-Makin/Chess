package Chess.board;

public enum MoveStatus {
    Done {
        public boolean isDone() {
            return true;
        }
    },
    Illegal {
        public boolean isDone() {
            return false;
        }
    },
    InCheck {
        public boolean isDone() {
            return false;
        }
    };


    public abstract boolean isDone();
}
