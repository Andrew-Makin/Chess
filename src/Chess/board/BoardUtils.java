package Chess.board;

public class BoardUtils {
    public static final int NUM_SQUARES = 64;
    public static final int NUM_SQUARES_PER_ROW = 8;

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
