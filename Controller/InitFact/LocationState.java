package Controller.InitFact;

/**
 * Created by jotbills on 7/29/17.
 */
public class LocationState extends IFactState{

    int x = -1;
    int y = -1;

    boolean gotY = false;

    IFactSubState substate;
    Object parameter;

    public LocationState(InitializerFactory IF) {
        super(IF);
       substate = new NumberState(IF, this);
    }

    @Override
    public void processChar(char character) {
        if(IF.data == null){
            IF.message = "Width or height not defined";
            IF.failed = true;
            return;
        }
        if(IF.players < 0){
            IF.message = "Players not defined";
            IF.failed = true;
        }

        if(substate == null) {
            switch (character) {
                case '#':
                    //marks transition from x to y value
                    if(!gotY){
                        //set x value
                        y = (Integer) parameter;
                        gotY = true;
                        if(y >= IF.height){
                            //piece out of bounds
                            IF.message = "Location out of bounds";
                            IF.failed = true;
                            return;
                        }
                        //transition to x
                        substate = new NumberState(IF,this);
                    } else {
                        IF.message = "Syntax Error";
                        IF.failed = true;
                    }
                    break;
                case ':':
                    //entering piece initializer state
                    if(gotY){
                        //set y value
                        x = (Integer) parameter;
                        if(x >= IF.width){
                            //piece out of bounds
                            IF.message = "Location out of bounds";
                            IF.failed = true;
                            return;
                        }
                        //transition to next state
                        IF.state = new PieceSetState(IF, x, y);


                    } else {
                        IF.message = "Syntax Error";
                        IF.failed = true;
                    }

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
