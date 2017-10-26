package tms;

import javafx.scene.control.ListCell;

/**
 * This class specifies the style of the prompt text and the option selected in a combo box for Courses
 *
 * @author Mahesh Bagde
 */
public class CourseButtonCell extends ListCell<Course> {

    @Override
    public void updateItem(Course course, boolean empty) {
        super.updateItem(course, empty);

        if (empty || course == null) {
            setStyle("-fx-text-fill: #94C947;"
                    + "-fx-font: bold 18pt \"Arial\"");
        } else {
            setStyle("-fx-text-fill: #94C947;"
                    + "-fx-font: bold 18pt \"Arial\"");
            setText("  " + course.getCourseName());
        } // end if else statement
    } // end of method updateItem

} // end of class CourseButtonCell
