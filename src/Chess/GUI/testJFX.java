package Chess.GUI;

import Chess.board.BoardUtils;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class testJFX extends Application {

    public static final int SQUARE_SIZE = 800 / BoardUtils.NUM_SQUARES_PER_ROW;
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(SQUARE_SIZE * BoardUtils.NUM_SQUARES_PER_ROW, SQUARE_SIZE * BoardUtils.NUM_SQUARES_PER_ROW);



        return root;
    }
}
