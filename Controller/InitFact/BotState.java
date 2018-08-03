package Controller.InitFact;


import AI.N1t3MaR3m00n2;
import Misc.Old.LunarBot;
import Model.CellModel;

/**
 * Created by jotbills on 7/20/18.
 */
public class BotState extends IFactState {

    IFactSubState substate = null;
    Object parameter;
    boolean setValue;
    int player = -1;

    String bot;

    boolean buildingString = false;

    public BotState (InitializerFactory IF) {
        super(IF);
    }

    @Override
    public void processChar(char character) {
        if(substate == null) {
            switch (character) {
                case '<':
                    //process string

                    if(bot.equals("N")){
                        IF.AIs[player] = new N1t3MaR3m00n2();
                    } else {
                        IF.failed = true;
                        IF.message = "Unknown Bot";
                    }


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
                case ' ':
                    break;
                case '\'':
                    if (!buildingString) {
                        //start string
                        buildingString = true;
                        substate = new StringState(IF, this);
                    } else {
                        //end string
                        buildingString = false;
                         bot = (String) parameter;

                    }
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
