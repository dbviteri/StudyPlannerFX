package Tests;

import View.AlertDialog;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Didac on 07/05/2017.
 */
public class AlertDialogTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void show() throws Exception {

        //new AlertDialog().loadWindow();
        Thread thread = new Thread(() -> {
            new JFXPanel(); // Initializes the JavaFx Platform
            Platform.runLater(() -> {
                //throw new SPException("Test");
                new AlertDialog(Alert.AlertType.ERROR, "Error message"); // Create and
                // initialize
                // your app.

            });
        });
        thread.start();// Initialize the thread
        Thread.sleep(10000);
    }


}