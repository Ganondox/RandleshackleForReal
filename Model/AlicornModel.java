package Model;

/**
 * Created by jotbills on 7/30/18.
 */
public class AlicornModel extends PieceModel {

    AlicornModel (PieceMock mock, BoardModel board, int x, int y){
        super(mock, board, x, y);
        isPowered = true;
        isFlying = true;
        isMagic = true;
        classname = "Alicorn";
        if(mock.getHits() == 0){
            health = 2;
        }
    }

    @Override
    public Boolean die() {
        myBoard.setRestPiece(this);
        powerDown();
        return super.die();
    }

    @Override
    void powerDown() {
        isFlying = false;
        isMagic = false;
        if(myBoard.getRestPiece() == null || !myBoard.getRestPiece().equals(this)) health--;
        super.powerDown();
    }

    @Override
    void powerUp() {
        isFlying = true;
        isMagic = true;
        health++;
        super.powerUp();
    }
}
