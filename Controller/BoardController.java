package Controller;


import Controller.InitFact.IFact;
import Controller.InitFact.InitializerFactory;
import Misc.Cell;
import Misc.CellPolygon;
import Misc.LunarBot;
import Misc.N1t3MaR3m00n;
import Model.BoardModel;
import Model.CellModel;
import Model.PieceModel;
import View.BoardView;
import View.CellView;
import View.IView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardController {


    enum Power{ NONE, PRIME_MISSILE, CANCEL_MISSILE, DEPOWER

    }

    //counterparts
    BoardModel model;
    BoardView view;


    private CellModel selectedCell;
    public Power currentPower = Power.NONE;
    public boolean missile = false;

    //AI control
    private Boolean isSinglePlayer = true;
    private LunarBot bot;


    public BoardController(AnchorPane myPane, Label stattext, Button powerButton, Boolean soloMode){

        InitializerFactory factory = new InitializerFactory();
        model = new BoardModel(factory.create("res/standard.txt"));
        view = new BoardView(myPane, stattext, powerButton, this);
        //model = view.getModel();

        //initialize cell views
        for(int i = 0; i < model.getWidth(); i++){
            for(int k = 0; k < model.getHeight(); k++){
                if(k >= model.lowerBound(i) && k <= model.upperBound(i)) {
                    CellController cellc = new CellController(this);
                    CellModel cellm = model.getCell(i, k);
                    CellPolygon cellp = new CellPolygon(cellc, view);
                    CellView cellv = new CellView(cellp, cellm);
                    cellm.setView(cellv);
                    cellc.setView(cellv);
                    cellc.setModel(cellm);
                }



            }
        }


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
        currentPower = Power.NONE;
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
