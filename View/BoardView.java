package View;

import Controller.BoardController;
import Model.BoardModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardView  implements IView {

    BoardController controller;
    BoardModel model;

    private AnchorPane myPane;
    private Label myText;
    private Button myButton;

    public BoardView(AnchorPane myPane, Label stattext, Button powerButton, BoardController master){
       controller = master;
        //connecting with fxml
        this.myPane = myPane;
        this.myText = stattext;
        this.myButton = powerButton;
        //initalizing model
        model = new BoardModel(11, 11);
        model.setView(this);
    }

    @Override
    public void draw() {

        //draw board

        //draw additional components

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
}
