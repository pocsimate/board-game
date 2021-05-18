package boardgame.controller;

import boardgame.result.ResultAccessor;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.tinylog.Logger;


public class ResultController {

    @FXML
    private TextFlow results;

    @FXML
    private void initialize(){
        ResultAccessor resultAccessor = new ResultAccessor();
        for(var result : resultAccessor.getResults()){
            results.getChildren().add(new Text("Winner: " + result.getWinner() + ", Opponent: " + result.getOpponent() + ", Ended: " + result.getDate() + "\n"));
        }
    }

    @FXML
    private void exit(){
        Logger.info("Quitting...");
        Platform.exit();
    }

}

