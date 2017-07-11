package Model;


import Misc.Cell;
import Misc.Piece;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardModel {

    double ruleset = 0.5;
    private Cell[][] myCells;
    private int height;
    private int width;
    private boolean isBlueTurn;
    private Piece restPiece;
    private boolean gameOver = false;

}
