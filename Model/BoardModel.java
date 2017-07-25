package Model;


import View.IView;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardModel {

    IView view;

    double ruleset = 0.5;
    private CellModel[][] myCells;
    private int height;
    private int width;
    private boolean isBlueTurn;
    private PieceModel restPiece;
    private boolean gameOver = false;
    private Initializer init;

    public BoardModel(int w, int h){
        height = h;
        width = w;
        myCells = new CellModel[width][height];
        for(int i = 0; i < width; i++){
            for(int k = 0; k < height; k++){
                if(k >= lowerBound(i) && k <= upperBound(i)) {
                    myCells[i][k] = new CellModel(i, k, this);
                }
            }
        }
        //blue starts
        isBlueTurn = true;
    }


    public void setView(IView view) {
        this.view = view;
    }

    //returns true if successful
    public boolean makeMove(Move move){

        changeTurns();
        view.draw();
        return true;
    }



    public int lowerBound(int n){
        //calculates the minimum y for a given x to keep it hexagonal
        // TODO: 7/22/17 refactor in terms of width and height
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
        // TODO: 7/22/17 refactor in terms of width and height
        //calculates the maximum y for a given x to keep it hexagonal
        return 10 - lowerBound(10 - n);
    }

    public CellModel getCell(int x, int y){
        //returns cell with a given coordinate
        return myCells[x][y];
    }

    public Boolean getBlueTurn() {
        //used to determine whose turn it is
        return isBlueTurn;
    }

    public PieceModel getRestPiece() {
        //piece recovering from attack
        return restPiece;
    }

    public void setRestPiece(PieceModel restPiece) {
        this.restPiece = restPiece;
    }

    public void changeTurns(){
        // TODO: 7/24/17 refactor to work with new design

        /*
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
        */
    }

    public void endGame(){
        gameOver = true;
        //deselect();
        /*if(isBlueTurn){
            myText.setText("Blue Wins");
        }else{
            myText.setText("White Wins");
        } */
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;

    }

    public Initializer getInit() {
        return init;
    }
}
