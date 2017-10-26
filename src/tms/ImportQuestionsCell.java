package tms;

import com.jfoenix.controls.JFXListCell;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This class specifies the style for a custom cell in a list view for Questions
 * As it is a new question being uploaded to the test bank, it does not have a
 * Topic assigned, nor does it have a test label history to display.
 *
 * @author Mahesh Bagde
 */
public class ImportQuestionsCell extends JFXListCell<Question> {

    @FXML
    private BorderPane questionCellBase;
    @FXML
    private Label questionLabel;
    @FXML
    private VBox answersVBox;

    public ImportQuestionsCell() {
       this.setPadding(new Insets(3, 5, 3, 5));
    } // end of constructor

    @Override
    public void updateItem(Question question, boolean empty) {
        super.updateItem(question, empty);

        if (empty || question == null) {
            setGraphic(null); // don't display anything
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ImportQuestionsCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(CourseCell.class.getName()).log(Level.SEVERE, null, ex);
            } // end of try catch block

            questionLabel.setText(question.getQuestionDescription());

            for (Answer ans : question.getMultipleChoiceAnswers()) {

                Text icon = GlyphsDude.createIcon(FontAwesomeIcon.CHECK_CIRCLE, "20px");
                icon.setFill(Paint.valueOf("#00673E"));

                Label answerLabel = new Label(ans.getAnswerChoice());
                answerLabel.setPadding(new Insets(0, 0, 0, 50));
                answerLabel.setTextFill(Paint.valueOf("#00673E"));
                answerLabel.setFont(Font.font("Ariel Rounded MT Bold", FontPosture.REGULAR, 18));

                if (ans.isCorrectAnswer()) {
                    answerLabel.setGraphic(icon);  // this check mark icon is shown only if it is the correct answer
                    answerLabel.setFont(Font.font("Ariel Rounded MT Bold", FontWeight.BOLD, FontPosture.REGULAR, 18));
                    answerLabel.setPadding(new Insets(0, 0, 0, 30));
                } // end if statement

                answersVBox.getChildren().add(answerLabel);
            } // end for loop

            setGraphic(questionCellBase);

        } // end if else statement

    } // end of method updateItem

} // end of class ImportQuestionsCell
