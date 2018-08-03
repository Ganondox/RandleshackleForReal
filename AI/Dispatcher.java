package AI;

import Controller.BoardController;
import Model.BoardModel;
import Model.Move;
import javafx.concurrent.Task;

/**
 * Created by jotbills on 7/31/18.
 */
public class Dispatcher {

    BoardController master;
    BoardModel reference;
    Bot AI;
    int number;
    Task<Void> task;
    Move move;

    public Dispatcher(BoardController master, BoardModel reference, Bot AI, int number) {
        this.master = master;
        this.reference = reference;
        this.AI = AI;
        this.number = number;

        task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                Dispatcher.this.run();
                return null ;
            }
        };

        task.setOnSucceeded(e -> {
            Dispatcher.this.master.makeMove(number,move);
        });
    }


    public void run() {

        move = AI.makeMove(reference);
        master.stopThinking(number);

        //super.run();
    }

    public Task<Void> getTask() {
        return task;
    }
}
