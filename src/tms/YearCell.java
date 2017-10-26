package tms;

import com.jfoenix.controls.JFXListCell;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * This class specifies the style for a custom cell in a list view for Years
 *
 * @author Mahesh Bagde
 */
public class YearCell extends JFXListCell {

    @FXML
    private TextFlow yearTextFlow;

    @FXML
    private Text yearText;

    public YearCell() {
        this.setPadding(new Insets(2, 5, 2, 5));
    } // end of constructor

    @Override
    public void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null); // don't display anything
        } else {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("YearCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(CourseCell.class.getName()).log(Level.SEVERE, null, ex);
            } // end of try catch block

            yearText.setText(String.valueOf(item));

            setGraphic(yearTextFlow);
        } // end if else statement

    } // end of method updateItem

} // end of class YearCell
