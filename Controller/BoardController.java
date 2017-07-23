package Controller;


import Misc.Cell;
import Misc.LunarBot;
import Misc.N1t3MaR3m00n;
import Model.BoardModel;
import View.BoardView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardController {

    //counterparts
    BoardModel model;
    BoardView view;

    //input
    private Button myButton;

    private Cell selectedCell;
    public int currentPower = 0;
    public boolean missile = false;
    //AI control
    private Boolean isSinglePlayer = true;
    private LunarBot bot;


    public BoardController(AnchorPane myPane, Label stattext, Button powerButton, Boolean soloMode){
        view = new BoardView(myPane, stattext, powerButton, this);

        isSinglePlayer = soloMode;
        if(isSinglePlayer){
            bot = new N1t3MaR3m00n2(this);
            bot.makeMove();
        }

    }

    // TODO: 7/22/17 place all other methods from board in the proper class
    // TODO: have make randleshackle controller call this class were appropriate

}
