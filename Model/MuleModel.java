package Model;

/**
 * Created by jotbills on 7/29/18.
 */
public class MuleModel extends PieceModel {

    MuleModel(PieceMock mock, BoardModel board, int x, int y){
        super(mock, board, x, y);
        isPowered = false;
        classname = "Mule";
    }

}
