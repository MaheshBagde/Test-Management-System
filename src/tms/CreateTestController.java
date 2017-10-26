package tms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

/**
 * This is the Controller class for Create Test scene
 *
 * @author Mahesh Bagde
 */
public class CreateTestController {

    @FXML
    private ComboBox<Course> coursesComboBox;
    @FXML
    private ComboBox<Topic> topicsComboBox;
    @FXML
    private JFXListView<Question> questionsListView, testListView;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private ComboBox<Term> termComboBox;
    @FXML
    private Label testLabel, testQuestionCountLabel;
    @FXML
    private ComboBox<TestType> typeComboBox;
    @FXML
    private HBox courseTopicHBox, createTestHBox;
    @FXML
    private JFXButton startButton, changeViewButton, saveButton, cancelButton, downloadButton;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Region region;
    @FXML
    private FontAwesomeIconView changeViewIcon;
    @FXML
    private Tooltip changeViewToolTip;

    private TestBank testBank;
    private DatabaseManager db;
    private BorderPane root = Main.getRoot();
    private ObservableList<Course> coursesObservableList;
    private ObservableList<Topic> topicsObservableList;
    private ObservableList<Question> questionsObservableList;
    Integer yearSelected;
    Term termSelected;
    TestType typeSelected;
    boolean editMode;
    boolean expandedView = false;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        testBank = TestBank.getInstance();
        db = DatabaseManager.getInstance();

        /* Removing all user interface controls that are not relevant at the start of this scene. 
        They get added systematically as the user selects the required options.*/
        courseTopicHBox.getChildren().removeAll(coursesComboBox, topicsComboBox);
        createTestHBox.getChildren().removeAll(typeComboBox, startButton, testLabel,
                testQuestionCountLabel, region, changeViewButton, saveButton, cancelButton, downloadButton);
        initializeYearTermTypeComboBox();

        topicsComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            questionsObservableList = FXCollections.observableArrayList(newValue.getQuestions());
            questionsListView.setItems(questionsObservableList);

            /* checks for questions that are already selected for the test and removes them from the list of questions available
            to be selected. This is important to avoid duplication, as the same question should not be selected again */
            for (Question q : testListView.getItems()) {
                if (questionsListView.getItems().contains(q)) {
                    questionsListView.getItems().remove(q);
                } // end if statement
            } // end for loop

