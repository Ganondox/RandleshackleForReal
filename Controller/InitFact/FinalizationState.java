package Controller.InitFact;

import Model.Config;
import View.Color;

/**
 * Created by jotbills on 7/21/18.
 */
public class FinalizationState extends IFactState {

    boolean killemall = false;

    public  FinalizationState(InitializerFactory IF){
        super(IF);
    }

    @Override
    public void processChar(char character) {
        switch (character){
            case '<':

                //set ruleset to default if not initialized
                if(IF.ruleset == -1) IF.ruleset = 0.51;

                //set colors to default if not specified
                for(int i = 0; i < IF.players; i++){
                    if(IF.colors[i] == null){
                        switch (i % 2){
                            case 0:
                                IF.colors[i] = Color.BLUE;
                                break;
                            case 1:
                                IF.colors[i] = Color.WHITE;
                                break;
                        }
                    }
                }


                //create config
                IF.configuration = new Config(IF.ruleset, killemall, IF.specialControl, IF.playerDirection, IF.colors);

                //send to final state
                IF.state = new EndState(IF);
                break;
            case 'k':
                killemall = true;
                break;

            default:
                IF.syntaxError(this.getClass().getSimpleName());


        }
    }
}
