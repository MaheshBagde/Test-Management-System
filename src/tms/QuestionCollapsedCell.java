package tms;

import com.jfoenix.controls.JFXListCell;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * This class specifies the style for a custom cell in a list view for Questions showing summary only
 * It displays a collapsed view with only the Topic and Question showing and hides its 
 * multiple choice answers and the test history
 *
 * @author Mahesh Bagde
 */
public class QuestionCollapsedCell extends JFXListCell<Question> {

    @FXML
    private HBox questionCellBase;
    @FXML
    private Label topicLabel, questionLabel;

    public QuestionCollapsedCell() {
        this.setPadding(new Insets(3, 5, 3, 5));
    } // end of constructor

    @Override
    public void updateItem(Question question, boolean empty) {
        super.updateItem(question, empty);

        if (empty || question == null) {
            setGraphic(null); // don't display anything
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuestionCollapsedCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(CourseCell.class.getName()).log(Level.SEVERE, null, ex);
            } // end of try catch block

            topicLabel.setText(question.getTopicName());
            questionLabel.setText(question.getQuestionDescription());

            setGraphic(questionCellBase);

        } // end if else statement

    } // end of method updateItem

} // end of class QuestionCollapsedCell
