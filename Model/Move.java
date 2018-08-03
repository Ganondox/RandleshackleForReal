package Model;


/**
 * Created by jotbills on 7/22/17.
 */
public class Move {


    public Move(PieceModel piece, CellModel destination, Action type) {
        this.piece = piece;
        this.destination = destination;
        this.type = type;
    }

    public enum Action{ MOVE, CAPTURE, MISSILE, TELEPORT, POWER_UP, DESCEND, SWOOP, FORFEIT

    }

    PieceModel piece;
    CellModel destination;
    Action type;


    //returns true if successful
    //this is the ONLY public function allowed to change the state of the model after initialization
    public  boolean execute(BoardModel board){
        //todo
        boolean successful = false;

        //check that it is that player's turn
        if(!board.isGameOver() && board.getTurn() == piece.getPlayer()) {
            switch (type) {
                case MOVE:
                    if (piece.getOpenCells().contains(destination) && destination.getPiece() == null) {
                        successful = true;
                        piece.move(destination);

                    }
                    break;
                case CAPTURE:
                    if(piece.getOpenCells().contains(destination) && destination.getPiece() != null){
                        successful = true;
                        if(destination.getPiece().die()){
                            piece.move(destination);
                        }
                    }
                    break;
                case POWER_UP:
                    if (piece instanceof EarthPonyModel || piece instanceof AlicornModel || piece instanceof PegasusModel ||
                            piece instanceof UnicornModel) {
                        if (!piece.isPowered() && (board.getRestPiece() == null || !board.getRestPiece().equals(piece))) {
                            successful = true;
                            piece.powerUp();
                        }
                    }
                    break;
                case DESCEND:
                    if(piece instanceof  PegasusModel || piece instanceof AlicornModel){
                        if(piece.isPowered){
                            successful = true;
                            piece.powerDown();
                        }
                    }
                    break;
                case TELEPORT:
                    if(piece.getDrainSpots().contains(destination) && destination.getPiece() == null){
                        successful = true;
                        piece.specialMove(destination);
                    }
                    break;
                case SWOOP:
                    if(piece.getDrainSpots().contains(destination) && destination.getPiece() != null){
                        successful = true;
                        if(destination.getPiece().die()){
                            piece.specialMove(destination);
                        }
                    }
                break;

                case MISSILE:
                    if(piece.missileSearch().contains(destination)){
                        successful = true;
                        piece.powerDown();
                        destination.getPiece().die();
                    }
                    break;
                case FORFEIT:
                    successful = true;
                    board.getPlayer(piece.getPlayer()).forfeit();
            }
        }

        if(successful) {
            board.changeTurns();
        }
        return  successful;

    }


}