            questionsListView.setCellFactory((param) -> new QuestionCell());
            testListView.setCellFactory((param) -> new QuestionCollapsedCell());
        }); // end of topicsComboBox Listener

        /* this event handler (on mouse double click) moves a question from the list of questions available for selection
        to the list of questions selected for the test */
        questionsListView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getClickCount() == 2) {
                Question questionSelected = questionsListView.getSelectionModel().getSelectedItem();
                questionsListView.getItems().remove(questionSelected);
                testListView.getItems().add(questionSelected);
                testListView.scrollTo(testListView.getItems().size() - 1);
                testQuestionCountLabel.setText(String.valueOf(testListView.getItems().size()));
            } // end if statement : mouse double click
        }); // end of mouse event handler on questionsListView

        /* this event handler (on mouse double click) moves a question from the list of questions selected for the test
        to the list of questions available for selection. This happens when the users change their decision and want to 
        remove it from the test. */
        testListView.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (event.getClickCount() == 2) {
                Question questionSelected = testListView.getSelectionModel().getSelectedItem();
                testListView.getItems().remove(questionSelected);
                testQuestionCountLabel.setText(String.valueOf(testListView.getItems().size()));

                /* Checks if the question to be removed from the test is part of the current topic selected and adds it back only if true. 
                This is important because the left side of the user interface is displaying a list of questions to be selected from a 
                particular Topic, and if the user decides to remove a question which is not from the Topic being displayed, visually it 
                would seem that the removed question is being added to a different Topic. */
                if (topicsComboBox.getSelectionModel().getSelectedItem().getQuestions().contains(questionSelected)) {
                    questionsListView.getItems().add(questionSelected);
                } // end if statement

            } // end if statement : mouse double click
        }); // end of mouse event handler on testListView

    } // end of method initialize    

    /**
     * This method initializes the Year, Term and Test Type Combo Box UI controls
     */
    private void initializeYearTermTypeComboBox() {
        Integer yearBeforeLast = LocalDate.now().minusYears(2).getYear();
        Integer lastYear = LocalDate.now().minusYears(1).getYear();
        Integer thisYear = LocalDate.now().getYear();
        ArrayList<Integer> years = new ArrayList<>();
        years.add(yearBeforeLast);
        years.add(lastYear);
        years.add(thisYear);
        yearComboBox.getItems().setAll(years);
        yearComboBox.getSelectionModel().select(thisYear);
        yearComboBox.setCellFactory((param) -> new YearCell());
        yearComboBox.setButtonCell(new YearButtonCell());
        termComboBox.getItems().setAll(Term.values());
        termComboBox.setCellFactory((param) -> new TermCell());
        termComboBox.setButtonCell(new TermButtonCell());
        typeComboBox.getItems().setAll(TestType.values());
        typeComboBox.setCellFactory((param) -> new TypeCell());
        typeComboBox.setButtonCell(new TypeButtonCell());

        termComboBox.getSelectionModel().selectedItemProperty().addListener((termObservable) -> {
            createTestHBox.getChildren().add(typeComboBox); // duplicate children added:
            typeComboBox.getSelectionModel().selectedItemProperty().addListener((topicObservable) -> {
                createTestHBox.getChildren().add(startButton);
            }); // end of typeComboBox Listener
        }); // end of termComboBox Listener

    } // end of method initializeYearTermTypeComboBox

    /**
     * This method is called when the user clicks on the Save test button
     * 1 - questions previously saved for this test are retrieved from the database
     * 2 - The modified or new selection of questions are compared with the previously saved list
     * 3 - New questions which were not in the previously saved test list are added to the database
     * 4 - Questions previously saved for this test, but removed from the modified or new selection of questions 
     * are removed from the database.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void saveTest(ActionEvent event) throws IOException {
        yearSelected = yearComboBox.getSelectionModel().getSelectedItem();
        termSelected = termComboBox.getSelectionModel().getSelectedItem();
        typeSelected = typeComboBox.getSelectionModel().getSelectedItem();
        ArrayList<Question> testQuestionsPreviouslySaved
                = db.getSavedTestQuestionsFromDatabase(yearSelected, termSelected, typeSelected);

        int added = 0; // this information is used to display a summary on the Alert box
        int removed = 0; // this information is used to display a summary on the Alert box

        if (editMode == true) {

            // Adds newly selected questions which were not in the previously saved test list 
            for (Question newQuestion : testListView.getItems()) {
                if (!testQuestionsPreviouslySaved.contains(newQuestion)) {
                    boolean testLabelExists = false;

                    // Check if the Test Label already exists in the Question 
                    for (TestLabel testLabelExisting : newQuestion.getTestLabels()) {
                        if (testLabelExisting.getYear() == yearSelected && testLabelExisting.getTerm() == termSelected
                                && testLabelExisting.getTestType() == typeSelected) {
                            testLabelExists = true;
                        } // end if statement : Test Label exists in Question
                    } // end for loop : check every Test Label in Question

                    // Add Test Label to Question
                    if (testLabelExists == false) {
                        int newTestLabelID = db.addTestLabelToDatabase(newQuestion.getQuestionID(), yearSelected,
                                String.valueOf(termSelected), String.valueOf(typeSelected));
                        if (newTestLabelID > 0) {
                            newQuestion.addTestLabel(newTestLabelID, yearSelected, termSelected, typeSelected);
                            added++;
                        } // end if statement : newTestLabelID > 0
                    }  // end if statement : Test Label does not exist in Question

                } // end if statement : New Question found in testListView

            } // end for loop : check every Question in testListView

            // Remove questions that were in the previously saved test list, but have been removed from the new test list
            for (Question existingQuestion : testQuestionsPreviouslySaved) {
                if (!testListView.getItems().contains(existingQuestion)) {

                    // Remove Test Label from the Question
                    for (TestLabel testLabel : existingQuestion.getTestLabels()) {
                        if (testLabel.getYear() == yearSelected
                                && testLabel.getTerm() == termSelected
                                && testLabel.getTestType() == typeSelected) {
                            if (db.deleteTestLabelInDatabase(testLabel.getTestLabelID()) == 1) {
                                existingQuestion.getTestLabels().remove(testLabel);
                                removed++;
                            } // end if transaction successful
                            break;
                        } // end if statement : Test Label found in Question
                    } // end for loop : check every Test Label in Question
                } // end if statement : previously saved Question missing in new testListView
            } // end for loop : check every Question in previously saved test list

        } else { // if editMode == false

            // Add selected Questions to the database
            for (Question question : testListView.getItems()) {
                boolean testLabelExists = false;

                // Check if the Test Label already exists in the Question 
                for (TestLabel testLabelExisting : question.getTestLabels()) {
                    if (testLabelExisting.getYear() == yearSelected && testLabelExisting.getTerm() == termSelected
                            && testLabelExisting.getTestType() == typeSelected) {
                        testLabelExists = true;
                    } // end if statement : Test Label exists in Question
                } // end for loop : check every Test Label in Question

                // Add Test Label to Question
                if (testLabelExists == false) {
                    int newTestLabelID = db.addTestLabelToDatabase(question.getQuestionID(), yearSelected,
                            String.valueOf(termSelected), String.valueOf(typeSelected));
                    if (newTestLabelID > 0) {
                        question.addTestLabel(newTestLabelID, yearSelected, termSelected, typeSelected);
                        added++;
                    } // end if statement : newTestLabelID > 0
                }  // end if statement : Test Label does not exist in Question

            } // end for loop : check every Question in testListView

            // Remove questions that were in the previously saved test list, but have been removed from the new test list
            for (Question existingQuestion : testQuestionsPreviouslySaved) {
                if (!testListView.getItems().contains(existingQuestion)) {

                    // Remove Test Label from the Question
                    for (TestLabel testLabel : existingQuestion.getTestLabels()) {
                        if (testLabel.getYear() == yearSelected
                                && testLabel.getTerm() == termSelected
                                && testLabel.getTestType() == typeSelected) {
                            if (db.deleteTestLabelInDatabase(testLabel.getTestLabelID()) == 1) {
                                existingQuestion.getTestLabels().remove(testLabel);
                                removed++;
                            } // end if transaction successful
                            break;
                        } // end if statement : Test Label found in Question
                    } // end for loop : check every Test Label in Question
                } // end if statement : previously saved Question missing in new testListView
            } // end for loop : check every Question in previously saved test list

        } // end if else statement : editMode true or false

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.getDialogPane().setStyle("-fx-background-color: white;"
                + "-fx-border-color: #00673E;"
                + "-fx-border-width: 10;");

        Text textAdded = new Text("Questions added : ");
        textAdded.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
        textAdded.setFill(Paint.valueOf("#00673E"));

        Text numberAdded = new Text(String.valueOf(added) + "\n");
        numberAdded.setFont(Font.font("Elephant", FontWeight.BOLD, FontPosture.REGULAR, 45));
        numberAdded.setFill(Paint.valueOf("#00673E"));

        Text textRemoved = new Text("Questions removed : ");
        textRemoved.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
        textRemoved.setFill(Paint.valueOf("#00673E"));

        Text numberRemoved = new Text(String.valueOf(removed));
        numberRemoved.setFont(Font.font("Elephant", FontWeight.BOLD, FontPosture.REGULAR, 45));
        numberRemoved.setFill(Paint.valueOf("#00673E"));

        TextFlow textFlow = new TextFlow(textAdded, numberAdded, textRemoved, numberRemoved);
        textFlow.setPadding(new Insets(10, 30, 10, 10));
        textFlow.setTextAlignment(TextAlignment.LEFT);
        alert.getDialogPane().setContent(textFlow);
        alert.showAndWait();

    } // end of method saveTest

    /**
     * This method is called when the user clicks on the cancel button
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void clearTest(ActionEvent event) throws IOException {
        root.setCenter(FXMLLoader.load(getClass().getResource("CreateTest.fxml")));
    } // end of method clearTest

    /**
     * This method is called when the user clicks on the download icon and the test is saved in a word doc
     * The user can select the location to save the test and can change the suggested name of the file which is 
     * the Year : Term : Test Type combined
     * Note: If the test has not been saved the download will not reflect the updated version
     * 
     * @param event 
     */
    @FXML
    private void downloadTest(ActionEvent event) {
        yearSelected = yearComboBox.getSelectionModel().getSelectedItem();
        termSelected = termComboBox.getSelectionModel().getSelectedItem();
        typeSelected = typeComboBox.getSelectionModel().getSelectedItem();
        ArrayList<Question> questionsOnTest
                = db.getSavedTestQuestionsFromDatabase(yearSelected, termSelected, typeSelected);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose folder to save test");
        fileChooser.setInitialFileName(yearSelected + " " + termSelected + " " + typeSelected + " ");
        fileChooser.setInitialDirectory(new File("C:\\Users\\User\\Documents\\Test Management System"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(" Text Files ", "*.doc"));

        File fileName = fileChooser.showSaveDialog(null);

        if (fileName != null) {
            Path target = Paths.get(fileName.getAbsolutePath());

            Charset charset = Charset.forName("US-ASCII");

            try (BufferedWriter writer = Files.newBufferedWriter(target, charset)) {
                for (Question question : questionsOnTest) {
                    writer.write(question.getQuestionDescription());
                    writer.newLine();
                    for (Answer answer : question.getMultipleChoiceAnswers()) {
                        writer.write(answer.getAnswerChoice());
                        writer.newLine();
                    } // end for each Answer loop
                    writer.newLine();
                } // end for each Question loop

            } catch (Exception e) {
                System.out.println(e.getMessage());
            } // end try catch block
        } // end if statement : fileName is Not Null

    } // end of method downloadTest

    /**
     * This method is called when the user clicks on the Start button
     * It first determines if the year : term : test type combination has an already saved test, in which case the 
     * edit mode is set to 'true' and a list of Topics is shown for the Course
     * If the year : term : test type combination does not exist then the edit mode is set to 'false' and 
     * first a list of Courses is shown for selection and then the Topics for that Course is loaded
     * 
     * @param event 
     */
    @FXML
    private void startTest(ActionEvent event) {
        yearSelected = yearComboBox.getSelectionModel().getSelectedItem();
        termSelected = termComboBox.getSelectionModel().getSelectedItem();
        typeSelected = typeComboBox.getSelectionModel().getSelectedItem();
        ArrayList<Question> testQuestionsPreviouslySaved
                = db.getSavedTestQuestionsFromDatabase(yearSelected, termSelected, typeSelected);

        if (testQuestionsPreviouslySaved.size() > 0) {
            editMode = true;
            Course testCourse = null;

            String courseName = testQuestionsPreviouslySaved.get(0).getCourseName();
            for (Course course : testBank.getCourses()) {
                if (course.getCourseName().equals(courseName)) {
                    testCourse = course;
                    break;
                } // end if statement
            } // end for loop

            for (Question questionOnTest : testQuestionsPreviouslySaved) {
                testListView.getItems().add(questionOnTest);
            } // end for loop

            courseTopicHBox.getChildren().add(topicsComboBox);
            splitPane.setDividerPositions(0.4);

            /* The UI controls not relevant at this stage of the scene are removed and the relevant ones are added */
            createTestHBox.getChildren().removeAll(yearComboBox, termComboBox, typeComboBox, startButton);
            createTestHBox.getChildren().addAll(testLabel, testQuestionCountLabel, region,
                    changeViewButton, saveButton, cancelButton, downloadButton);
            
            testLabel.setText(String.valueOf(yearSelected) + " - " + String.valueOf(termSelected) + " - "
                    + String.valueOf(typeSelected) + " :");
            testQuestionCountLabel.setText(String.valueOf(testListView.getItems().size()));
            topicsObservableList = FXCollections.observableArrayList(testCourse.getTopics());
            topicsComboBox.setItems(topicsObservableList);
            topicsComboBox.setCellFactory((param) -> new TopicCell());
            topicsComboBox.setButtonCell(new TopicButtonCell());
            testListView.setCellFactory((param) -> new QuestionCollapsedCell());

        } else {
            editMode = false;

            courseTopicHBox.getChildren().add(coursesComboBox);
            splitPane.setDividerPositions(0.4);

            /* The UI controls not relevant at this stage of the scene are removed and the relevant ones are added */
            createTestHBox.getChildren().removeAll(yearComboBox, termComboBox, typeComboBox, startButton);
            createTestHBox.getChildren().addAll(testLabel, testQuestionCountLabel, region,
                    changeViewButton, saveButton, cancelButton, downloadButton);
            
            testLabel.setText(String.valueOf(yearSelected) + " - " + String.valueOf(termSelected) + " - "
                    + String.valueOf(typeSelected) + " :");
            coursesObservableList = FXCollections.observableArrayList(testBank.getCourses());
            coursesComboBox.setItems(coursesObservableList);
            coursesComboBox.setCellFactory((param) -> new CourseCell());
            coursesComboBox.setButtonCell(new CourseButtonCell());

            coursesComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                topicsObservableList = FXCollections.observableArrayList(newValue.getTopics());
                topicsComboBox.setItems(topicsObservableList);
                topicsComboBox.setCellFactory((param) -> new TopicCell());
                topicsComboBox.setButtonCell(new TopicButtonCell());
                courseTopicHBox.getChildren().remove(coursesComboBox);
                courseTopicHBox.getChildren().add(topicsComboBox);
            }); // end of coursesComboBox Listener

        } // end if else statement
    } // end of method startTest

    /**
     * This method controls the view of the questions selected on the test, and toggles between an expanded
     * and collapsed view
     * 
     * @param event 
     */
    @FXML
    private void changeTestView(ActionEvent event) {
        if (expandedView) {
            testListView.setCellFactory((param) -> new QuestionCollapsedCell());
            expandedView = false;
            changeViewIcon.setGlyphName("EXPAND");
            changeViewToolTip.setText("Expand View");
        } else {
            testListView.setCellFactory((param) -> new QuestionCell());
            expandedView = true;
            changeViewIcon.setGlyphName("COMPRESS");
            changeViewToolTip.setText("Colapse View");
        } // end if else statement
    } // end of method changeTestView

} // end of class CreateTestController
