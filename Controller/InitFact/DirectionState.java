package Controller.InitFact;

import Controller.IBoardController;
import Model.CellModel;

/**
 * Created by jotbills on 7/20/18.
 */
public class DirectionState extends IFactState {

    IFactSubState substate = null;
    Object parameter;
    boolean setValue;
    int player = -1;

    public DirectionState (InitializerFactory IF) {
        super(IF);
    }

    @Override
    public void processChar(char character) {
        if(substate == null) {
            switch (character) {
                case '<':
                    //end command
                    IF.state = new CommentState(IF);
                    break;
                case '=':
                    if(setValue){
                        player = (Integer) parameter;
                    }
                    break;
                case '-':
                    substate = new NumberState(IF, this);
                    setValue = true;
                    break;
                case 'C':
                    IF.playerDirection[player] = CellModel.Direction.DOWNRIGHT;
                    break;
                case 'E':
                    IF.playerDirection[player] = CellModel.Direction.UPLEFT;
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
