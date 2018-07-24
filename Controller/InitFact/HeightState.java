package Controller.InitFact;

import Model.PieceMock;

/**
 * Created by jotbills on 7/29/17.
 */
public class HeightState extends  IFactState{

    IFactSubState substate = null;
    Object parameter;
    boolean setValue;

    public HeightState(InitializerFactory IF) {
        super(IF);
    }

    @Override
    public void processChar(char character) {
        if(substate == null) {
            switch (character) {
                case '<':
                    if(setValue){
                        //set height
                        IF.height = (Integer) parameter;
                    }
                    if(IF.width > -1){
                        //initialize data
                        IF.data = new PieceMock[IF.width][IF.height];
                    }
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
