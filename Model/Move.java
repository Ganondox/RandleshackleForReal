package Model;


import static Model.Move.Action.MOVE;

/**
 * Created by jotbills on 7/22/17.
 */
public class Move {


    public Move(PieceModel piece, CellModel destination, Action type) {
        this.piece = piece;
        this.destination = destination;
        this.type = type;
    }

    public enum Action{ MOVE, CAPTURE, MISSILE, TELEPORT, RECOVER, DESCEND

    }

    PieceModel piece;
    CellModel destination;
    Action type;


    //returns true if successful
    public  boolean execute(BoardModel board){
        //todo
        boolean successful = false;

        //check that it is that player's turn
        if(board.getTurn() == piece.getPlayer()) {
            switch (type) {
                case MOVE:
                    if (piece.getOpenCells().contains(destination)) {
                        successful = true;
                        piece.move(destination);

                    }

            }
        }

        if(successful) {
            board.changeTurns();
        }
        return  successful;

    }


}
