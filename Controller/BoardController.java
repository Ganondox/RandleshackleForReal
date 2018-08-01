package Controller;


import Controller.InitFact.InitializerFactory;
import Misc.CellPolygon;
import Misc.LunarBot;
import Model.*;
import View.BoardView;
import View.CellView;
import View.PieceView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.HashSet;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardController {


    public enum Power{ NONE, PRIME_MISSILE, CANCEL_MISSILE, DEPOWER, POWER_UP

    }

    //counterparts
    BoardModel model;
    BoardView view;


    private  CellController[][] myCells;
    private CellModel selectedCell;
    public Power currentPower = Power.NONE;
    public boolean missile = false;


    //AI control
    private Boolean isSinglePlayer = true;
    private LunarBot bot;


    public BoardController(AnchorPane myPane, Label stattext, Button powerButton, Boolean soloMode){

        InitializerFactory factory = new InitializerFactory();
        model = new BoardModel(factory.create("res/standard.txt"));
        view = new BoardView(myPane, stattext, powerButton, this, model);
        //model = view.getModel();


        //initialize cell and piece views
        myCells = new CellController[model.getWidth()][model.getHeight()];
        for(int i = 0; i < model.getWidth(); i++){
            for(int k = 0; k < model.getHeight(); k++){
                if(k >= model.lowerBound(i) && k <= model.upperBound(i)) {
                    CellController cellc = new CellController(this);
                    myCells[i][k] = cellc;
                    CellModel cellm = model.getCell(i, k);
                    CellPolygon cellp = new CellPolygon(cellc, view);
                    CellView cellv = new CellView(cellp, cellm, view);
                    cellm.setView(cellv);
                    cellc.setView(cellv);
                    cellc.setModel(cellm);

                    //check if cell has a piece

                    if(cellm.getPiece() != null){
                        PieceView pv = new PieceView(cellm.getPiece(), view);
                    }
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

        //clears board from move calculations

        for(int i = 0; i < model.getWidth(); i++){
            for(int k = 0; k < model.getHeight(); k++){
                if(k >= model.lowerBound(i) && k <= model.upperBound(i)){

                    myCells[i][k].close();
                }
            }
        }

    }

    public void deselect(){
        //deselects piece
        closeCells();
        setSelectedCell(null);
       // myButton.setText("*null*");
        currentPower = Power.NONE;
        view.draw();
    }

    public PieceModel getSelectedPiece(){
        return getSelectedCell().getPiece();
    }

    public void usePower(){

        Move move;
        switch (currentPower){
            case POWER_UP:
                move = new Move(getSelectedPiece(), null, Move.Action.POWER_UP );
                makeMove(move);
                break;
            case DEPOWER:
                move = new Move(getSelectedPiece(), null, Move.Action.DESCEND );
                makeMove(move);
                break;
            case PRIME_MISSILE:
                System.out.println("BZZT");
                missile = true;
                closeCells();
                //getSelectedCell().setFill(Color.AQUA);
                missileSearch();
                currentPower = Power.CANCEL_MISSILE;
                view.draw();
                break;
            case CANCEL_MISSILE:
                CellController cell = myCells[selectedCell.getxCordinate()][selectedCell.getyCordinate()];
                deselect();
                cell.onClicked();
                break;
        }
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

    void selectPiece(PieceModel piece){
        //selects piece, calculates it's move, and colors board accordingly
        setSelectedCell(piece.getCell());
        missile = false;
        openCells(piece);

        //set appropriate power

        if (piece instanceof EarthPonyModel || piece instanceof AlicornModel || piece instanceof PegasusModel ||
                piece instanceof UnicornModel) {
            if(!piece.isPowered() && (model.getRestPiece() == null || !model.getRestPiece().equals(piece))){
                currentPower = Power.POWER_UP;
            } else {
                if(piece instanceof PegasusModel){
                    currentPower = Power.DEPOWER;
                }
                if(piece instanceof UnicornModel || piece instanceof AlicornModel){
                    currentPower = Power.PRIME_MISSILE;
                }
            }
        }

        view.draw();

    }

    void openCells(PieceModel pieceModel){
        //get cells that can be moved into or captured, and other spots
        HashSet<CellModel> opencells = pieceModel.getOpenCells();
        HashSet<CellModel> drainCells = pieceModel.getDrainSpots();

        //open cells
        for(int i = 0; i < model.getWidth(); i++){
            for(int k = 0; k < model.getHeight(); k++){
                if(k >= model.lowerBound(i) && k <= model.upperBound(i)){

                    if(opencells.contains(model.getCell(i,k)))
                        myCells[i][k].open();
                    else if(drainCells.contains(model.getCell(i,k)))
                        myCells[i][k].markDrainSpot();

                }
            }
        }


    }

    public void makeMove(Move move){

        if(move.execute(model)) {
            deselect();
            view.draw();
        }

    }

    void missileSearch(){
        HashSet<CellModel> vulernableCells = getSelectedPiece().missileSearch();
        for(int i = 0; i < model.getWidth(); i++){
            for(int k = 0; k < model.getHeight(); k++){
                if(k >= model.lowerBound(i) && k <= model.upperBound(i)){
                  myCells[i][k].missileColor(vulernableCells);
                }
            }
        }

    }
}
