package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application {

    private final double insets = 5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        GUIWindow window = new GUIWindow();


        AnchorPane root = new AnchorPane(window);
        AnchorPane.setBottomAnchor(window, this.insets);
        AnchorPane.setTopAnchor(window, this.insets);
        AnchorPane.setLeftAnchor(window, this.insets);
        AnchorPane.setRightAnchor(window, this.insets);

        root.setBackground(new Background(
                new BackgroundFill(Color.SNOW,
                        CornerRadii.EMPTY,
                        Insets.EMPTY)));

        Scene scene = new Scene(root);
        stage.setTitle("Прогноз погоды в Санкт-Петербурге.");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }
}
