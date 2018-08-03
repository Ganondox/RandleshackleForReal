package Misc.Old;

import Model.BoardModel;
import Controller.BoardController;
import View.BoardView;

/**
 * Created by jotbills on 7/22/17.
 */
public class BoardSingleton {

    private static BoardSingleton singleton;

    private BoardModel model;
    private BoardController controller;
    private BoardView view;


    public static BoardSingleton getSingleton() {
        if(singleton.equals( null)){

        }

        return singleton;
    }

    private  BoardSingleton(){

     /*  model = new BoardModel();
       view = new BoardView(model);
       controller = new Controller(model, view);
*/

    }
}
