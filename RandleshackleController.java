import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class RandleshackleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox soloMode;


    @FXML
    private AnchorPane gameBoard;

    @FXML
    private Button powerButton;

    @FXML
    private AnchorPane powerGraphic;

    @FXML
    private Label statusText;

    @FXML
    private Text title;

    Board board;


    @FXML
    void exitGame(MouseEvent event) {
        //close app
        System.exit(0);
    }

    @FXML
    void newGame(MouseEvent event) {
        Text title2 = title;

        gameBoard.getChildren().clear();
        //draw and initiate game board
        board = new Board(gameBoard, statusText, powerButton, soloMode.selectedProperty().getValue());
        gameBoard.getChildren();
        statusText.setText("Blue");
       // gameBoard.getChildren().add(title2);
        //
        int no = 1;

    }

    @FXML
    void openHelp(MouseEvent event) throws Exception {

            /*Desktop d = Desktop.getDesktop();
            d.browse(new URI("http://ganondox.deviantart.com/art/RANDLESHACKLE-board-game-420725226"));   */
    }

    @FXML
    void usePower(MouseEvent event) {

        switch (board.currentPower){
            case 1:
                //power up
                board.getSelectedCell().getPiece().powerUp();
                break;
            case 2:
                //prime missile
                board.missile = true;
                board.closeCells();
                board.getSelectedCell().setFill(Color.AQUA);
                board.getSelectedCell().getPiece().missileSearch();
                board.getButton().setText("Cancel");
                board.currentPower = 3;
                break;
            case 3:
                //cancel missile
                board.missile = false;
                board.closeCells();
                board.getSelectedCell().setFill(Color.AQUA);
                board.getSelectedCell().getPiece().calculateOpenCells();
                board.getButton().setText("Missile");
                board.currentPower = 2;
                break;
            case 4:
                //depower piece
                board.getSelectedCell().getPiece().powerDown();
                board.closeCells();
                board.changeTurns();
                break;
        }
    }

    @FXML
    void initialize() {
        assert gameBoard != null : "fx:id=\"gameBoard\" was not injected: check your FXML file 'Randleshackle.fxml'.";
        assert powerButton != null : "fx:id=\"powerButton\" was not injected: check your FXML file 'Randleshackle.fxml'.";
        assert powerGraphic != null : "fx:id=\"powerGraphic\" was not injected: check your FXML file 'Randleshackle.fxml'.";
        assert statusText != null : "fx:id=\"statusText\" was not injected: check your FXML file 'Randleshackle.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'randleshackle.fxml'.";
        assert soloMode != null : "fx:id=\"soloMode\" was not injected: check your FXML file 'randleshackle.fxml'.";


    }

}
