package Controller.InitFact;

import Model.Config;

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

                //create config
                IF.configuration = new Config(IF.ruleset, killemall, IF.specialControl, IF.playerDirection);

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
