package AI;

import Model.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by jotbills on 7/22/17.
 */
public class N1t3MaR3m00n2  extends Bot {


    LinkedList<PieceModel> pieces;
    AlicornModel NightmareMoon;
    int player;
    BoardModel board;


    boolean isInitialized = false;

    public N1t3MaR3m00n2() {
        super("Nightmare Moon");
    }


    public void initialize(BoardModel board, int player){
        this.player = player;
        pieces = new LinkedList<PieceModel>();
        for(int i = 0; i < 11; i++){
            for(int k = 0; k < 11; k++){
                if(k >= board.lowerBound(i) && k <= board.upperBound(i)){
                    if( board.getCell(i,k).getPiece() != null && board.getCell(i,k).getPiece().getPlayer() == player){
                        pieces.add(board.getCell(i,k).getPiece());
                        if(board.getCell(i,k).getPiece().isHero()) NightmareMoon = (AlicornModel) board.getCell(i,k).getPiece();
                    }
                }
            }
        }
        //scramble
        for(int i = 0; i < pieces.size(); i++){
            int k = (int)(Math.random() * pieces.size());
            PieceModel front = pieces.get(k);
            pieces.remove(k);
            pieces.addFirst(front);
        }
    }


//force push



    @Override
    public Move makeMove(BoardModel board) {

        this.board = board;

        if(!isInitialized){
            initialize(board, board.getTurn());
        }

        System.out.println("thinking");

        Move move = null;

        move = savePiece(NightmareMoon);
        if (move != null) return  move;
        System.out.println("Nightmare Moon Secured");


        if(board.getTurn() == player){
            System.out.println("Securing Pieces");
            move = savePieces();
        }


        if (move != null) return  move;

        if(board.getTurn() == player){
            System.out.println("Looking for openings");
            move = defensiveAttacks();
        }


        if (move != null) return  move;

        if(board.getTurn() == player){
            System.out.println("Charging units");
            move = powerPieces();
        }

        if (move != null) return  move;

        if(board.getTurn() == player){
            System.out.println("Looking for safe passage");
            move = avoidAttacks();
        }

        if (move != null) return  move;

        if(board.getTurn() == player){
            System.out.println("ATTACK!");
           move = aggressiveAttacks();
        }


        if (move != null) return  move;

        if(board.getTurn() == player){
            System.out.println("Must make move");
           move = randomMove();

        }

        return  move;


    }

    private Move randomMove(){
        int i = 0;
        Move move = null;
        while(move == null && pieces.size() > 0){

            if(i >= pieces.size() ) i = 0;
            if(pieces.get(i).isAlive()){

               PieceModel piece = pieces.get(i);
               HashSet<CellModel> possibleMoves = piece.getOpenCells();

               if(possibleMoves.size() > 0){
                   int index = (int) (possibleMoves.size() * Math.random());
                   Iterator<CellModel> cell = possibleMoves.iterator();
                   for(int j = 0; j < index; j++){
                       cell.next();
                   }
                   CellModel finalCell = null;
                   if(cell.hasNext()) {
                       finalCell = cell.next();
                       if (finalCell.getPiece() == null) {
                           move = new Move(piece, finalCell, Move.Action.MOVE);
                       } else {
                           move = new Move(piece, finalCell, Move.Action.CAPTURE);
                       }
                   }
               }


            }   else {
                pieces.remove(i);
            }
            i++;
        }
        if(move == null){
            return new Move(NightmareMoon,null, Move.Action.FORFEIT );
        }

        return move;
    }

    private Move defensiveAttacks(){
        //loops for all pieces to see if any attacks can be made without putting a piece at risk
        int i = 0;
        Move move = null;
        while(board.getTurn() == player && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive()){
                move = defensiveAttack(pieces.get(i));
                if(move != null) return  move;
                //System.out.println(i + "Piece is safe");
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;
        }
        return move;
    }

    private Move avoidAttacks(){
        //loops for all pieces to see if any moves can be made without putting a piece at risk
        int i = 0;
        Move move = null;
        while(board.getTurn() == player && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive()){
                move = avoidAttack(pieces.get(i));
                if(move != null) return  move;
                //System.out.println(i + "Piece is safe");
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;
        }
        return move;
    }

    private Move savePieces(){
        //loops for all pieces to make sure none are in danger
        int i = 0;
        Move move = null;
        while(board.getTurn() == player && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive()){
                move = savePiece(pieces.get(i));
                if(move != null) return  move;
                //System.out.println(i + "Piece is safe");
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;
        }
        return move;
    }

    private Move aggressiveAttacks(){
        //make any possible attack, regardless of danger
        int i = 0;
        Move move = null;
        while(board.getTurn() == player && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive()){
                move = aggressiveAttack(pieces.get(i));
                if(move != null) return  move;
                //System.out.println(i + "Piece is safe");
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;
        }
        return move;
    }

