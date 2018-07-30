package Model;

/**
 * Created by jotbills on 7/30/18.
 */
public class UnicornModel extends PieceModel {

    UnicornModel (PieceMock mock, BoardModel board, int x, int y){
        super(mock, board, x, y);
        isPowered = true;
        classname = "Unicorn";
    }

}
