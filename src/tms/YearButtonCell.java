package tms;

import javafx.scene.control.ListCell;

/**
 * This class specifies the style of the prompt text and the option selected in a combo box for Years
 *
 * @author Mahesh Bagde
 */
public class YearButtonCell extends ListCell<Integer> {

    @Override
    public void updateItem(Integer integer, boolean empty) {
        super.updateItem(integer, empty);

        if (empty || integer == null) {
            setStyle("-fx-text-fill: #94C947;"
                    + "-fx-font: bold 16pt \"Arial\";"
                    + "-fx-padding: 0 5 0 7;");
        } else {
            setStyle("-fx-text-fill: #94C947;"
                    + "-fx-font: bold 16pt \"Arial\";"
                    + "-fx-padding: 0 5 0 7;");
            setText(integer.toString());
        } // end if else statement
    } // end of method updateItem

} // end of class YearButtonCell
