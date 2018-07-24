package Controller.OnlineMultiplayer;

import Controller.IBoardController;
import Model.BoardModel;
import Model.Move;

/**
 * Created by jotbills on 8/2/17.
 * blocks controllers from accessing the model if it is not their player's turn, will be needed for the remote multiplayer
 */
public class GateKeeper {

    BoardModel board;

    public boolean makeMove(IBoardController player, Move move){
        boolean myTurn = true;
        //myTurn = player.getOrder() == board.getTurn();
         if(myTurn) return board.makeMove(move); else return  false;
    }

}
