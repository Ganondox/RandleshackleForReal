package Model;

import AI.Bot;
import View.Color;

/**
 * Created by jotbills on 7/29/18.
 */
public class PlayerModel {

    CellModel.Direction direction;
    Color color;


    BoardModel game;
    int lives;



    public Color getColor() {
        return color;
    }

    void forfeit(){
        lives = 0;
    }
}
