package Model;

import java.util.HashSet;
import Model.CellModel.Direction;
/**
 * Created by jotbills on 7/22/17.
 */
public class PieceModel {

    //coordinates of cell
   protected int xCordinate;
   protected int yCordinate;
   protected BoardModel myBoard;
   protected String classname;
    //details of piece beyond type
   protected int player;
   protected boolean isActive = true; //for .51 ruleset, .5 ruleset in use
   protected boolean isPowered;
   protected boolean isAlive = true;

    //for pegasi and alicorns
   protected boolean isFlying = false;

   //for alicorns
   boolean isHero = false;
   int health = 1;

   PieceModel(PieceMock mock, BoardModel board, int x, int y){
       xCordinate = x;
       yCordinate = y;
       myBoard = board;
       player = mock.getPlayerNumber();
       if(mock.getHits() > 0){
           health = mock.getHits();
           isHero = true;
       }

   }

    public int getxCordinate() {
        return xCordinate;
    }

    public int getyCordinate() {
        return yCordinate;
    }

    public BoardModel getMyBoard() {
        return myBoard;
    }

    public String getClassname() {
        return classname;
    }

    public int getPlayer() {
        return player;
    }

    public CellModel getCell(){
      return myBoard.getCell( xCordinate,  yCordinate);
    }

    public HashSet<CellModel> getOpenCells(){

        //Note that comments assume piece is not flying, it's the opposite for flying pieces

        HashSet<CellModel> openCells = new HashSet<>();
        //determines what cells can be moved into or pieces captured
        //first all neighbors
       Direction firstDirection = Direction.TOP;
        for(int i = 1; i <= 6; i++){
            CellModel current = this.getCell().getNeighbor(firstDirection);
            firstDirection = firstDirection.clockwise();
            if(current != null){
                if(current.getPiece() == null){
                    //open if cell has no piece on it and is adjacent
                    //current.setFill(Color.YELLOW);
                    openCells.add(current);
                    //open cells 2 spaces away through the cell
                    Direction secondDirection = Direction.TOP;
                    for(int j = 1; j <= 6; j++){
                        CellModel current2 = current.getNeighbor(secondDirection);
                        secondDirection = secondDirection.clockwise();
                        if(current2 != null){
                            if(current2.getPiece() == null){
                                //open if with 2 spaces, open, and has path to it
                                //current2.setFill(Color.YELLOW);
                                openCells.add(current2);
                            } else if(current2.getPiece().getPlayer() != this.getPlayer() && this.isFlying == current2.getPiece().isFlying) {
                                //enemy pieces on the ground within 2 spaces with an open path to them can be captured
                                //current2.setFill(Color.YELLOW);
                                //current2.isVulnerable = true;
                                openCells.add(current2);
                            }
                        }
                    }
                } else if(current.getPiece().isFlying != this.isFlying){
                    //spaces with flying pieces aren't open, but there is a path through them
                    Direction secondDirection = Direction.TOP;
                    for(int j = 1; j <= 6; j++){
                        CellModel current2 = current.getNeighbor(secondDirection);
                        secondDirection = secondDirection.clockwise();
                        if(current2 != null){
                            if(current2.getPiece() == null){
                                //open if with 2 spaces, open, and has path to it
                                //current2.setFill(Color.YELLOW);
                                openCells.add(current2);
                            } else if(current2.getPiece().getPlayer() != this.getPlayer() && (current2.getPiece().isFlying == this.isFlying )) {
                                //enemy pieces on the ground within 2 spaces with an open path to them can be captured
                                //current2.setFill(Color.YELLOW);
                                //current2.isVulnerable = true;
                                openCells.add(current2);
                            }
                        }
                    }
                } else if(current.getPiece().getPlayer() != this.getPlayer()) {
                    //adjacent enemy pieces on ground can be captured
                    //current.setFill(Color.YELLOW);
                    //current.isVulnerable = true;
                    openCells.add(current);

                }


            }
        }
        //teleportSpots();
        //for unicorn and alicorns

        return openCells;

    }


     void move(CellModel destination){
        //moves piece from one cell to another
        getCell().setPiece(null);
        xCordinate = destination.getxCordinate();
        yCordinate = destination.getyCordinate();
        destination.setPiece(this);
        //myBoard.closeCells();
        //updating image
       /* myBoard.getPane().getChildren().remove(imageView);
        imageView.setX(this.getXFactor());
        imageView.setY(this.getYFactor());
        myBoard.getPane().getChildren().add(imageView);
        */
        //for .51 rules
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public Boolean die(){
        System.out.print("BLEGH");
        //returns true if piece is successfully killed
        health--;
        if(health == 0) {
            //myBoard.getPane().getChildren().remove(imageView);
            getCell().setPiece(null);
            //myBoard.closeCells();
            isAlive = false;
            return true;
        } else return false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isPowered() {
        return isPowered;
    }

    void powerDown(){
        isPowered = false;
    }
}
