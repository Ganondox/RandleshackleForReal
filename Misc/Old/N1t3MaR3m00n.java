package Misc.Old;

import Misc.Old.*;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: 36483
 * Date: 2/26/14
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class N1t3MaR3m00n extends LunarBot {

    //quite buggy, know it doesn't calculate safety for flying pieces when landing correctly

    LinkedList<Piece> pieces;
    Board board;
    AnchorPane virtualPane = new AnchorPane();
    Alicorn NightmareMoon;

   public N1t3MaR3m00n(Board board){
        this.board = board;
        pieces = new LinkedList<Piece>();
        for(int i = 0; i < 11; i++){
            for(int k = 0; k < 11; k++){
                if(k >= board.lowerBound(i) && k <= board.upperBound(i)){
                   if( board.getCell(i,k).getPiece() != null && board.getCell(i,k).getPiece().isBlue ){
                       pieces.add(board.getCell(i,k).getPiece());
                   }
                }
            }
        }
        NightmareMoon = (Alicorn)board.getCell(5,1).getPiece();
        //scramble
        for(int i = 0; i < pieces.size(); i++){
            int k = (int)(Math.random() * pieces.size());
            Piece front = pieces.get(k);
            pieces.remove(k);
            pieces.addFirst(front);
        }
    }

    @Override
    public void makeMove() {
        //makeMove(3);
        System.out.println("thinking");

        savePieces(NightmareMoon);
        System.out.println("Nightmare Moon Secured");
        if(board.getBlueTurn()){
            System.out.println("Securing Pieces");
            savePieces();
        }



        if(board.getBlueTurn()){
            System.out.println("Looking for openings");
            defensiveAttack();
        }

        if(board.getBlueTurn()){
            System.out.println("Charging units");
            powerPieces();
        }

        if(board.getBlueTurn()){
            System.out.println("Looking for safe passage");
            avoidAttack();
        }

        if(board.getBlueTurn()){
            System.out.println("ATTACK!");
            aggressiveAttack();
        }

        if(board.getBlueTurn()){
            System.out.println("Must make move");
            randomMove();
        }



    }

    public int makeMove(int IQ){
       if(true);
       return 0;
    }

    private void randomMove(){
        int i = 0;
        while(board.getBlueTurn() && board.isGameOver() == false){

            if(i >= pieces.size() ) i = 0;
            if(pieces.get(i).isAlive){
                pieces.get(i).getCell().onClicked();
                int a = (int)(Math.random() * 6);
                int b = (int)(Math.random() * 6);
                if(board.getSelectedCell().getNeighbor(a) != null && board.getSelectedCell().getNeighbor(a).getNeighbor(b) != null ){
                    board.getSelectedCell().getNeighbor(a).getNeighbor(b).onClicked();
                }
                if(board.getSelectedCell() != null){
                    board.getSelectedCell().onClicked();
                    i++;
                }
            }   else {
                pieces.remove(i);
            }


        }
    }



    private void savePieces(){
        //loops for all pieces to make sure none are in danger
        int i = 0;
        while(board.getBlueTurn() && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive){
               savePieces(pieces.get(i));
                //System.out.println(i + "Piece is safe");
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;
        }
    }

    private void savePieces(Piece p){
      //find if given piece is threatened by any other pieces

        Boolean threatened = true;

        if(p instanceof EarthPony && p.isPowered ){
            threatened = false;
        } else threatened = !isCellSafe(p.getCell(), p.isFlying);

        if(threatened){
            defensiveAttack(p);
            if(board.getBlueTurn() && !board.isGameOver()){
                System.out.println("RETREAT!");
                avoidAttack(p);
            }
        }
    }

    private void defensiveAttack(){
        //loops for all pieces to see if any attacks can be made without putting a piece at risk
        int i = 0;
        while(board.getBlueTurn() && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive){
                defensiveAttack(pieces.get(i));
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;
        }
    }

    private void defensiveAttack(Piece p){
        System.out.println(p.getClass().toString() + p.xCordinate + "|" + p.yCordinate + ": " + " HISS!");
        //attacks piece if doesn't make piece vulnerable

        p.getCell().onClicked();



        //capture

        for(int i = 1; i <= 6; i++){
            Cell current = p.getCell().getNeighbor(i);
            if(current != null){
                for(int k = 1; k <= 6; k++){
                    Cell current2 = current.getNeighbor(k);
                    if(current2 != null && current2.isVulnerable){
                        //if( current2.getPiece() != null && current2.getPiece().getBlue() != p.getBlue() && board.getBlueTurn() && !board.isGameOver()){
                            //no need to be defensive when you can KO an alicorn
                            if(current2.getPiece() instanceof Alicorn){
                                current2.onClicked();
                            } else {
                               if(isCellSafe(current2,p.isFlying)) current2.onClicked();
                            }
                       // }
                    }
                }
            }
        }

        //missile attack
        if(board.getBlueTurn() && !board.isGameOver()){
            if(  (p instanceof Unicorn || p instanceof Alicorn ) && p.isPowered ) {
                 board.usePower();
                System.out.println("CHARGING MAH LAZER!");
                for(int i = 1; i <= 6; i++){
                    Cell current = p.getCell().getNeighbor(i);
                    if(current != null){
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2.isVulnerable){
                                //if( current2.getPiece() != null && current2.getPiece().getBlue() != p.getBlue() && board.getBlueTurn() && !board.isGameOver()){
                                //no need to be defensive when you can KO an alicorn
                                if(current2.getPiece() instanceof  Alicorn){
                                    current2.onClicked();
                                } else {
                                    if(isCellSafe(current2.getPiece(),p.isFlying)){
                                        System.out.println("BITE!");
                                        current2.onClicked();
                                    }
                                }
                                // }
                            }
                        }
                    }
                }

            }
        }

        //deselect if did not attack
        if(board.getBlueTurn() && !board.isGameOver()){
            p.getCell().onClicked();
        }

    }

    private void avoidAttack(){
        //loops for all pieces to see if any can move without being placed in harms way
        int i = 0;
        while(board.getBlueTurn() && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive){
                avoidAttack(pieces.get(i));
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;
        }
    }

    private void avoidAttack(Piece p){
        //don't step into danger if she can help it
        p.getCell().onClicked();

        for(int i = 1; i <= 6; i++){
           Cell current = p.getCell().getNeighbor(i);
            if(current != null){
                for(int k = 1; k <= 6; k++){
                    Cell current2 = current.getNeighbor(k);
                    if(current2 != null && current2.isOpen){
                         if(isCellSafe(current2,p.isFlying)) current2.onClicked();


                    }
                }
            }
        }

        if(board.getBlueTurn() && !board.isGameOver()){
            p.getCell().onClicked();
        }


    }

    private void aggressiveAttack(){
        //loops for all pieces to see if any attacks can be made, regardless of danger
        int i = 0;
        while(board.getBlueTurn() && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive){
                aggressiveAttack(pieces.get(i));
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;
        }



    }

    private void aggressiveAttack(Piece p){
        //any attack goes

        p.getCell().onClicked();



        //capture

        for(int i = 1; i <= 6; i++){
           Cell current = p.getCell().getNeighbor(i);
            if(current != null){
                for(int k = 1; k <= 6; k++){
                    Cell current2 = current.getNeighbor(k);
                    if(current2 != null && current2.isVulnerable){
                        System.out.println("BONZAI!!");
                            current2.onClicked();

                    }
                }
            }
        }

        //missile attack
        if(board.getBlueTurn() && !board.isGameOver()){
            if( (p instanceof Unicorn || p instanceof Alicorn) && p.isPowered ) {
                board.usePower();
                for(int i = 1; i <= 6; i++){
                    Cell current = p.getCell().getNeighbor(i);
                    if(current != null){
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2.isVulnerable){

                                current2.onClicked();

                            }
                        }
                    }
                }

            }
        }

        if(board.getBlueTurn() && !board.isGameOver()){
            p.getCell().onClicked();
        }
    }

    private Boolean isCellSafe(Cell c, Boolean flying){
        //from cell, checks for any pieces that can capture a piece in that cell
        if(flying){

            //open flying pieces are a threat
            for(int i = 1; i <= 6; i++){
                Cell current = c.getNeighbor(i);
                if(current != null){
                    if(current.getPiece() == null){
                        //enemies can attack through open squares
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece().getBlue() == false && current2.getPiece().isFlying){
                                return false;
                            }
                        }
                    } else if(current.getPiece().getBlue() == false && current.getPiece().isFlying) {
                        return false;
                    } else if(current.getPiece().isFlying) {
                        //grounded pieces don't block
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece().getBlue() == false && current2.getPiece().isFlying){
                                return false;
                            }
                        }
                    }
                }
            }

           //watch out for unicorns with lasers
            for(int i = 1; i <= 6; i++){
               Cell current = c.getNeighbor(i);
                if(current != null){
                    for(int k = 1; k <= 6; k++){
                        Cell current2 = current.getNeighbor(k);
                        if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece().getBlue() == false && current2.getPiece() instanceof Unicorn && current2.getPiece().isPowered){

                            return false;


                        }
                    }
                }
            }

        } else {

            //open ground pieces are a threat, as are adjacent flying pieces
            for(int i = 1; i <= 6; i++){
                Cell current = c.getNeighbor(i);
                if(current != null){
                    if(current.getPiece() == null){
                    //enemies can attack through open squares
                       for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece().getBlue() == false && !current2.getPiece().isFlying){
                                return false;
                            }
                       }
                    } else if(current.getPiece().getBlue() == false) {
                       return false;
                    } else if(current.getPiece().isFlying) {
                        //flying pieces don't block
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece().getBlue() == false && !current2.getPiece().isFlying){
                                return false;
                            }
                        }
                    }
                }
            }

            //watch out for alicorns with lasers
            for(int i = 1; i <= 6; i++){
                Cell current = c.getNeighbor(i);
                if(current != null){
                    for(int k = 1; k <= 6; k++){
                        Cell current2 = current.getNeighbor(k);
                        if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece().getBlue() == false && current2.getPiece() instanceof Alicorn && current2.getPiece().isPowered){

                            return false;


                        }
                    }
                }
            }

        }
        return true;

    }

    private Boolean isCellSafe(Piece p, Boolean flying){
        //aside from the given piece, sees if any piece can capture the selected piece
        Cell c = board.getSelectedCell();
        if(flying){

            //open flying pieces are a threat
            for(int i = 1; i <= 6; i++){
                Cell current = c.getNeighbor(i);
                if(current != null){
                    if(current.getPiece() == null || current.getPiece() == p){
                        //enemies can attack through open squares
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece() != p && current2.getPiece().getBlue() == false && current2.getPiece().isFlying){
                                return false;
                            }
                        }
                    } else if(current.getPiece().getBlue() == false && current.getPiece().isFlying) {
                        return false;
                    } else if(current.getPiece().isFlying) {
                        //grounded pieces don't block
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece() != p && current2.getPiece().getBlue() == false && current2.getPiece().isFlying){
                                return false;
                            }
                        }
                    }
                }
            }

            //watch out for unicorns with lasers
            for(int i = 1; i <= 6; i++){
                Cell current = c.getNeighbor(i);
                if(current != null){
                    for(int k = 1; k <= 6; k++){
                        Cell current2 = current.getNeighbor(k);
                        if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece() != p && current2.getPiece().getBlue() == false && current2.getPiece() instanceof Unicorn && current2.getPiece().isPowered){

                            return false;


                        }
                    }
                }
            }

        } else {

            //open ground pieces are a threat, as are adjacent flying pieces
            for(int i = 1; i <= 6; i++){
                Cell current = c.getNeighbor(i);
                if(current != null){
                    if(current.getPiece() == null || current.getPiece() == p){
                        //enemies can attack through open squares
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece() != p && current2.getPiece().getBlue() == false && !current2.getPiece().isFlying){
                                return false;
                            }
                        }
                    } else if(current.getPiece().getBlue() == false) {
                        return false;
                    } else if(current.getPiece().isFlying) {
                        //flying pieces don't block
                        for(int k = 1; k <= 6; k++){
                            Cell current2 = current.getNeighbor(k);
                            if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece() != p && current2.getPiece().getBlue() == false && !current2.getPiece().isFlying){
                                return false;
                            }
                        }
                    }
                }
            }

            //watch out for alicorns with lasers
            for(int i = 1; i <= 6; i++){
                Cell current = c.getNeighbor(i);
                if(current != null){
                    for(int k = 1; k <= 6; k++){
                        Cell current2 = current.getNeighbor(k);
                        if(current2 != null && current2 != c && current2.getPiece() != null && current2.getPiece() != p && current2.getPiece().getBlue() == false && current2.getPiece() instanceof Alicorn && current2.getPiece().isPowered){

                            return false;


                        }
                    }
                }
            }

        }
        return true;

    }

    public  void powerPieces(){
        //loops for all pieces to see if any can be powered up
        int i = 0;
        while(board.getBlueTurn() && !board.isGameOver()){

            if(i >= pieces.size() ) break;
            if(pieces.get(i).isAlive){
                pieces.get(i).getCell().onClicked();
                if(!pieces.get(i).isPowered) board.usePower();
                if(board.getBlueTurn() && !board.isGameOver()) pieces.get(i).getCell().onClicked();
            }   else {
                //remove pieces that have been captured
                pieces.remove(i);
            }

            i++;

        }
    }






}
