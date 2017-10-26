package tms;

import javafx.scene.control.ListCell;

/**
 * This class specifies the style of the prompt text and the option selected in a combo box for Topics
 *
 * @author Mahesh Bagde
 */
public class TopicButtonCell extends ListCell<Topic> {

    @Override
    public void updateItem(Topic topic, boolean empty) {
        super.updateItem(topic, empty);

        if (empty || topic == null) {
            setStyle("-fx-text-fill: #00673E;"
                    + "-fx-font: bold 18pt \"Arial\"");
        } else {
            setStyle("-fx-text-fill: #00673E;"
                    + "-fx-font: bold 18pt \"Arial\"");
            setText("  " + topic.getTopicName());
        } // end if else statement
    } // end of method updateItem

} // end of class CourseButtonCell
