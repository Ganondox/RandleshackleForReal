package Misc;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
//import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: 36483
 * Date: 1/23/14
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class Board {

    private AnchorPane myPane;
    private Cell[][] myCells;
    private int height;
    private int width;
    private boolean isBlueTurn;
    private Label myText;
    private Cell selectedCell;
    private Piece restPiece;
    private Button myButton;
    public int currentPower = 0;
    public boolean missile = false;
    private boolean gameOver = false;
    //AI control
    private Boolean isSinglePlayer = true;
    private LunarBot bot;


    public Board(AnchorPane myPane, Label stattext, Button powerButton, Boolean soloMode) {
        //connecting with controller
        this.myPane = myPane;
        this.myText = stattext;
        this.myButton = powerButton;
        //drawing board
        height = 11;
        width = 11;
        myCells = new Cell[width][height];
        for(int i = 0; i < width; i++){
            for(int k = 0; k < height; k++){
               if(k >= lowerBound(i) && k <= upperBound(i))
               myCells[i][k] = new Cell(i, k, this);
            }
        }
      //blue starts
      isBlueTurn = true;
      isSinglePlayer = soloMode;
      if(isSinglePlayer){
         bot = new N1t3MaR3m00n(this);
         bot.makeMove();
      }

    }

    public AnchorPane getPane() {
        return myPane;
    }

    public Button getButton() {
        return myButton;
    }

    public static int lowerBound(int n){
    //calculates the minimum y for a given x to keep it hexagonal
        switch (n){
            case 0:
                return 2;
            case 1:
                return 1;
            case 2:
                return 0;
            case 3:
                return 0;
            case 4:
                return 1;
            case 5:
                return 1;
            case 6:
                return 2;
            case 7:
                return 2;
            case 8:
                return 3;
            case 9:
                return 5;
            case 10:
                return 7;
            default:
                return 15;

        }
    }
    public int upperBound(int n){
        //calculates the maximum y for a given x to keep it hexagonal
        return 10 - lowerBound(10 - n);
    }

    public Cell getCell(int x, int y){
        //returns cell with a given coordinate
        return myCells[x][y];
    }

    public Boolean isPieceSelected(){
      return selectedCell != null;
    }

    public Boolean getBlueTurn() {
        //used to determine whose turn it is
        return isBlueTurn;
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    public Piece getRestPiece() {
        //piece recovering from attack
        return restPiece;
    }

    public void setRestPiece(Piece restPiece) {
        this.restPiece = restPiece;
    }

    public void closeCells(){
        //clears board from move calculations
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
    }

    public void changeTurns(){
        if(!gameOver){
          //piece no longer on rest after their turn is over
          if(restPiece != null && restPiece.getBlue() == isBlueTurn){
             restPiece = null;
          }
          //change turns
          isBlueTurn = !isBlueTurn;
          //update GUI
          if(isBlueTurn){
              myText.setText("Blue");
            }else{
              myText.setText("White");
            }
        }
        deselect();
        if(isSinglePlayer && isBlueTurn){
            bot.makeMove();
        }

    }

    public void deselect(){
        //deselects piece
        closeCells();
        setSelectedCell(null);
        myButton.setText("*null*");
        currentPower = 0;
    }

    public Piece getSelectedPiece(){
        return getSelectedCell().getPiece();
    }

    public void endGame(){
        gameOver = true;
        //deselect();
        if(isBlueTurn){
            myText.setText("Blue Wins");
        }else{
            myText.setText("White Wins");
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void usePower(){
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
    }


}
