package Model;

/**
 * Created by jotbills on 7/30/18.
 */
public class EarthPonyModel extends PieceModel {

    EarthPonyModel (PieceMock mock, BoardModel board, int x, int y){
        super(mock, board, x, y);
        isPowered = true;
        classname = "EarthPony";
        if(mock.getHits() == 0){
            health = 2;
        }
    }

    @Override
    public Boolean die() {
        powerDown();
        return super.die();
    }
}
