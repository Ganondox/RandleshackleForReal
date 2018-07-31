package Model;

/**
 * Created by jotbills on 7/30/18.
 */
public class PegasusModel extends PieceModel {

    PegasusModel (PieceMock mock, BoardModel board, int x, int y){
        super(mock, board, x, y);
        isPowered = true;
        isFlying = true;
        classname = "Pegasus";
    }

    @Override
    void powerDown() {
        isFlying = false;
        super.powerDown();
    }

}
