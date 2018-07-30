package Model;

/**
 * Created by jotbills on 7/30/18.
 */
public class EarthPonyModel extends PieceModel {

    EarthPonyModel (PieceMock mock, BoardModel board, int x, int y){
        super(mock, board, x, y);
        isPowered = true;
        classname = "EarthPony";
    }
}
