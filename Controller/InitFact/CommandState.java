package Controller.InitFact;

/**
 * Created by jotbills on 7/28/17.
 */
public class CommandState extends IFactState {

    public CommandState(InitializerFactory IF) {
        super(IF);
    }

    @Override
    public void processChar(char character) {

        switch (character){

            case 'W':
                if(IF.debug) System.out.println("Set Width");
                //set width command
                IF.state = new WidthState(IF);
                break;
            case 'H':
                if(IF.debug) System.out.println("Set Height");
                //set height command
                IF.state = new HeightState(IF);
                break;
            case 'P':
                if(IF.debug) System.out.println("Set Player Count");
                //set players command
                IF.state = new PlayersState(IF);
                break;
            case '@':
                if(IF.debug) System.out.println("Create Pieces");
                //set piece data
                IF.state = new LocationState(IF);
                break;
            case 'D':
                if(IF.debug) System.out.println("Set Play Direction");
                //set direction command
                IF.state = new DirectionState(IF);
                break;

            case '$':

                if(IF.debug) System.out.println("Ending");
                //finalization command

                IF.state = new FinalizationState(IF);
                break;

                default:
                    IF.message = "Undefined Command:" + character;
                    IF.failed = true;
        }

    }
}
