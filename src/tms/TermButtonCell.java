package tms;

import javafx.scene.control.ListCell;

/**
 * This class specifies the style of the prompt text and the option selected in a combo box for Terms
 *
 * @author Mahesh Bagde
 */
public class TermButtonCell extends ListCell<Term> {

    @Override
    public void updateItem(Term term, boolean empty) {
        super.updateItem(term, empty);

        if (empty || term == null) {
            setStyle("-fx-text-fill: #94C947;"
                    + "-fx-font: bold 16pt \"Arial\";"
                    + "-fx-padding: 0 5 0 7;");
        } else {
            setStyle("-fx-text-fill: #94C947;"
                    + "-fx-font: bold 16pt \"Arial\";"
                    + "-fx-padding: 0 5 0 7;");
            setText(term.toString());
        } // end if else statement
    } // end of method updateItem

} // end of class TypeButtonCell
