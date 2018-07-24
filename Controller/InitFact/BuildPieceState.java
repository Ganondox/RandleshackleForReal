package Controller.InitFact;

import Model.PieceMock;

import java.lang.reflect.Parameter;

/**
 * Created by jotbills on 8/2/17.
 */
public class BuildPieceState extends IFactSubState {

    enum Value{ PLAYER, HEALTH};

    IFactSubState substate;
    Object parameter;

    boolean buildingString = false;
    PieceMock.Type type = null;
    boolean valueSetting = false;

    Value valueToSet = null;
    boolean setValue = false;
    int player = -1;
    int health = -1;

    public BuildPieceState(InitializerFactory IF, IFactState master) {
        super(IF, master);
    }

    @Override
    public void processChar(char character) {

        if(substate == null) {
            switch (character) {
                case '\'':
                    //getting piece type
                    if(type == null) {
                        if (!buildingString) {
                            //start string
                            buildingString = true;
                            substate = new StringState(IF, this);
                        } else {
                            //end string
                            buildingString = false;
                           String typeID = (String) parameter;
                            parseType(typeID);
                        }
                    } else{
                        //string already processed
                        IF.syntaxError(this.getClass().getSimpleName());
                    }
                    break;

                case '(':
                    //start setting values
                    if(valueSetting || type == null){
                        IF.syntaxError(this.getClass().getSimpleName());
                    }
                    valueSetting = true;
                    break;
                case ')':
                    if(!valueSetting || type == null || !setValue){
                        IF.syntaxError(this.getClass().getSimpleName());
                        return;
                    }
                    //set value
                    if(setValue){
                        switch (valueToSet){
                            case PLAYER:
                                player = (Integer) parameter;
                                break;
                            case HEALTH:
                                health = (Integer) parameter;
                                break;
                        }
                        valueToSet = null;

                    }
                    setValue = false;
                    //stop setting values

                    valueSetting = false;
                    break;
                case ']':

                    if(type != null){
                        //build piece
                        PieceMock piece = new PieceMock();
                        piece.setType(type);
                        piece.setPlayerNumber(player);
                        piece.setHits(health);
                                /* switch (type){
                                     case MULE:

                                         break;
                                     case ALICORN:
                                         break;
                                     default:

                                       break;
                                 } */
                        result = piece;
                    } else result = null;

                    isFinished = true;
                    if(IF.debug) System.out.println("Next piece");
                    master.processChar(character);
                    break;
                case ',':
                    if(type == null){
                        //IF.syntaxError(this.getClass().getSimpleName());
                        isFinished = true;
                        result = null;
                        if(IF.debug) System.out.println("Next piece");
                        master.processChar(character);
                    } else
                        {
                            if(valueSetting){
                                //set value
                                if(setValue){
                                    switch (valueToSet){
                                        case PLAYER:
                                            player = (Integer) parameter;
                                            break;
                                        case HEALTH:
                                            health = (Integer) parameter;
                                            break;
                                    }
                                    valueToSet = null;
                                }
                            } else {
                                //build piece
                                PieceMock piece = new PieceMock();
                                piece.setType(type);
                                piece.setPlayerNumber(player);
                                piece.setHits(health);
                                /* switch (type){
                                     case MULE:

                                         break;
                                     case ALICORN:
                                         break;
                                     default:

                                       break;
                                 } */
                                result = piece;
                                isFinished = true;
                                if(IF.debug) System.out.println("Next piece");
                                master.processChar(character);

                            }
                        }

                    break;
                case '=':
                    if(valueToSet == null)  IF.syntaxError(this.getClass().getSimpleName());
                    substate = new NumberState(IF, this);
                    setValue = true;
                    break;
                case 'P':
                    //set player
                    if(valueSetting){
                        if(valueToSet != null) IF.syntaxError(this.getClass().getSimpleName());
                        valueToSet = Value.PLAYER;
                    } else {
                        IF.syntaxError(this.getClass().getSimpleName());
                    }
                    break;
                case 'H':
                    if(IF.debug) System.out.println("Setting Health");
                    //set health
                    if(valueSetting){
                        if(valueToSet != null) IF.syntaxError(this.getClass().getSimpleName());
                        valueToSet = Value.HEALTH;
                    } else {
                        IF.syntaxError(this.getClass().getSimpleName());
                    }
                    break;
                default:
                    IF.syntaxError(this.getClass().getSimpleName());
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

    private void parseType(String typeID){
        if(typeID.length() == 1){
            switch (typeID.charAt(0)){
                case 'M':
                    type = PieceMock.Type.MULE;
                    break;
                case 'A':
                    type = PieceMock.Type.ALICORN;
                    break;
                    default:
                        IF.message = "Undefined Type";
                        IF.failed = true;
            }
        } else {
            IF.message = "Undefined Type";
            IF.failed = true;
        }
    }
}
