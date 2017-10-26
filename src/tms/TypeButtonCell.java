package tms;

import javafx.scene.control.ListCell;

/**
 * This class specifies the style of the prompt text and the option selected in a combo box for Test Types
 *
 * @author Mahesh Bagde
 */
public class TypeButtonCell extends ListCell<TestType> {

    @Override
    public void updateItem(TestType testType, boolean empty) {
        super.updateItem(testType, empty);

        if (empty || testType == null) {
            setStyle("-fx-text-fill: #94C947;"
                    + "-fx-font: bold 16pt \"Arial\";"
                    + "-fx-padding: 0 5 0 7;");
        } else {
            setStyle("-fx-text-fill: #94C947;"
                    + "-fx-font: bold 16pt \"Arial\";"
                    + "-fx-padding: 0 5 0 7;");
            setText(testType.toString());
        } // end if else statement
    } // end of method updateItem

} // end of class TypeButtonCell
