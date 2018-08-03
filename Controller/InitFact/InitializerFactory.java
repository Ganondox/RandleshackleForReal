package Controller.InitFact;

import Controller.IBoardController;
import AI.Bot;
import Model.CellModel.Direction;
import Model.Config;
import Model.Initializer;
import Model.PieceMock;
import View.Color;

import java.io.FileReader;

/**
 * Created by jotbills on 7/26/17.
 */
public class InitializerFactory implements IFact{

    PieceMock[][] data = null;
    Bot[] AIs = null;
    Config configuration = null;

    IBoardController[] specialControl = null;
    Direction[] playerDirection = null;
    Color[] colors = null;

    IFactState state = new CommentState(this);
    int width = -1;
    int height = -1;
    int players = -1;

    double ruleset = -1;

    boolean failed = false;
    String message = "Success";

    boolean debug = true;


    public Initializer create(String fileName){
        // TODO: 7/25/17

        //clean data from previous runs
        data = null;
        AIs = null;
        configuration = null;

        specialControl = null;
        playerDirection = null;


        state = new CommentState(this);
        width = -1;
        height = -1;
        players = -1;


        failed = false;
        message = "Success";

        if(debug){
            System.out.println("Finished initalizing factory");
        }
        //read file
        try {
            FileReader file = new FileReader(fileName);
            int input = file.read();
            //while file still has characters
            while (input > -1) {
                char character = (char)input;
                processChar(character);
                if(failed) {
                    if(debug) System.out.println("FAILED: " + message);
                    return null;
                }
                input = file.read();
            }
        }
        catch(Exception e){
            message = "Unknown Exception";
            return null;
        }

        //assemble initializater
        if(debug) System.out.println("Success!");
        Initializer init = new Initializer(data, AIs , configuration);
        return  init;
    }

    @Override
    public void processChar(char character){

        state.processChar(character);

    }

    void syntaxError(String classname){
        message = "Syntax Error in: " + classname;
        failed = true;
    }
}
