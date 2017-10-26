package tms;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * This class is the main entry point for the JavaFX application
 *
 * @author Mahesh Bagde
 */
public class Main extends Application {

    private static StackPane base = new StackPane();
    private static BorderPane root = new BorderPane();
    private TestBank testBank = TestBank.getInstance();
    private DatabaseManager db = DatabaseManager.getInstance();

    @FXML
    private VBox menuBase;
    @FXML
    private JFXButton courseTopicButton, importQuestionsButton, viewTestBankButton, createTestButton, closeApplicationButton;

    @Override
    public void start(Stage stage) throws Exception {
        base.getChildren().add(root);
        root.setCenter(FXMLLoader.load(getClass().getResource("Home.fxml")));
        Scene scene = new Scene(base);
        scene.getStylesheets().add("tms/application.css");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.show();

        final VBox menu = FXMLLoader.load(getClass().getResource("Menu.fxml"));

        /* This event handler manages the display of menu. When the mouse goes to the left edge of the screen the menu 
        will be displayed and when the mouse goes away from the left edge of the screen the menu will be hidden*/
        root.addEventFilter(MouseEvent.MOUSE_MOVED, (event) -> {
            if (event.getScreenX() < 10) {
                if (!base.getChildren().contains(menu)) {
                    base.getChildren().add(menu);
                    base.setAlignment(menu, Pos.CENTER_LEFT);
                    TranslateTransition menuBaseMoveIn = new TranslateTransition(Duration.millis(400), menu);
                    menuBaseMoveIn.setToX(0);
                    menuBaseMoveIn.play();
                }
            } else {
                if (base.getChildren().contains(menu)) {
                    TranslateTransition menuBaseMoveOut = new TranslateTransition(Duration.millis(400), menu);
                    menuBaseMoveOut.setToX(-(menu.getWidth()));
                    menuBaseMoveOut.play();
                    menuBaseMoveOut.setOnFinished((e) -> {
                        base.getChildren().remove(menu);
                    });
                }
            }
        });
    } // end of method start

    public static BorderPane getRoot() {
        return root;
    } // end of method getRoot

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    } // end of method main

} // end of class Main
