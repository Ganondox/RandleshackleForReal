package Model;

import View.IView;

/**
 * Created by jotbills on 7/22/17.
 */
public class CellModel {

   public enum Direction{ TOP, BOTTOM, UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT

    }

    private BoardModel myBoard;
    private PieceModel myPiece;
    private int xCordinate;
    private int yCordinate;

    private IView view;


    public CellModel(int x, int y, BoardModel board) {
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

    public CellModel getNeighbor(Direction d){
        //calculates which cells is in certain direction of the this cell
        int x;
        int y;
        switch (d){
            case UPLEFT:
                //top left
                y = yCordinate - 1;
                x = xCordinate - 1;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case TOP:
                //top
                y = yCordinate - 1;
                x = xCordinate + 0;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case UPRIGHT:
                //top right
                y = yCordinate + 0;
                x = xCordinate + 1;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case DOWNRIGHT:
                //bottom right
                y = yCordinate + 1;
                x = xCordinate + 1;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case BOTTOM:
                //bottom
                y = yCordinate + 1;
                x = xCordinate - 0;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case DOWNLEFT:
                //bottom left
                y = yCordinate - 0;
                x = xCordinate - 1;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            default:
                return  null;
        }
    }


}
