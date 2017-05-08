package View;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by Didac on 07/05/2017.
 */
public class AlertDialog {

    public AlertDialog(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType,
                message,
                ButtonType.OK);

        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("Test"));
    }
}
