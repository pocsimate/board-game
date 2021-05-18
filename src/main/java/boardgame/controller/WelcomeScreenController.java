package boardgame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class WelcomeScreenController {

    @FXML
    private TextField player0;

    @FXML
    private TextField player1;

    @FXML
    public void startGame(ActionEvent event) throws IOException{
        if (! player0.getText().isBlank() && ! player1.getText().isBlank()){
            Logger.info("Player one: {}, Player two: {}", player0.getText(), player1.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(BoardGameController.class.getResource("/gameUi.fxml"));
            Parent root = fxmlLoader.load();
            BoardGameController controller = fxmlLoader.<BoardGameController>getController();
            controller.setNames(player0.getText(), player1.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
