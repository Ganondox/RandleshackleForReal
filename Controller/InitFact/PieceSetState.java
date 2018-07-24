package Controller.InitFact;

import Model.PieceMock;

/**
 * Created by jotbills on 8/2/17.
 */
public class PieceSetState extends IFactState{


    int x;
    int y;

    IFactSubState substate;
    Object parameter;



    public PieceSetState(InitializerFactory IF, int x, int y) {
        super(IF);
        this.x = x;
        this.y = y;
    }

    @Override
    public void processChar(char character) {
        if(substate == null) {
            switch (character) {

                case '<':
                    //end command
                    IF.state = new CommentState(IF);
                case '[':
                    //start assigning pieces
                    substate = new BuildPieceState(IF, this);
                    break;
                case ',':
                    //assign returned piece
                    IF.data[x][y] = (PieceMock) parameter;
                    //move location
                    x++;
                    if(x >= IF.width){
                        x = 0;
                        y++;
                        if(y > IF.height) {
                            IF.message = "Piece out of bounds";
                            IF.failed = true;
                        }
                    }
                    //ready for next part
                    substate = new BuildPieceState(IF, this);
                    break;
                case ']':
                    //end assignment
                    IF.data[x][y] = (PieceMock) parameter;
                    //move location
                    x++;
                    if(x >= IF.width){
                        x = 0;
                        y++;
                        if(y > IF.height) {
                            IF.message = "Piece out of bounds";
                            IF.failed = true;
                        }
                    }

                case ' ':
                    break;
                case '\n':
                    break;
                case '\r':
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
