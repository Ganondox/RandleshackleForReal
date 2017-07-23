package Model;


import Controller.IBoardView;
import Misc.Cell;
import Misc.Piece;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardModel {

    IBoardView view;

    double ruleset = 0.5;
    private CellModel[][] myCells;
    private int height;
    private int width;
    private boolean isBlueTurn;
    private PieceModel restPiece;
    private boolean gameOver = false;

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


    public void setView(IBoardView view) {
        this.view = view;
    }

    //returns true if successful
    public boolean makeMove(Move move){

        view.draw();
        return true;
    }



    public static int lowerBound(int n){
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

}
