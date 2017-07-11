package Misc;

/**
 * Created with IntelliJ IDEA.
 * User: 36483
 * Date: 2/13/14
 * Time: 8:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class EarthPony extends Piece {

    EarthPony(int XCordinate, int YCordinate, Board myBoard){
        super( XCordinate,  YCordinate,  myBoard, "EarthPony.png");
    }

    public Boolean die(){
        //returns true if piece is successfully killed
        if(isPowered){
            //will be depowered instead of dying
            powerDown();
            //sets piece on rest so can't immediately repower
            myBoard.setRestPiece(this);
            myBoard.closeCells();
           return false;
        }  else {
            //dies if unpowered
            myBoard.getPane().getChildren().remove(imageView);
            getCell().setPiece(null);
            myBoard.closeCells();
            isAlive = false;
            return true;
        }




    }

    public void selectPiece(){
        myBoard.setSelectedCell(this.getCell());
        calculateOpenCells();
        if(!isPowered && getMyBoard().getRestPiece() != this){
          //enables recover (power up) action if unpowered
          myBoard.getButton().setText("Recover");
          myBoard.currentPower = 1;
        }
       // getCell().setFill(Color.AQUA);
    }

    @Override
    public void powerUp() {
        super.powerUp("EarthPony.png");
    }

    @Override
    public void powerDown() {
        super.powerDown("EarthPony");
    }
}
