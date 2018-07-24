package Controller.InitFact;

/**
 * Created by jotbills on 7/29/17.
 */
public class NumberState extends IFactSubState {


    String prenumber = "";

    public NumberState(InitializerFactory IF, IFactState master) {
        super(IF, master);
    }

    @Override
    public void processChar(char character) {

        switch (character){

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                //add digit
                prenumber += character;
                break;
            default:
                //return parsed number
                isFinished = true;
                result = Integer.parseInt(prenumber);
                if(IF.debug) System.out.println("Value is " + prenumber);
                master.processChar(character);

        }
    }
}
