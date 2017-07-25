package Model;

import View.IView;

/**
 * Created by jotbills on 7/22/17.
 */
public class CellModel {

    // TODO: 7/24/17  add needed properties 
    private BoardModel myBoard;
    private PieceModel myPiece;
    private int xCordinate;
    private int yCordinate;

    private IView view;


    public CellModel(int x, int y, BoardModel board) {
        // TODO: 7/22/17
        //setting hexagonal coordinates
        xCordinate = x;
        yCordinate = y;

        //creating initial piece, if it has one
        myPiece = generatePiece(board.getInit());
    }

    public PieceModel getPiece() {
        return myPiece;
    }

    public void setView(IView view) {
        this.view = view;
    }

    public int getxCordinate() {
        return xCordinate;
    }

    public int getyCordinate() {
        return yCordinate;
    }

    private PieceModel generatePiece(Initializer initializer){
        // TODO: 7/25/17
        return null;
    }


}
