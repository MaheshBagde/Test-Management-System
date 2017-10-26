package tms;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

/**
 * This is the Controller class for View Questions scene
 *
 * @author Mahesh Bagde
 */
public class ViewQuestionsController {

    @FXML
    private ComboBox<Course> coursesComboBox;
    @FXML
    private ComboBox<Topic> topicsComboBox;
    @FXML
    private JFXListView<Question> questionsListView;

    private TestBank testBank;
    private ObservableList<Course> coursesObservableList;
    private ObservableList<Topic> topicsObservableList;
    private ObservableList<Question> questionsObservableList;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        testBank = TestBank.getInstance();
        coursesObservableList = FXCollections.observableArrayList(testBank.getCourses());
        coursesComboBox.setItems(coursesObservableList);
        coursesComboBox.setCellFactory((param) -> new CourseCell());
        coursesComboBox.setButtonCell(new CourseButtonCell());

        coursesComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            topicsObservableList = FXCollections.observableArrayList(newValue.getTopics());
            topicsComboBox.setItems(topicsObservableList);
            topicsComboBox.setCellFactory((param) -> new TopicCell());
            topicsComboBox.setButtonCell(new TopicButtonCell());
        }); // end of coursesComboBox Listener

        topicsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!topicsComboBox.getSelectionModel().isEmpty()) {
                questionsObservableList = FXCollections.observableArrayList(newValue.getQuestions());
                questionsListView.setItems(questionsObservableList);
                questionsListView.setCellFactory((param) -> new QuestionCell());
            } // end if statement : topicsComboBox has a selection          

        });



    } // end of method initialize    

} // end of class ViewQuestionsController
