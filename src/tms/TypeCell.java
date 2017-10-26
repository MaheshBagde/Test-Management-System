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
 * This class specifies the style for a custom cell in a list view for Test Types
 *
 * @author Mahesh Bagde
 */
public class TypeCell extends JFXListCell<TestType> {

    @FXML
    private TextFlow typeTextFlow;

    @FXML
    private Text typeText;

    public TypeCell() {
        this.setPadding(new Insets(2, 5, 2, 5));
    } // end of constructor

    @Override
    public void updateItem(TestType type, boolean empty) {
        super.updateItem(type, empty);

        if (empty || type == null) {
            setGraphic(null); // don't display anything
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TypeCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(CourseCell.class.getName()).log(Level.SEVERE, null, ex);
            } // end of try catch block

            // the user has applied these colors as a way to distiguish the different types of tests while maintaining 
            // this information on manual documents, hence coding it accordingly to make it more user friendly based
            // on the familiarity with these colors.
            switch (type.toString().charAt(0)) {
                case 'Q':
                    typeText.setText(type.toString());
                    typeTextFlow.setStyle("-fx-background-color: #ff5252;"
                            + "-fx-background-radius: 200;");
                    break;
                case 'T':
                    typeText.setText(type.toString());
                    typeTextFlow.setStyle("-fx-background-color: #66bb6a;"
                            + "-fx-background-radius: 200;");
                    break;
                case 'E':
                    typeText.setText(type.toString());
                    typeTextFlow.setStyle("-fx-background-color: #ce93d8;"
                            + "-fx-background-radius: 200;");
                    break;
                case 'A':
                    typeText.setText(type.toString());
                    typeTextFlow.setStyle("-fx-background-color: #64b5f6;"
                            + "-fx-background-radius: 200;");
                    break;
            } // end switch statement

            setGraphic(typeTextFlow);
        } // end if else statement

    } // end of method updateItem

} // end of class TypeCell
