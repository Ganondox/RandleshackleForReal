import javafx.scene.paint.Color;

/**
 * Created with IntelliJ IDEA.
 * User: 36483
 * Date: 2/13/14
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pegasus extends Piece {
    Pegasus(int XCordinate, int YCordinate, Board myBoard){
        super( XCordinate,  YCordinate,  myBoard, "Pegasus.png");
        isFlying = true;
    }

    public void selectPiece(){
        myBoard.setSelectedCell(this.getCell());
        calculateOpenCells();
        if(!isPowered){
            //enables fly (power up) action if unpowered
            myBoard.getButton().setText("Fly");
            myBoard.currentPower = 1;
        } else {
            //lands if powered
            myBoard.getButton().setText("Land");
            myBoard.currentPower = 4;


        }
       // getCell().setFill(Color.AQUA);
    }

    @Override
    public void calculateOpenCells() {
        if(isFlying){
           //is flying, so invert is flying conditions
           //also dive attack
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
                                 } else if(current2.getPiece().getBlue() != this.getBlue() && current2.getPiece().isFlying) {
                                    //enemy pieces in the air within 2 spaces with an open path to them can be captured
                                    current2.setFill(Color.YELLOW);
                                    current2.isVulnerable = true;
                                 }
                            }
                       }
                   } else if(!current.getPiece().isFlying){
                       //spaces with grounded pieces aren't open to normal attack, but there is a path through them
                       for(int j = 1; j <= 6; j++){
                            Cell current2 = current.getNeighbor(j);
                            if(current2 != null){
                                if(current2.getPiece() == null){
                                    //open if with 2 spaces, open, and has path to it
                                    current2.setFill(Color.YELLOW);
                                    current2.isOpen = true;
                                 } else if(current2.getPiece().getBlue() != this.getBlue() && current2.getPiece().isFlying) {
                                     //enemy pieces in the air within 2 spaces with an open path to them can be captured
                                     current2.setFill(Color.YELLOW);
                                     current2.isVulnerable = true;
                                 }
                            }
                        }
                        //they are open to special attack though
                        if(current.getPiece().getBlue() != this.getBlue()) {
                            //adjacent enemy pieces on the ground can be divebombed
                            current.setFill(Color.LIMEGREEN);
                            current.isDrainSpot = true;
                        }
                    } else if(current.getPiece().getBlue() != this.getBlue()) {
                        //adjacent enemy pieces in the air can be captured
                        current.setFill(Color.YELLOW);
                        current.isVulnerable = true;
                     }


                 }
             }
        } else {
            //is on ground
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
        }

        //unused, but so I won't forget to copy for alicorn
        teleportSpots();

    }

    @Override
    public void powerUp() {
        super.powerUp("Pegasus.png");
        isFlying = true;
    }

    @Override
    public void powerDown() {
        super.powerDown("Pegasus");
        isFlying = false;
    }
}
