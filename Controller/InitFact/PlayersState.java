package Controller.InitFact;

import Controller.IBoardController;
import Model.CellModel;
import View.Color;

/**
 * Created by jotbills on 8/2/17.
 */
public class PlayersState extends IFactState {

    IFactSubState substate = null;
    Object parameter;
    boolean setValue;

    public PlayersState(InitializerFactory IF) {
        super(IF);
    }

    @Override
    public void processChar(char character) {
        if(substate == null) {
            switch (character) {
                case '<':
                    if(setValue){
                        //set player
                        IF.players = (Integer) parameter;
                    }

                    //initialize data
                    IF.playerDirection = new CellModel.Direction[IF.players];
                    IF.specialControl = new IBoardController[IF.players];
                    IF.colors = new Color[IF.players];

                    //end command
                    IF.state = new CommentState(IF);
                    break;
                case '=':
                    substate = new NumberState(IF, this);
                    setValue = true;
                    break;
                case ' ':
                    break;
                default:
                    IF.message = "Syntax Error";
                    IF.failed = true;
                    break;
            }
        } else {
            if(!substate.isFinished){
                substate.processChar(character);
            } else {
                //get result and return to normal execution
                parameter = substate.result;
                substate = null;
                processChar(character);

            }
        }

    }
}