    //if the piece is threatened and can be saved, returns move to save it
    private Move savePiece(PieceModel p){
        //find if given piece is threatened by any other pieces

        Boolean threatened = true;

        if(p instanceof EarthPonyModel && p.isPowered() ){
            threatened = false;
        } else threatened = !isCellSafe(p.getCell(), p.isFlying());


        if(threatened){
            Move move = defensiveAttack(p);
            if(move != null) return move;
            if(board.getTurn() == player && !board.isGameOver()){
                System.out.println("RETREAT!");
                move = avoidAttack(p);
            }
            return  move;
        }
        return null;
    }

    private Move avoidAttack(PieceModel piece){
        //don't step into danger if she can help it
        HashSet<CellModel> possibleMoves = piece.getOpenCells();
        if(possibleMoves.size() > 0){
            Iterator<CellModel> cell = possibleMoves.iterator();
            while(cell.hasNext()){
                CellModel current = cell.next();
                if(current.getPiece() == null && isCellSafe(current, piece.isFlying())){
                    return new Move(piece, current, Move.Action.MOVE);
                }
            }
        }

        return null;

    }



    private Move defensiveAttack(PieceModel piece){
        System.out.println(piece.getClass().toString() + piece.getxCordinate() + "|" + piece.getyCordinate() + ": " + " HISS!");
        //attacks piece if doesn't make piece vulnerable

        //p.getCell().onClicked();




        Move move = null;

        //capture
        HashSet<CellModel> possibleMoves = piece.getOpenCells();
        if(possibleMoves.size() > 0){
            Iterator<CellModel> cell = possibleMoves.iterator();
            while(cell.hasNext()){
                CellModel current = cell.next();
                if(current.getPiece() != null){
                    //no need to be defensive when you can KO an alicorn
                    if(current.getPiece() instanceof AlicornModel){
                       return move = new Move(piece, current, Move.Action.CAPTURE);
                    }  else if( isCellSafe(current, piece.isFlying()) ) {
                        return move = new Move(piece, current, Move.Action.CAPTURE);
                    }

                }
            }
        }


        //missile attack
        if(board.getTurn() == player && !board.isGameOver()){
            if(  piece.isMagic() ) {
                //board.usePower();
                System.out.println("CHARGING MAH LAZER!");

                possibleMoves = piece.getDrainSpots();

                if(possibleMoves.size() > 0){
                    Iterator<CellModel> cell = possibleMoves.iterator();
                    while(cell.hasNext()){
                        CellModel current = cell.next();
                        if(current.getPiece() != null){
                            //no need to be defensive when you can KO an alicorn
                            if(current.getPiece() instanceof AlicornModel){
                                return move = new Move(piece, current, Move.Action.MISSILE);
                            }  else if( isCellSafeFrom(piece.getCell(), current.getPiece() , piece.isFlying()) ) {
                                return move = new Move(piece, current, Move.Action.MISSILE);
                            }

                        }
                    }
                }
            }
        }

        return move;

    }


    private Move aggressiveAttack(PieceModel piece){
        System.out.println(piece.getClass().toString() + piece.getxCordinate() + "|" + piece.getyCordinate() + ": " + " HISS!");
        //any attack goes

        //p.getCell().onClicked();




        Move move = null;

        //capture
        HashSet<CellModel> possibleMoves = piece.getOpenCells();
        if(possibleMoves.size() > 0){
            Iterator<CellModel> cell = possibleMoves.iterator();
            while(cell.hasNext()){
                CellModel current = cell.next();
                if(current.getPiece() != null){

                        return move = new Move(piece, current, Move.Action.CAPTURE);


                }
            }
        }


        //missile attack
        if(board.getTurn() == player && !board.isGameOver()){
            if(  piece.isMagic() ) {
                //board.usePower();
                System.out.println("CHARGING MAH LAZER!");

                possibleMoves = piece.getDrainSpots();

                if(possibleMoves.size() > 0){
                    Iterator<CellModel> cell = possibleMoves.iterator();
                    while(cell.hasNext()){
                        CellModel current = cell.next();
                        if(current.getPiece() != null){

                                return move = new Move(piece, current, Move.Action.MISSILE);

                        }
                    }
                }
            }
        }

        return move;

    }


    private Boolean isCellSafe(CellModel cell, Boolean flying){
        //from cell, checks for any pieces that can capture a piece in that cell

        return isCellSafeFrom(cell, cell.getPiece(), flying);

    }

