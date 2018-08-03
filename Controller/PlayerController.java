package Controller;

import AI.Bot;
import Model.PlayerModel;

/**
 * Created by jotbills on 7/31/18.
 */
public class PlayerController {




    String name = null;
    Bot AI;
    PlayerModel model;

    public PlayerController(String name, Bot AI, PlayerModel model) {
        this.name = name;
        this.AI = AI;
        this.model = model;
    }

    public  String getName(){
        if(name != null) return name;
        if(AI != null) return  AI.getName();
        return null;
    }

}
