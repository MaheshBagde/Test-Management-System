package tms;

import com.jfoenix.controls.JFXListCell;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * This class specifies the style for a custom cell in a list view for Terms
 *
 * @author Mahesh Bagde
 */
public class TermCell extends JFXListCell<Term> {

    @FXML
    private TextFlow termTextFlow;

    @FXML
    private FontAwesomeIconView termIcon;

    @FXML
    private Text termText;

    public TermCell() {
        this.setPadding(new Insets(2, 5, 2, 5));
    } // end of constructor

    @Override
    public void updateItem(Term term, boolean empty) {
        super.updateItem(term, empty);

        if (empty || term == null) {
            setGraphic(null); // don't display anything
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TermCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(CourseCell.class.getName()).log(Level.SEVERE, null, ex);
            } // end of try catch block

            switch (term) {
                case FALL:
                    termText.setText("  " + term.toString());
                    termTextFlow.setStyle("-fx-background-color: #ff5722;"
                            + "-fx-background-radius: 200;");
                    termIcon.setIcon(FontAwesomeIcon.LEAF);
                    break;

                case SPRING:
                    termText.setText("  " + term.toString());
                    termTextFlow.setStyle("-fx-background-color: #66bb6a;"
                            + "-fx-background-radius: 200;");
                    termIcon.setIcon(FontAwesomeIcon.PAGELINES);
                    break;

                case WINTER:
                    termText.setText("  " + term.toString());
                    termTextFlow.setStyle("-fx-background-color: #64b5f6;"
                            + "-fx-background-radius: 200;");
                    termIcon.setIcon(FontAwesomeIcon.TREE);
                    break;
            } // end switch statement

            setGraphic(termTextFlow);
        } // end if else statement

    } // end of method updateItem

} // end of class TermCell