    private Boolean isCellSafeFrom(CellModel victim, PieceModel threat, Boolean flying){
        //aside from the second piece, sees if any piece can capture the first piece

        CellModel cell = victim;

        if(flying){

            //open flying pieces are a threat
            CellModel.Direction firstDirection = CellModel.Direction.TOP;
            for(int i = 1; i <= 6; i++) {
                CellModel current = cell.getNeighbor(firstDirection);
                firstDirection = firstDirection.clockwise();
                if (current != null) {
                    if (current.getPiece() == null) {
                        CellModel.Direction secondDirection = CellModel.Direction.TOP;
                        for (int j = 1; j <= 6; j++) {
                            CellModel current2 = current.getNeighbor(secondDirection);
                            secondDirection = secondDirection.clockwise();
                            if (current2 != null) {
                                if (current2.getPiece() != null &&  current2.getPiece().getPlayer() != player && current2.getPiece().isFlying()
                                        && !current2.getPiece().equals(threat)) {
                                    return false;
                                }
                            }
                        }
                    } else if (!current.getPiece().isFlying()) {
                        CellModel.Direction secondDirection = CellModel.Direction.TOP;
                        for (int j = 1; j <= 6; j++) {
                            CellModel current2 = current.getNeighbor(secondDirection);
                            secondDirection = secondDirection.clockwise();
                            if (current2 != null) {
                                if (current2.getPiece() != null &&  current2.getPiece().getPlayer() != player && current2.getPiece().isFlying()
                                        && !current2.getPiece().equals(threat)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }

            //watch out for unicorns with lasers
            firstDirection = CellModel.Direction.TOP;
            for(int i = 1; i <= 6; i++) {
                CellModel current = cell.getNeighbor(firstDirection);
                firstDirection = firstDirection.clockwise();
                if (current != null) {
                    CellModel.Direction secondDirection = CellModel.Direction.TOP;
                    for (int j = 1; j <= 6; j++) {
                        CellModel current2 = current.getNeighbor(secondDirection);
                        secondDirection = secondDirection.clockwise();
                        if(current2 != null) {
                            if (current2.getPiece() != null &&  current2.getPiece().getPlayer() != player && !current2.getPiece().isFlying() && current2.getPiece().isMagic()
                                    && !current2.getPiece().equals(threat)) {
                                return false;
                            }
                        }

                    }
                }
            }





        } else {

            //open ground pieces are a threat, as are adjacent flying pieces
            CellModel.Direction firstDirection = CellModel.Direction.TOP;
            for(int i = 1; i <= 6; i++) {
                CellModel current = cell.getNeighbor(firstDirection);
                firstDirection = firstDirection.clockwise();
                if (current != null) {
                    if (current.getPiece() == null) {
                        CellModel.Direction secondDirection = CellModel.Direction.TOP;
                        for (int j = 1; j <= 6; j++) {
                            CellModel current2 = current.getNeighbor(secondDirection);
                            secondDirection = secondDirection.clockwise();
                            if (current2 != null) {
                                if (current2.getPiece() != null &&  current2.getPiece().getPlayer() != player && !current2.getPiece().isFlying()
                                        && !current2.getPiece().equals(threat)) {
                                    return false;
                                }
                            }
                        }
                    } else if (current.getPiece().isFlying()) {

                        if(current.getPiece().getPlayer() != player  && !current.getPiece().equals(threat)){
                            return  false;
                        }

                        CellModel.Direction secondDirection = CellModel.Direction.TOP;
                        for (int j = 1; j <= 6; j++) {
                            CellModel current2 = current.getNeighbor(secondDirection);
                            secondDirection = secondDirection.clockwise();
                            if (current2 != null) {
                                if (current2.getPiece() != null &&  current2.getPiece().getPlayer() != player && !current2.getPiece().isFlying()
                                        && !current2.getPiece().equals(threat)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }

            //watch out for alicorns with lasers
            firstDirection = CellModel.Direction.TOP;
            for(int i = 1; i <= 6; i++) {
                CellModel current = cell.getNeighbor(firstDirection);
                firstDirection = firstDirection.clockwise();
                if (current != null) {
                    CellModel.Direction secondDirection = CellModel.Direction.TOP;
                    for (int j = 1; j <= 6; j++) {
                        CellModel current2 = current.getNeighbor(secondDirection);
                        secondDirection = secondDirection.clockwise();
                        if(current2 != null) {
                            if (current2.getPiece() != null &&  current2.getPiece().getPlayer() != player && current2.getPiece().isFlying() && current2.getPiece().isMagic()
                                    && !current2.getPiece().equals(threat)) {
                                return false;
                            }
                        }

                    }
                }
            }

        }
        return true;


    }

    public  Move powerPieces(){
        //loops for all pieces to see if any can be powered up
        int i = 0;
        while(board.getTurn() == player && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive()){

                PieceModel piece = pieces.get(i);
                if (piece instanceof EarthPonyModel || piece instanceof AlicornModel || piece instanceof PegasusModel ||
                        piece instanceof UnicornModel) {
                    if (!piece.isPowered() && (board.getRestPiece() == null || !board.getRestPiece().equals(piece))) {
                        return new Move(piece, null, Move.Action.POWER_UP);
                    }
                }


                /*
                pieces.get(i).getCell().onClicked();
                if(!pieces.get(i).isPowered) board.usePower();
                if(board.getBlueTurn() && !board.isGameOver()) pieces.get(i).getCell().onClicked();
                */
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;

        }
        return null;
    }





}
