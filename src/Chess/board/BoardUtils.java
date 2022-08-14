package Chess.board;

public class BoardUtils {
    public static final int NUM_SQUARES_PER_ROW = 8;
    public static final int NUM_SQUARES = NUM_SQUARES_PER_ROW * NUM_SQUARES_PER_ROW;

    private BoardUtils() {
        throw new RuntimeException("Not instantiateable");
    }

    public static boolean validCoordinates(int XCoord, int YCoord) {
        if (0 <= XCoord && XCoord < NUM_SQUARES_PER_ROW
                && 0 <= YCoord && YCoord < NUM_SQUARES_PER_ROW) {
            return true;
        }
        return false;
    }
}
