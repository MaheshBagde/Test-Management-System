package tms;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * This is the Controller class for Menu options
 *
 * @author Mahesh Bagde
 */
public class MenuController {

    @FXML
    private VBox menuBase;
    @FXML
    private JFXButton courseTopicButton, importQuestionsButton, viewTestBankButton, createTestButton, closeApplicationButton;
    private BorderPane root = Main.getRoot();

    /**
     * Initializes the controller class.
     */
    public void initialize() {

    } // end of method initialize       

    @FXML
    private void loadCourseAndTopicScene(ActionEvent event) throws IOException {
        root.setCenter(FXMLLoader.load(getClass().getResource("CoursesTopics.fxml")));
    } // end of method 

    @FXML
    private void loadImportQuestionsScene(ActionEvent event) throws IOException {
        root.setCenter(FXMLLoader.load(getClass().getResource("ImportQuestions.fxml")));
    } // end of method 

    @FXML
    private void loadViewTestBankScene(ActionEvent event) throws IOException {
        root.setCenter(FXMLLoader.load(getClass().getResource("ViewQuestions.fxml")));
    } // end of method 

    @FXML
    private void loadCreateTestScene(ActionEvent event) throws IOException {
        root.setCenter(FXMLLoader.load(getClass().getResource("CreateTest.fxml")));
    } // end of method 

    @FXML
    private void closeApplication(ActionEvent event) {
        System.exit(0);
    } // end of method 

} // end of class MenuController
