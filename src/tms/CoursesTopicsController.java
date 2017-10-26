package tms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * This is the Controller class for Courses & Topics scene
 *
 * @author Mahesh Bagde
 */
public class CoursesTopicsController {

    @FXML
    private HBox courseHeaderHBox, addCourseHBox, topicHeaderHBox, addTopicHBox;
    @FXML
    private JFXButton addCourseIconButton, addTopicIconButton,
            addCourseSaveButton, addCourseCancelButton, addTopicSaveButton, addTopicCancelButton;
    @FXML
    private JFXTextField addCourseTextField, addTopicTextField;
    @FXML
    private JFXListView<Course> coursesListView;
    @FXML
    private JFXListView<Topic> topicsListView;

    private TestBank testBank;
    private DatabaseManager db;
    private ObservableList<Course> coursesObservableList;
    private ObservableList<Topic> topicsObservableList;

    /**
     * Initializes the controller class.
     */
    public void initialize() {

        testBank = TestBank.getInstance();
        db = DatabaseManager.getInstance();

        coursesObservableList = FXCollections.observableArrayList(testBank.getCourses());
        coursesListView.setItems(coursesObservableList);
        coursesListView.setExpanded(true);
        coursesListView.setCellFactory((param) -> new CourseCell());

        coursesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            topicsObservableList = FXCollections.observableArrayList(newValue.getTopics());
            topicsListView.setItems(topicsObservableList);
            topicsListView.setExpanded(true);
            topicsListView.setCellFactory((param) -> new TopicCell());
        });

    } // end of method initialize

    /**
     * This method is called when the user clicks on the Save button for Course
     * It reads input from the text field and sends that name as a Course to be
     * added to the database, once the primary key is received back from the
     * database as confirmation of successful creation, the Course is added to
     * the running application.
     *
     * @param event
     */
    @FXML
    private void addNewCourse(ActionEvent event) {
        if (!addCourseTextField.getText().isEmpty()) {
            int newCourseID = db.addCourseToDatabase(addCourseTextField.getText());
            if (newCourseID > 0) {

                testBank.addCourse(newCourseID, addCourseTextField.getText());
                addCourseTextField.clear();

                coursesObservableList = FXCollections.observableArrayList(testBank.getCourses());
                coursesListView.setItems(coursesObservableList);
                coursesListView.setCellFactory((param) -> new CourseCell());

                TranslateTransition outTransition = new TranslateTransition(Duration.millis(600), addCourseHBox);
                System.out.println(addCourseHBox.getHeight() + courseHeaderHBox.getHeight());
                outTransition.setToY(-(addCourseHBox.getHeight() + courseHeaderHBox.getHeight()));
                outTransition.play();
                outTransition.setOnFinished((e) -> {
                    addCourseIconButton.setVisible(true);
                });

            } // end if statement : newCourseID > 0 
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(null);
            alert.getDialogPane().setStyle("-fx-background-color: white;"
                    + "-fx-border-color: #00673E;"
                    + "-fx-border-width: 10;");

            Text text = new Text("Please enter Course name");
            text.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
            text.setFill(Paint.valueOf("#00673E"));

            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(15));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            alert.getDialogPane().setContent(textFlow);
            alert.showAndWait();

        }// end if else statement : addCourseTextField Not Empty
    } // end of method addNewCourse

    /**
     * This method is called when the user clicks on the Save button for Topic
     * It reads input from the text field and sends that name as a Topic to be
     * added to the database, once the primary key is received back from the
     * database as confirmation of successful creation, the Topic is added to
     * the running application.
     *
     * @param event
     */
    @FXML
    private void addNewTopic(ActionEvent event) {
        if (coursesListView.getSelectionModel().getSelectedItem() != null) {
            Course courseSelected = coursesListView.getSelectionModel().getSelectedItem();

            if (!addTopicTextField.getText().isEmpty()) {
                int newTopicID = db.addTopicToDatabase(courseSelected.getCourseID(), addTopicTextField.getText());
                if (newTopicID > 0) {
                    courseSelected.addTopic(newTopicID, courseSelected.getCourseName(), addTopicTextField.getText());
                    addTopicTextField.clear();

                    topicsObservableList = FXCollections.observableArrayList(courseSelected.getTopics());
                    topicsListView.setItems(topicsObservableList);
                    topicsListView.setExpanded(true);
                    topicsListView.setCellFactory((param) -> new TopicCell());

                } // end if statement : newTopicID > 0
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText(null);
                alert.getDialogPane().setStyle("-fx-background-color: white;"
                        + "-fx-border-color: #94C947;"
                        + "-fx-border-width: 10;");

                Text text = new Text("Please enter Topic name");
                text.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
                text.setFill(Paint.valueOf("#94C947"));

                TextFlow textFlow = new TextFlow(text);
                textFlow.setPadding(new Insets(15));
                textFlow.setTextAlignment(TextAlignment.CENTER);
                alert.getDialogPane().setContent(textFlow);
                alert.showAndWait();

            } // end if else statement : addTopicTextField Not Empty

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(null);
            alert.getDialogPane().setStyle("-fx-background-color: white;"
                    + "-fx-border-color: #00673E;"
                    + "-fx-border-width: 10;");

            Text text = new Text("Please select a Course");
            text.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
            text.setFill(Paint.valueOf("#00673E"));

            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(15));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            alert.getDialogPane().setContent(textFlow);
            alert.showAndWait();

        } // end if else statement : courseSelected != null

    } // end of method addNewTopic

    /**
     * This method is called when the user clicks on the + icon to add a new Course
     *
     * @param event
     */
    @FXML
    private void animateAddNewCourse(ActionEvent event) {
        addCourseIconButton.setVisible(false);
        TranslateTransition inTransition = new TranslateTransition(Duration.millis(600), addCourseHBox);
        inTransition.setToY(0);
        inTransition.play();
    } // end of method animateAddNewCourse

    /**
     * This method is called when the user clicks on the cancel button for adding a new Course
     * 
     * @param event 
     */
    @FXML
    private void cancelAddNewCourse(ActionEvent event) {
        addCourseTextField.clear();
        TranslateTransition outTransition = new TranslateTransition(Duration.millis(600), addCourseHBox);
        outTransition.setToY(-(addCourseHBox.getHeight() + courseHeaderHBox.getHeight()));
        outTransition.play();
        outTransition.setOnFinished((e) -> {
            addCourseIconButton.setVisible(true);
        });
    } // end of method cancelAddNewCourse

    /**
     * This method is called when the user clicks on the + icon to add a new Topic
     *
     * @param event
     */
    @FXML
    private void animateAddNewTopic(ActionEvent event) {
        addTopicIconButton.setVisible(false);
        TranslateTransition inTransition = new TranslateTransition(Duration.millis(600), addTopicHBox);
        inTransition.setToY(0);
        inTransition.play();
    } // end of method animateAddNewTopic

    /**
     * This method is called when the user clicks on the cancel button for adding a new Topic
     * 
     * @param event 
     */
    @FXML
    private void cancelAddNewTopic(ActionEvent event) {
        addTopicTextField.clear();
        TranslateTransition outTransition = new TranslateTransition(Duration.millis(600), addTopicHBox);
        outTransition.setToY(-(addTopicHBox.getHeight() + topicHeaderHBox.getHeight()));
        outTransition.play();
        outTransition.setOnFinished((e) -> {
            addTopicIconButton.setVisible(true);
        });
    } // end of method cancelAddNewTopic

} // end of class CoursesTopicsController
