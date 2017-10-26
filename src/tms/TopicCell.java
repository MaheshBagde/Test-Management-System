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
 * This class specifies the style for a custom cell in a list view for Topics
 *
 * @author Mahesh Bagde
 */
public class TopicCell extends JFXListCell<Topic> {

    @FXML
    private Label topicNameLabel, questionCountLabel;
    @FXML
    private HBox topicCellBase;

    public TopicCell() {
        this.setPadding(new Insets(2, 5, 2, 5));
    } // end of constructor

    @Override
    public void updateItem(Topic topic, boolean empty) {
        super.updateItem(topic, empty);

        if (empty || topic == null) {
            setGraphic(null); // don't display anything
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TopicCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(CourseCell.class.getName()).log(Level.SEVERE, null, ex);
            } // end of try catch block

            topicNameLabel.setText(topic.getTopicName());
            questionCountLabel.setText(String.valueOf(topic.getQuestions().size()));

            setGraphic(topicCellBase);
        } // end if else statement

    } // end of method updateItem

} // end of class TopicCell
