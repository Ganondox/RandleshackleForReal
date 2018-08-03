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

   //for unicorns and alicorns
    protected  boolean isMagic = false;

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
           if(board.killEmAll) board.getPlayer(player).lives++;
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
            if(isHero) myBoard.getPlayer(player).lives--;
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

    void powerUp(){
        isPowered = true;
    }

    public HashSet<CellModel> getDrainSpots() {
        HashSet<CellModel> drainCells = new HashSet<>();
        HashSet<CellModel> openCells = getOpenCells();
        //if has magic and powered, finds where it can teleport to
        if (isMagic){
            Direction firstDirection = Direction.TOP;
            for(int i = 1; i <= 6; i++){
                CellModel current = this.getCell().getNeighbor(firstDirection);
                firstDirection = firstDirection.clockwise();
                if(current != null){
                    Direction secondDirection = Direction.TOP;
                    for(int j = 1; j <= 6; j++){
                        CellModel current2 = current.getNeighbor(secondDirection);
                        secondDirection = secondDirection.clockwise();
                        if(current2 != null && current2.getPiece() == null && !openCells.contains(current2)){
                            //can specialMove to any empty cell within 2 spaces, as long as it's not otherwise open
                            drainCells.add(current2);
                            //current2.isDrainSpot = true;
                            //current2.setFill(Color.PLUM);
                        }
                    }
                }
            }
        }
        //if is flying, can swoop on adjacent pieces
        if(isFlying){
            Direction firstDirection = Direction.TOP;
            for(int i = 1; i <= 6; i++) {
                CellModel current = this.getCell().getNeighbor(firstDirection);
                firstDirection = firstDirection.clockwise();
                if(current != null) {
                    //cell must contain enemy
                    if (current.getPiece() != null && current.getPiece().player != player && !openCells.contains(current)) {
                        drainCells.add(current);
                    }
                }
            }
        }
        return  drainCells;
    }

    void specialMove(CellModel destination){
        //specialMove is move + powerdown
        //move
        move(destination);
        //power down
        powerDown();
    }

    public HashSet<CellModel> missileSearch() {
        //finds what cells are vulnerable to missile fire
        HashSet<CellModel> vulnerableCells = new HashSet<>();
        //any normally vulnerable cells is vulnerable to magic missile
        vulnerableCells.addAll(getOpenCells());
        //all enemy flying pieces within 2 cells are also vulnerable, if is on ground, otherwise, reverse
        Direction firstDirection = Direction.TOP;
        for (int i = 1; i <= 6; i++) {
            CellModel current = this.getCell().getNeighbor(firstDirection);
            firstDirection = firstDirection.clockwise();
            if (current != null) {
                Direction secondDirection = Direction.TOP;
                for (int j = 1; j <= 6; j++) {
                    CellModel current2 = current.getNeighbor(secondDirection);
                    secondDirection = secondDirection.clockwise();
                    if (current2 != null && current2.getPiece() != null && current2.getPiece().player != player && (current2.getPiece().isFlying != isFlying)) {
                        vulnerableCells.add(current2);
                        //current2.isVulnerable = true;
                        //current2.setFill(Color.RED);
                    }
                }
            }
        }

        return vulnerableCells;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public boolean isHero() {
        return isHero;
    }

    public boolean isMagic() {
        return isMagic;
    }
}
