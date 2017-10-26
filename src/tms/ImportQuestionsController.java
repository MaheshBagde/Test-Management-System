package tms;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
 * This is the Controller class for Import Questions scene
 *
 * @author Mahesh Bagde
 */
public class ImportQuestionsController {

    @FXML
    private ComboBox<Course> coursesComboBox;
    @FXML
    private ComboBox<Topic> topicsComboBox;
    @FXML
    private JFXListView<Question> questionsListView;
    @FXML
    private JFXButton OpenFileButton, saveButton, cancelButton;
    @FXML
    private HBox topHBox;
    @FXML
    private JFXProgressBar progressBar;

    private ObservableList<Course> coursesObservableList;
    private ObservableList<Topic> topicsObservableList;
    private ObservableList<Question> questionsObservableList;
    private TestBank testBank;
    private DatabaseManager db;
    private BorderPane root = Main.getRoot();
    private ArrayList<Question> questionsImported;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        testBank = TestBank.getInstance();
        db = DatabaseManager.getInstance();
        progressBar.setProgress(0);
        topHBox.getChildren().removeAll(progressBar);

        coursesObservableList = FXCollections.observableArrayList(testBank.getCourses());
        coursesComboBox.setItems(coursesObservableList);
        coursesComboBox.setCellFactory((param) -> new CourseCell());
        coursesComboBox.setButtonCell(new CourseButtonCell());

        coursesComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            topicsObservableList = FXCollections.observableArrayList(newValue.getTopics());
            topicsComboBox.setItems(topicsObservableList);
            topicsComboBox.setCellFactory((param) -> new TopicCell());
            topicsComboBox.setButtonCell(new TopicButtonCell());

        }); // end of Listener

    } // end of method initialize   

    /**
     * This method open the file explorer window of the system for the user to select the text file to be imported
     */
    @FXML
    private void chooseFileToImportQuestions() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to import questions into Test Bank");
        fileChooser.setInitialDirectory(new File("C:\\Users\\User\\Documents\\Test Management System"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(" Text Files ", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            questionsObservableList = FXCollections.observableArrayList(importQuestionsFromTextFile(selectedFile));
            questionsListView.setItems(questionsObservableList);
            questionsListView.setCellFactory((param) -> new ImportQuestionsCell());
        }  // end if statement

    } // end of method chooseFileToImportQuestions

    /**
     * This method reads from the text file and builds an ArrayList of Question objects
     * 
     * @param file the text file selected by the user to import questions from
     * @return a collection of Questions to be imported into the test bank
     */
    private ArrayList<Question> importQuestionsFromTextFile(File file) {
        boolean isQuestion = true;
        String questionDescription = null;
        questionsImported = new ArrayList<>();
        ArrayList<Answer> multipleChoiceAnswers = new ArrayList<>();

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (!line.isEmpty()) {
                    if (isQuestion) {
                        questionDescription = line;
                        isQuestion = false;
                    } else {
                        if (line.startsWith("*")) {
                            multipleChoiceAnswers.add(new Answer(0, line, true));
                        } else {
                            multipleChoiceAnswers.add(new Answer(0, line, false));
                        } // end if else statement - check if answer is true or false
                    } // end if else statement - check if it is a question
                } else {
                    isQuestion = true;
                    questionsImported.add(new Question(questionDescription, multipleChoiceAnswers));
                    multipleChoiceAnswers = new ArrayList<>();
                } // end if else statement - check if line if blank
            } // end while loop - did not reach EOF

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
        } // end of try catch block

        return questionsImported;
    } // end of method importQuestionsFromTextFile

    /**
     * This method is called when the user clicks on the save button
     * The imported questions are saved into the database and upon receiving confirmation of successful creation
     * the questions are uploaded into the application (test bank)
     * Note : the user needs to make all the required selection before clicking on the save button, in case the user 
     * misses any selection an alert box is displayed with the relevant information
     * 
     * @param event 
     */
    @FXML
    private void saveImportedQuestions(ActionEvent event) {

        Course courseSelected = coursesComboBox.getSelectionModel().getSelectedItem();
        Topic topicSelected = topicsComboBox.getSelectionModel().getSelectedItem();

        if (questionsImported == null) { // user did not choose a text file for importing questions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setHeaderText(null);
            alert.getDialogPane().setStyle("-fx-background-color: white;"
                    + "-fx-border-color: #00673E;"
                    + "-fx-border-width: 10;");

            Text text = new Text("Open File Location and select file to import questions");
            text.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
            text.setFill(Paint.valueOf("#00673E"));

            TextFlow textFlow = new TextFlow(text);
            textFlow.setPadding(new Insets(15));
            textFlow.setTextAlignment(TextAlignment.CENTER);
            alert.getDialogPane().setContent(textFlow);
            alert.showAndWait();

        } else {
            if (courseSelected == null) {  // user did not choose a Course 
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText(null);
                alert.getDialogPane().setStyle("-fx-background-color: white;"
                        + "-fx-border-color: #00673E;"
                        + "-fx-border-width: 10;");

                Text text = new Text("Please select a Course to add questions");
                text.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
                text.setFill(Paint.valueOf("#00673E"));

                TextFlow textFlow = new TextFlow(text);
                textFlow.setPadding(new Insets(15));
                textFlow.setTextAlignment(TextAlignment.CENTER);
                alert.getDialogPane().setContent(textFlow);
                alert.showAndWait();

            } else {
                if (topicSelected == null) { // user did not choose a Topic
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.setHeaderText(null);
                    alert.getDialogPane().setStyle("-fx-background-color: white;"
                            + "-fx-border-color: #00673E;"
                            + "-fx-border-width: 10;");

                    Text text = new Text("Please select a Topic to add questions");
                    text.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
                    text.setFill(Paint.valueOf("#00673E"));

                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setPadding(new Insets(15));
                    textFlow.setTextAlignment(TextAlignment.CENTER);
                    alert.getDialogPane().setContent(textFlow);
                    alert.showAndWait();

                } else {
                    int courseIndex = testBank.getCourses().indexOf(courseSelected);
                    int topicIndex = testBank.getCourses().get(courseIndex).getTopics().indexOf(topicSelected);

                    topHBox.getChildren().removeAll(OpenFileButton, coursesComboBox, topicsComboBox, saveButton, cancelButton);
                    topHBox.getChildren().addAll(progressBar);

                    Task<Void> task = new Task<Void>() {
                        int count = 0;

                        @Override
                        protected Void call() throws Exception {

                            for (Question q : questionsImported) {
                                count++;
                                ArrayList<Answer> ans = new ArrayList<>();
                                int questionID = db.addQuestionToDatabase(topicSelected.getTopicID(), q.getQuestionDescription());

                                if (questionID > 0) {
                                    for (Answer a : q.getMultipleChoiceAnswers()) {
                                        int correctAnswer = (a.isCorrectAnswer()) ? 1 : 0;
                                        String answerChoice = (a.getAnswerChoice());

                                        if (a.isCorrectAnswer()) {
                                            StringBuilder stringBuilder = new StringBuilder(a.getAnswerChoice());
                                            stringBuilder.deleteCharAt(0);
                                            answerChoice = stringBuilder.toString();
                                        } // end if statement : correct answer

                                        int answerID = db.addAnswerToDatabase(questionID, answerChoice, correctAnswer);

                                        if (answerID > 0) {
                                            ans.add(new Answer(answerID, answerChoice, a.isCorrectAnswer()));
                                        } // end if statement : answerID > 0 

                                    } // end for each Answer loop

                                    testBank.getCourses().get(courseIndex).getTopics().get(topicIndex)
                                            .addQuestion(questionID,
                                                    courseSelected.getCourseName(),
                                                    topicSelected.getTopicName(),
                                                    q.getQuestionDescription(),
                                                    ans);

                                } // end if statement : questionID > 0

                                updateProgress(count, questionsImported.size());
                            } // end for each Question loop

                            return null;
                        } // end of method call
                    }; // end of Task

                    progressBar.progressProperty().bind(task.progressProperty());

                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();

                    task.setOnSucceeded((e) -> { // show summary of question uploaded into the test bank
                        questionsListView.getItems().clear();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initStyle(StageStyle.UNDECORATED);
                        alert.setHeaderText(null);
                        alert.getDialogPane().setStyle("-fx-background-color: white;"
                                + "-fx-border-color: #00673E;"
                                + "-fx-border-width: 10;");

                        Text numberText = new Text(String.valueOf(questionsImported.size()));
                        numberText.setFont(Font.font("Elephant", FontWeight.BOLD, FontPosture.REGULAR, 45));
                        numberText.setFill(Paint.valueOf("#00673E"));

                        Text text = new Text("  Questions added to Test Bank");
                        text.setFont(Font.font("SansSerif", FontWeight.MEDIUM, FontPosture.REGULAR, 35));
                        text.setFill(Paint.valueOf("#00673E"));

                        TextFlow textFlow = new TextFlow(numberText, text);
                        textFlow.setPadding(new Insets(15));
                        textFlow.setTextAlignment(TextAlignment.CENTER);
                        alert.getDialogPane().setContent(textFlow);
                        alert.showAndWait();

                        try {
                            root.setCenter(FXMLLoader.load(getClass().getResource("ImportQuestions.fxml")));
                        } catch (IOException ex) {
                            Logger.getLogger(ImportQuestionsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }); // end of Task Completed

                } // end if else statement : Topic is null
            } // end if else statement : Course is null
        } // end if else statement : questionsImported is null

    } // end of method saveImportedQuestions

    /**
     * This method is called when the user clicks on the cancel button
     * 
     * @param event 
     */
    @FXML
    private void clearImportedQuestions(ActionEvent event) {
        questionsListView.getItems().clear();
    } // end of method clearImportedQuestions

} // end of class ImportQuestionsController
