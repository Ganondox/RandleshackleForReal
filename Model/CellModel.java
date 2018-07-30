package Model;

import View.IView;
import com.sun.org.apache.bcel.internal.generic.SWITCH;

import java.util.Observable;

/**
 * Created by jotbills on 7/22/17.
 */
public class CellModel {

   public enum Direction{ TOP, BOTTOM, UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT;

       public Direction clockwise(){
           switch (this){
               case TOP:
                   return UPRIGHT;
               case UPRIGHT:
                   return DOWNRIGHT;
               case DOWNRIGHT:
                   return BOTTOM;
               case BOTTOM:
                   return DOWNLEFT;
               case DOWNLEFT:
                   return UPLEFT;
               case UPLEFT:
                   return TOP;

           }
           return  null;
       }


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
        myBoard = board;
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

        PieceMock mock = initializer.data[xCordinate][yCordinate];
        PieceModel piece = null;

        if(mock != null) {
            switch (mock.type) {
                case MULE:
                    piece = new MuleModel(mock, myBoard, xCordinate, yCordinate);
                    break;
                case ALICORN:
                    piece = new AlicornModel(mock, myBoard, xCordinate, yCordinate);
                    break;
                case UNICORN:
                    piece = new UnicornModel(mock, myBoard, xCordinate, yCordinate);
                    break;
                case EARTHPONY:
                    piece = new EarthPonyModel(mock, myBoard, xCordinate, yCordinate);
                    break;
                case PEGASUS:
                    piece = new PegasusModel(mock, myBoard, xCordinate, yCordinate);

            }
        }


        return piece;
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

     void setPiece(PieceModel myPiece) {
        this.myPiece = myPiece;
    }
}
