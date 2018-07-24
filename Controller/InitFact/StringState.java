package Controller.InitFact;

/**
 * Created by jotbills on 8/7/17.
 */
public class StringState extends IFactSubState {


    String string = "";

    public StringState(InitializerFactory IF, IFactState master) {
        super(IF, master);
    }

    @Override
    public void processChar(char character) {

        switch (character){

            case '\'':

                //return string
                isFinished = true;
                result = string;
                if(IF.debug) System.out.println("String is " + string);
                master.processChar(character);
                break;

            default:
                //add character
                string += character;
                break;



        }
    }
}
