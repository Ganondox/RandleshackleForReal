package Model;

import Controller.IBoardController;
import Model.CellModel.Direction;
import View.Color;

/**
 * Created by jotbills on 7/25/17.
 */
public class Config {

    public Config(double ruleset, boolean killemall, IBoardController[] specialControl, Direction[] playerDirection, Color[] playerColors) {
        this.ruleset = ruleset;
        this.killemall = killemall;
        this.specialControl = specialControl;
        this.playerDirection = playerDirection;
        this.playerColors = playerColors;
    }

    double ruleset;
    boolean killemall;
    IBoardController[] specialControl;
    Direction[] playerDirection;
    Color[] playerColors;

}
