package Model;

import Controller.IBoardController;
import Model.CellModel.Direction;

/**
 * Created by jotbills on 7/25/17.
 */
public class Config {

    public Config(double ruleset, boolean killemall, IBoardController[] specialControl, Direction[] playerDirection) {
        this.ruleset = ruleset;
        this.killemall = killemall;
        this.specialControl = specialControl;
        this.playerDirection = playerDirection;
    }

    double ruleset;
    boolean killemall;
    IBoardController[] specialControl;
    Direction[] playerDirection;
}
