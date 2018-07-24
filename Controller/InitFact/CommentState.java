package Controller.InitFact;

/**
 * Created by jotbills on 7/28/17.
 */
public class CommentState extends  IFactState{


    public CommentState(InitializerFactory IF) {
        super(IF);
    }

    @Override
    public void processChar(char character) {

        switch (character){

            case '>':
                if(IF.debug) System.out.println("Executing command:");
                //execute command
                IF.state = new CommandState(IF);
                break;

        }
    }
}
