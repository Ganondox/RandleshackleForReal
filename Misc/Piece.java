package Misc;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Created with IntelliJ IDEA.
 * User: 36483
 * Date: 1/29/14
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Piece{
    //coordinates of cell
    int xCordinate;
    int yCordinate;
    Board myBoard;
    //details of piece beyond type
    boolean isBlue;
    boolean isActive = true; //for .51 ruleset, .5 ruleset in use
    boolean isPowered;
    boolean isAlive = true;
    Image pieceImage;
    ImageView imageView;
    //for pegasi and alicorns
    boolean isFlying = false;


   /* private EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Piece me = (Piece)mouseEvent.getSource();
            me.getMyBoard().getCell(me.xCordinate, me.yCordinate).onClicked();
            System.out.println("OUCH");

        }
    };  */

    public Piece(int XCordinate, int YCordinate, Board myBoard, String classImage) {
        this.xCordinate = XCordinate;
        this.yCordinate = YCordinate;
        this.myBoard = myBoard;

        isPowered = true;
        //is blue if on top half of baord
        isBlue = YCordinate < 5;

        //setting up graphic
        String imagurl = "res/";
        if(isBlue){
            imagurl += "blue" + classImage;
        } else imagurl += "white" + classImage;

        //Cell myCell = myBoard.getCell(XCordinate,YCordinate);
        //System.out.println(myCell.getXFactor());

        System.out.println(imagurl);
        pieceImage = new Image(imagurl);
        imageView = new ImageView(pieceImage);


        //System.out.println(pieceImage);
        //System.out.println(imageView);
        imageView.setX(this.getXFactor());
        imageView.setY(this.getYFactor());
        imageView.setMouseTransparent(true);
        myBoard.getPane().getChildren().add(imageView);

    }

    public double getXFactor(){
        //converts hexagonal coordinate to rectangular one
        return (xCordinate * 5 * Math.sqrt(3)) * Math.sqrt(3) + 100;
    }

    public double getYFactor(){
        //converts hexagonal coordinate to rectangular one
        return ((yCordinate * 10) - (xCordinate * 5)) * Math.sqrt(3) + 100;
    }

    public Board getMyBoard() {
        return myBoard;
    }

    public Boolean getBlue() {
        return isBlue;
    }

    public void calculateOpenCells(){
      //determines what cells can be moved into or pieces captured
      //first all neighbors
      for(int i = 1; i <= 6; i++){
          Cell current = this.getCell().getNeighbor(i);
          if(current != null){
              if(current.getPiece() == null){
                  //open if cell has no piece on it and is adjacent
                  current.setFill(Color.YELLOW);
                  current.isOpen = true;
                  //open cells 2 spaces away through the cell
                  for(int j = 1; j <= 6; j++){
                      Cell current2 = current.getNeighbor(j);
                      if(current2 != null){
                          if(current2.getPiece() == null){
                              //open if with 2 spaces, open, and has path to it
                              current2.setFill(Color.YELLOW);
                              current2.isOpen = true;
                          } else if(current2.getPiece().getBlue() != this.getBlue() && !current2.getPiece().isFlying) {
                             //enemy pieces on the ground within 2 spaces with an open path to them can be captured
                             current2.setFill(Color.YELLOW);
                             current2.isVulnerable = true;
                          }
                      }
                  }
              } else if(current.getPiece().isFlying){
                  //spaces with flying pieces aren't open, but there is a path through them
                  for(int j = 1; j <= 6; j++){
                      Cell current2 = current.getNeighbor(j);
                      if(current2 != null){
                          if(current2.getPiece() == null){
                              //open if with 2 spaces, open, and has path to it
                              current2.setFill(Color.YELLOW);
                              current2.isOpen = true;
                          } else if(current2.getPiece().getBlue() != this.getBlue() && !current2.getPiece().isFlying) {
                              //enemy pieces on the ground within 2 spaces with an open path to them can be captured
                              current2.setFill(Color.YELLOW);
                              current2.isVulnerable = true;
                          }
                      }
                  }
              } else if(current.getPiece().getBlue() != this.getBlue()) {
                 //adjacent enemy pieces on ground can be captured
                 current.setFill(Color.YELLOW);
                 current.isVulnerable = true;
              }


          }
      }
      teleportSpots();
        //for unicorn and alicorns

    }

    public void selectPiece(){
      //selects piece, calculates it's move, and colors board accordingly
      myBoard.setSelectedCell(this.getCell());
      myBoard.missile = false;
      calculateOpenCells();
      //getCell().setFill(Color.AQUA);
    }

    public Cell getCell(){
        return myBoard.getCell(xCordinate,yCordinate);
    }

    public void move(Cell destination){
        //moves piece from one sell to another
        getCell().setPiece(null);
        xCordinate = destination.getxCordinate();
        yCordinate = destination.getyCordinate();
        destination.setPiece(this);
        myBoard.closeCells();
        //updating image
        myBoard.getPane().getChildren().remove(imageView);
        imageView.setX(this.getXFactor());
        imageView.setY(this.getYFactor());
        myBoard.getPane().getChildren().add(imageView);
        //for .51 rules
        isActive = true;
    }

    public Boolean die(){
        System.out.print("BLEGH");
        //returns true if piece is successfully killed
        myBoard.getPane().getChildren().remove(imageView);
        getCell().setPiece(null);
        myBoard.closeCells();
        isAlive = false;
        return true;
    }

    public void powerUp(){
      //powers an unpowered piece
      isPowered = true;
      myBoard.closeCells();
      myBoard.changeTurns();
    }


    public void powerUp(String classImage){
        isPowered = true;
        myBoard.closeCells();
        myBoard.changeTurns();

        //updates image to powered state
        myBoard.getPane().getChildren().remove(imageView);
        String imagurl;
        if(isBlue){
            imagurl = "res/blue" + classImage;
        } else imagurl = "res/white" + classImage;

        //Cell myCell = myBoard.getCell(XCordinate,YCordinate);
        //System.out.println(myCell.getXFactor());

        System.out.println(imagurl);
        pieceImage = new Image(imagurl);
        imageView = new ImageView(pieceImage);


        //System.out.println(pieceImage);
        //System.out.println(imageView);
        imageView.setX(this.getXFactor());
        imageView.setY(this.getYFactor());
        imageView.setMouseTransparent(true);
        myBoard.getPane().getChildren().add(imageView);
    }

    public void powerDown(){
        //when a pieces power is used up
        isPowered = false;
    }

    public void powerDown(String classname){
        myBoard.getPane().getChildren().remove(imageView);
        String imagurl = "res/";
        if(isBlue){
            imagurl += "blue" + classname + "X.png";
        } else imagurl += "white" + classname + "X.png";

        //Cell myCell = myBoard.getCell(XCordinate,YCordinate);
        //System.out.println(myCell.getXFactor());

        System.out.println(imagurl);
        pieceImage = new Image(imagurl);
        imageView = new ImageView(pieceImage);


        //System.out.println(pieceImage);
        //System.out.println(imageView);
        imageView.setX(this.getXFactor());
        imageView.setY(this.getYFactor());
        imageView.setMouseTransparent(true);
        myBoard.getPane().getChildren().add(imageView);

        isPowered = false;
    }

    public void teleportSpots(){
      //for unicorns and alicorns
    }

    public void teleport(Cell destination){
        //teleport is move + powerdown
        //move
        move(destination);
        //power down
        powerDown();
    }


    public void missileSearch(){
      //for unicorns and alicorns

    }


}
