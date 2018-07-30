package View;

import Controller.BoardController;
import Model.BoardModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardView  implements IView {

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

       switch (model.getPlayer(model.getTurn()).getColor()){
           case BLUE:
            myText.setText("Blue");
            break;
           case WHITE:
            myText.setText("White");
            break;
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
