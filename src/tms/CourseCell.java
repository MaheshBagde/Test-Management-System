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
 * This class specifies the style for a custom cell in a list view for Courses
 *
 * @author Mahesh Bagde
 */
public class CourseCell extends JFXListCell<Course> {

    @FXML
    private Label courseLabel, topicCountLabel;
    @FXML
    private HBox courseCellBase;

    public CourseCell() {
        this.setPadding(new Insets(2, 5, 2, 5));
    } // end of constructor

    @Override
    public void updateItem(Course course, boolean empty) {
        super.updateItem(course, empty);

        if (empty || course == null) {
            setGraphic(null); // don't display anything
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(CourseCell.class.getName()).log(Level.SEVERE, null, ex);
            } // end of try catch block

            courseLabel.setText(course.getCourseName());
            topicCountLabel.setText(String.valueOf(course.getTopics().size()));

            setGraphic(courseCellBase);
        } // end if else statement

    } // end of method updateItem

} // end of class CourseCell
