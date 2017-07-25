package Controller;


import Misc.Cell;
import Misc.LunarBot;
import Misc.N1t3MaR3m00n;
import Model.BoardModel;
import Model.CellModel;
import Model.PieceModel;
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


    private CellModel selectedCell;
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

    public Boolean isPieceSelected(){
        return selectedCell != null;
    }

    public CellModel getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(CellModel selectedCell) {
        this.selectedCell = selectedCell;
    }

    public void closeCells(){

        // TODO: 7/24/17  refactor so works with new design

        //clears board from move calculations
        /*
        for(int i = 0; i < width; i++){
            for(int k = 0; k < height; k++){
                if(k >= lowerBound(i) && k <= upperBound(i)){
                    myCells[i][k].isOpen = false;
                    myCells[i][k].isVulnerable = false;
                    myCells[i][k].isDrainSpot = false;
                    myCells[i][k].setFill(Color.GRAY);
                }
            }
        }
        */
    }

    public void deselect(){
        //deselects piece
        closeCells();
        setSelectedCell(null);
       // myButton.setText("*null*");
        currentPower = 0;
    }

    public PieceModel getSelectedPiece(){
        return getSelectedCell().getPiece();
    }

    public void usePower(){
        // TODO: 7/24/17 refactor according to new design

            /*
        switch (currentPower){


            case 1:
                //power up
                getSelectedCell().getPiece().powerUp();
                break;
            case 2:
                //prime missile
                System.out.println("BZZT");
                missile = true;
                closeCells();
                getSelectedCell().setFill(Color.AQUA);
                getSelectedCell().getPiece().missileSearch();
                getButton().setText("Cancel");
                currentPower = 3;
                break;
            case 3:
                //cancel missile
                missile = false;
                closeCells();
                getSelectedCell().setFill(Color.AQUA);
                getSelectedCell().getPiece().calculateOpenCells();
                getButton().setText("Missile");
                currentPower = 2;
                break;
            case 4:
                //depower piece
                getSelectedCell().getPiece().powerDown();
                closeCells();
                changeTurns();
                break;
        }
        */
    }

    // TODO: have make randleshackle controller call this class were appropriate

}
