package Model;

import Model.CellModel;
import View.Color;

/**
 * Created by jotbills on 7/29/18.
 */
public class Player {

    CellModel.Direction direction;
    Color color;

    BoardModel game;
    int lives;


    public Color getColor() {
        return color;
    }
}
