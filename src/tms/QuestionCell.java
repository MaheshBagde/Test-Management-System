package tms;

import com.jfoenix.controls.JFXListCell;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This class specifies the style for a custom cell in a list view for Questions
 *
 * @author Mahesh Bagde
 */
public class QuestionCell extends JFXListCell<Question> {

    @FXML
    private BorderPane questionCellBase;
    @FXML
    private Label topicLabel, questionLabel, yearBeforeLastLabel, lastYearLabel, thisYearLabel;
    @FXML
    private VBox answersVBox;
    @FXML
    private FlowPane thisYearFall, thisYearWinter, thisYearSpring,
            lastYearFall, lastYearWinter, lastYearSpring,
            yearBeforeLastFall, yearBeforeLastWinter, yearBeforeLastSpring;

    public QuestionCell() {
        this.setPadding(new Insets(3, 5, 3, 5));
    } // end of constructor

    @Override
    public void updateItem(Question question, boolean empty) {
        super.updateItem(question, empty);

        if (empty || question == null) {
            setGraphic(null); // don't display anything
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(CourseCell.class.getName()).log(Level.SEVERE, null, ex);
            } // end of try catch block

            topicLabel.setText(question.getTopicName());
            questionLabel.setText(question.getQuestionDescription());
            yearBeforeLastLabel.setText(String.valueOf(LocalDate.now().minusYears(2).getYear()));
            lastYearLabel.setText(String.valueOf(LocalDate.now().minusYears(1).getYear()));
            thisYearLabel.setText(String.valueOf(LocalDate.now().getYear()));

            updateTestHistory(question);

            for (Answer ans : question.getMultipleChoiceAnswers()) {
                
                Text icon = GlyphsDude.createIcon(FontAwesomeIcon.CHECK_CIRCLE, "20px");
                icon.setFill(Paint.valueOf("#00673E"));
                
                Label answerLabel = new Label(ans.getAnswerChoice());
                answerLabel.setMinWidth(USE_PREF_SIZE);
                answerLabel.setPrefWidth(1);
                answerLabel.setMaxWidth(Double.MAX_VALUE);
                
                answerLabel.setWrapText(true);
                answerLabel.setPadding(new Insets(0, 0, 0, 50));
                answerLabel.setTextFill(Paint.valueOf("#00673E"));
                answerLabel.setFont(Font.font("Ariel Rounded MT Bold", FontPosture.REGULAR, 18));

                if (ans.isCorrectAnswer()) {
                    answerLabel.setGraphic(icon); // this check mark icon is shown only if it is the correct answer
                    answerLabel.setFont(Font.font("Ariel Rounded MT Bold", FontWeight.BOLD, FontPosture.REGULAR, 18));
                    answerLabel.setPadding(new Insets(0, 0, 0, 30));
                } // end if statement

                answersVBox.getChildren().add(answerLabel);
            } // end for loop

            setGraphic(questionCellBase);

        } // end if else statement

    } // end of method updateItem

    /**
     * This method loops through all the test labels for a given Question and updates the grid with the 
     * test types that this Question was included with
     * 
     * @param question the Question object for which its test history needs to be searched
     */
    private void updateTestHistory(Question question) {
        Integer thisYear = LocalDate.now().getYear();
        Integer lastYear = LocalDate.now().minusYears(1).getYear();
        Integer yearBeforeLast = LocalDate.now().minusYears(2).getYear();

        ArrayList<Text> thisYearFallList = new ArrayList<>();
        ArrayList<Text> thisYearWinterList = new ArrayList<>();
        ArrayList<Text> thisYearSpringList = new ArrayList<>();
        ArrayList<Text> lastYearFallList = new ArrayList<>();
        ArrayList<Text> lastYearWinterList = new ArrayList<>();
        ArrayList<Text> lastYearSpringList = new ArrayList<>();
        ArrayList<Text> yearBeforeLastFallList = new ArrayList<>();
        ArrayList<Text> yearBeforeLastWinterList = new ArrayList<>();
        ArrayList<Text> yearBeforeLastSpringList = new ArrayList<>();

        if (!question.getTestLabels().isEmpty()) {
            for (TestLabel testLabel : question.getTestLabels()) {

                if (testLabel.getYear() == thisYear && testLabel.getTerm() == Term.FALL) {
                    thisYearFallList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if This Year && Fall

                if (testLabel.getYear() == thisYear && testLabel.getTerm() == Term.WINTER) {
                    thisYearWinterList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if This Year && Winter

                if (testLabel.getYear() == thisYear && testLabel.getTerm() == Term.SPRING) {
                    thisYearSpringList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if This Year && Spring

                if (testLabel.getYear() == lastYear && testLabel.getTerm() == Term.FALL) {
                    lastYearFallList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if Last Year && Fall

                if (testLabel.getYear() == lastYear && testLabel.getTerm() == Term.WINTER) {
                    lastYearWinterList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if Last Year && Winter

                if (testLabel.getYear() == lastYear && testLabel.getTerm() == Term.SPRING) {
                    lastYearSpringList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if Last Year && Spring

                if (testLabel.getYear() == yearBeforeLast && testLabel.getTerm() == Term.FALL) {
                    yearBeforeLastFallList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if Year Before Last && Fall

                if (testLabel.getYear() == yearBeforeLast && testLabel.getTerm() == Term.WINTER) {
                    yearBeforeLastWinterList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if Year Before Last && Winter

                if (testLabel.getYear() == yearBeforeLast && testLabel.getTerm() == Term.SPRING) {
                    yearBeforeLastSpringList.add(applyColorToTestLabel(new Text(testLabel.getTestType().toString() + " ")));
                } // end if Year Before Last && Spring

            } // end for loop
        } // end if statement

        thisYearFall.getChildren().clear();
        thisYearFall.getChildren().addAll(thisYearFallList);
        thisYearWinter.getChildren().clear();
        thisYearWinter.getChildren().addAll(thisYearWinterList);
        thisYearSpring.getChildren().clear();
        thisYearSpring.getChildren().addAll(thisYearSpringList);
        lastYearFall.getChildren().clear();
        lastYearFall.getChildren().addAll(lastYearFallList);
        lastYearWinter.getChildren().clear();
        lastYearWinter.getChildren().addAll(lastYearWinterList);
        lastYearSpring.getChildren().clear();
        lastYearSpring.getChildren().addAll(lastYearSpringList);
        yearBeforeLastFall.getChildren().clear();
        yearBeforeLastFall.getChildren().addAll(yearBeforeLastFallList);
        yearBeforeLastWinter.getChildren().clear();
        yearBeforeLastWinter.getChildren().addAll(yearBeforeLastWinterList);
        yearBeforeLastSpring.getChildren().clear();
        yearBeforeLastSpring.getChildren().addAll(yearBeforeLastSpringList);

    } // end of method updateTestHistory

    /**
     * This method applies different colors to the test label depending on the
     * test type to provide a visual familiarity to the user, as the user has
     * been applying these colors to identify the test labels on the word
     * documents maintained as test banks
     *
     * @param text the text derived from the TestType enum
     * @return text with color
     */
    public Text applyColorToTestLabel(Text text) {
        switch (text.getText().charAt(0)) {
            case 'Q':
                text.setFill(Paint.valueOf("#d50000"));
                text.setFont(Font.font("Constantia", FontWeight.BOLD, 17));
                break;
            case 'T':
                text.setFill(Paint.valueOf("#00a800"));
                text.setFont(Font.font("Constantia", FontWeight.BOLD, 17));
                break;
            case 'E':
                text.setFill(Paint.valueOf("#5c007a"));
                text.setFont(Font.font("Constantia", FontWeight.BOLD, 17));
                break;
            case 'A':
                text.setFill(Paint.valueOf("#004ba0"));
                text.setFont(Font.font("Constantia", FontWeight.BOLD, 17));
                break;
        } // end switch statement
        return text;
    } // end of method applyColorToTestLabel

} // end of class QuestionCell
