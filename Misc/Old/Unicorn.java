package Misc.Old;

import javafx.scene.paint.Color;

/**
 * Created with IntelliJ IDEA.
 * User: 36483
 * Date: 2/13/14
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Unicorn extends Piece {
    Unicorn(int XCordinate, int YCordinate, Board myBoard){
        super( XCordinate,  YCordinate,  myBoard, "Unicorn.png");
    }

    public void selectPiece(){
        myBoard.setSelectedCell(this.getCell());
        calculateOpenCells();
        if(!isPowered){
            //enables charge (power up) action if unpowered
            myBoard.getButton().setText("Charge");
            myBoard.currentPower = 1;
        } else {
            //enables missile action if powered
            myBoard.getButton().setText("Missile");
            myBoard.currentPower = 2;


        }
      //  getCell().setFill(Color.AQUA);
    }

    @Override
    public void teleportSpots() {
        //if powered, finds where it can teleport to
        if (isPowered){
            for(int i = 1; i <= 6; i++){
               Cell current = this.getCell().getNeighbor(i);
             if(current != null){
                for(int k = 1; k <= 6; k++){
                     Cell current2 = current.getNeighbor(k);
                    if(current2 != null && current2.getPiece() == null && !current2.isOpen){
                       //can teleport to any empty cell within 2 spaces, as long as it's not otherwise open
                      current2.isDrainSpot = true;
                      current2.setFill(Color.PLUM);
                     }
                    }
                }
            }
        }
    }


    @Override
    public void powerUp() {
        super.powerUp("Unicorn.png");
    }

    @Override
    public void powerDown() {
        super.powerDown("Unicorn");
    }

    @Override
    public void missileSearch() {
        //finds what cells are vulnerable to missile fire
        //any normally vulnerable cells is vulnerable to magic missile, only it's red now
        for(int i = 1; i <= 6; i++){
            Cell current = this.getCell().getNeighbor(i);
            if(current != null){
                if(current.getPiece() == null){
                    for(int j = 1; j <= 6; j++){
                        Cell current2 = current.getNeighbor(j);
                        if(current2 != null){
                            if(current2.getPiece() == null){
                            } else if(current2.getPiece().getBlue() != this.getBlue() && !current2.getPiece().isFlying) {
                                current2.setFill(Color.RED);
                                current2.isVulnerable = true;
                            }
                        }
                    }
                } else if(current.getPiece().isFlying){
                    for(int j = 1; j <= 6; j++){
                        Cell current2 = current.getNeighbor(j);
                        if(current2 != null){
                            if(current2.getPiece() == null){
                            } else if(current2.getPiece().getBlue() != this.getBlue() && !current2.getPiece().isFlying) {
                                current2.setFill(Color.RED);
                                current2.isVulnerable = true;
                            }
                        }
                    }
                } else if(current.getPiece().getBlue() != this.getBlue()) {
                    current.setFill(Color.RED);
                    current.isVulnerable = true;
                }


            }
        }
        //all enemy flying pieces within 2 cells are also vulnerable
        for(int i = 1; i <= 6; i++){
            Cell current = this.getCell().getNeighbor(i);
            if(current != null){
                for(int k = 1; k <= 6; k++){
                    Cell current2 = current.getNeighbor(k);
                    if(current2 != null && current2.getPiece() != null && current2.getPiece().getBlue() != getBlue() && current2.getPiece().isFlying){
                        current2.isVulnerable = true;
                        current2.setFill(Color.RED);
                    }
                }
            }
        }
    }
}

