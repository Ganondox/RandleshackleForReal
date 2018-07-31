package Model;

/**
 * Created by jotbills on 7/30/18.
 */
public class AlicornModel extends PieceModel {

    AlicornModel (PieceMock mock, BoardModel board, int x, int y){
        super(mock, board, x, y);
        isPowered = true;
        isFlying = true;
        classname = "Alicorn";
        if(mock.getHits() == 0){
            health = 2;
        }
    }

    @Override
    public Boolean die() {
        powerDown();
        return super.die();
    }

    @Override
    void powerDown() {
        isFlying = false;
        super.powerDown();
    }
}
