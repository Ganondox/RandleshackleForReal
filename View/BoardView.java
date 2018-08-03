package View;

import Controller.BoardController;
import Model.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardView implements IView {

    BoardController controller;
    BoardModel model;

    private AnchorPane myPane;
    private Label myText;
    private Button myButton;

    private ArrayList<CellView> cells;
    private ArrayList<PieceView> pieces;

    public BoardView(AnchorPane myPane, Label stattext, Button powerButton, BoardController master, BoardModel board){
       controller = master;
        //connecting with fxml
        this.myPane = myPane;
        this.myText = stattext;
        this.myButton = powerButton;
        //initalizing model

        //model = new BoardModel(11, 11);

        //model.setView(this);

        model = board;

        cells = new ArrayList<>();
        pieces = new ArrayList<>();
    }

    @Override
    public void draw() {

        //draw board
        for(int i = 0; i < cells.size(); i++){
            cells.get(i).draw();
        }
        for(int i = 0; i < pieces.size(); i++){
            pieces.get(i).draw();
        }

        //draw additional components

        //title
        if(!model.isGameOver()) {
            if(!controller.isAIthinking()) {
                if(controller.getCurrentPlayer().getName() == null) {

                    switch (model.getPlayer(model.getTurn()).getColor()) {
                        case BLUE:
                            myText.setText("Blue");
                            break;
                        case WHITE:
                            myText.setText("White");
                            break;
                    }
                } else {
                    myText.setText( controller.getCurrentPlayer().getName());
                }
            } else {
                myText.setText( controller.getCurrentPlayer().getName() + " is thinking...");
            }
        } else {
            switch (model.getPlayer(model.getTurn()).getColor()) {
                case BLUE:
                    myText.setText("Blue Wins");
                    break;
                case WHITE:
                    myText.setText("White Wins");
                    break;
            }
        }

        //button
        switch (controller.currentPower){
            case POWER_UP:
                if(controller.getSelectedPiece() instanceof EarthPonyModel)
                myButton.setText("Recover");
                 else if(controller.getSelectedPiece() instanceof UnicornModel)
                    myButton.setText("Charge");
                 else if(controller.getSelectedPiece() instanceof AlicornModel)
                    myButton.setText("Ascend");
                else if(controller.getSelectedPiece() instanceof PegasusModel)
                    myButton.setText("Fly");
                else myButton.setText("Power Up");
            break;
            case DEPOWER:
                myButton.setText("Land");
                break;
            case PRIME_MISSILE:
                if(controller.getSelectedPiece().isFlying()){
                    myButton.setText("Missile*");
                } else
                myButton.setText("Missile");
                break;
            case CANCEL_MISSILE:
                myButton.setText("Cancel");
                break;
            default:
                myButton.setText("*null*");

        }

    }

    public AnchorPane getPane() {
        return myPane;
    }

    public Button getButton() {
        return myButton;
    }


    public BoardModel getModel() {
        return model;
    }

    public void addCell(CellView cell){
        cells.add(cell);

    }

    public void addPiece(PieceView piece){
        pieces.add(piece);

    }
}
