package View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Created by Didac on 08/05/2017.
 */
public class DashBoardView {

    @FXML
    Label assignments;
    @FXML
    HBox scene;
    @FXML
    Pane completedDeadlines;
    @FXML
    Pane upcomingDeadlines;
    @FXML
    Pane missedDeadlines;
    @FXML
    Label completedDeadlinesLbl;
    @FXML
    Label upcomingDeadlinesLbl;
    @FXML
    Label missedDeadlinesLbl;
}
