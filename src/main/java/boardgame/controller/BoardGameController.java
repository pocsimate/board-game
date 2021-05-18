package boardgame.controller;

import boardgame.model.Direction;
import boardgame.result.Result;
import boardgame.result.ResultAccessor;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import boardgame.model.BoardGame;
import boardgame.model.Position;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BoardGameController {

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private boolean isGameWon = false;

    private final String[] playerNames = new String[2];

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private BoardGame model = new BoardGame();

    @FXML
    private GridPane board;

    @FXML
    private Text player0;

    @FXML
    private Text player1;

    @FXML
    private Text winnerText;

    @FXML
    private void initialize() {
        createBoard();
        createPieces();
        createBlocks();
        setSelectablePositions();
        showSelectablePositions();
        model.getIsWon().addListener(this::handleWon);
        Platform.runLater(() -> {
            this.player0.setText(playerNames[0]);
            this.player1.setText(playerNames[1]);
        });
    }

    @FXML
    private void resetGame(){
        selectionPhase = SelectionPhase.SELECT_FROM;
        board.getChildren().clear();
        isGameWon = false;
        winnerText.setText("");
        model = new BoardGame();
        initialize();
    }

    @FXML
    private void showStatistics(ActionEvent event) throws IOException {
        if(isGameWon){
            FXMLLoader fxmlLoader = new FXMLLoader(ResultController.class.getResource("/resultUi.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void setNames(String playerOne, String playerTwo){
        playerNames[0] = playerOne;
        playerNames[1] = playerTwo;
    }

    private void createBlocks(){
        for (var block : model.blocks){
            var square = getSquare(block.getPosition());
            square.getStyleClass().add("block");
        }
    }

    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void createPieces() {
        for (int i = 0; i < model.getPieceSize(); i++) {
            for (int j = 0; j < model.getPieceCount(i); j++) {
                model.positionProperty(i,j).addListener(this::piecePositionChange);
                var piece = createPiece(Color.valueOf(model.getPieceColor(i,j)));
                getSquare(model.getPiecePosition(i,j)).getChildren().add(piece);
            }
        }
    }

    private Circle createPiece(Color color) {
        var piece = new Circle(30);
        piece.setFill(color);
        return piece;
    }

    private void handleWon(ObservableValue<? extends Boolean> observable, Boolean oldState, Boolean newState){
        if (newState){
            Logger.warn("Game won by: {}, against: {}",
                    playerNames[model.toggleActivePlayer()], playerNames[model.activePlayer]);
            winnerText.setText(playerNames[model.toggleActivePlayer()] + " won the match");
            isGameWon = true;
            hideSelectablePositions();
            ResultAccessor resultAccessor = new ResultAccessor();
            resultAccessor.insertResult(Result.builder()
                    .winner(playerNames[model.toggleActivePlayer()])
                    .opponent(playerNames[model.activePlayer])
                    .date(Date.valueOf(LocalDate.now()))
                    .build());
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.debug("Click on square {}", position);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getPieceNumber(selected).getAsInt();
                    var direction = Direction.of(position.row() - selected.row(), position.col() - selected.col());
                    Logger.debug("Moving piece {} {}", pieceNumber, direction);
                    model.move(pieceNumber, direction);
                    deselectSelectedPosition();
                    alterSelectionPhase();
                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void selectPosition(Position position) {
        selected = position;
        Logger.debug("selectPosition(), selected: {}", selected);
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> selectablePositions.addAll(model.getPiecePositions());

            case SELECT_TO -> {
                var pieceNumber = model.getPieceNumber(selected).getAsInt();
                for (var direction : model.getValidMoves(pieceNumber)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().clear();
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
    }
}
