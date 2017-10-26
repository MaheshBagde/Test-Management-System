package tms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This is the Controller class for Home scene (Application start view)
 *
 * @author Mahesh Bagde
 */
public class HomeController {

    @FXML
    private Label logoLabel;

    public void initialize() {

        Image acLogoImage = new Image(getClass().getResourceAsStream("/ac.png"));
        ImageView acLogoImageView = new ImageView(acLogoImage);
        acLogoImageView.setPreserveRatio(true);
        acLogoImageView.setFitHeight(200);
        logoLabel.setGraphic(acLogoImageView);

    } // end of method initialize

} // end of class HomeController
