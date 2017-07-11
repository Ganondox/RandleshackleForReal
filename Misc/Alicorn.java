package Misc;

import javafx.scene.paint.Color;

/**
 * Created with IntelliJ IDEA.
 * User: 36483
 * Date: 2/20/14
 * Time: 8:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class Alicorn extends Piece {

    Alicorn(int XCordinate, int YCordinate, Board myBoard){
        super( XCordinate,  YCordinate,  myBoard, "Alicorn.png");
        isFlying = true;
    }

    public void selectPiece(){
        myBoard.setSelectedCell(this.getCell());
        calculateOpenCells();
        if(!isPowered){
            if(getMyBoard().getRestPiece() != this){
                //enables ascend (power up) action if unpowered
                myBoard.getButton().setText("Ascend");
                myBoard.currentPower = 1;
            }

        } else {
            //enables missile action if powered
            myBoard.getButton().setText("Missile");
            myBoard.currentPower = 2;


        }
        getCell().setFill(Color.AQUA);
    }

    @Override
    public void powerUp() {
        super.powerUp("Alicorn.png");
        isFlying = true;
    }

    @Override
    public void powerDown() {
        super.powerDown("Alicorn");
        isFlying = false;
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

        // for alicorn
        teleportSpots();

    }

    @Override
    public void missileSearch() {
        //finds what cells are vulnerable to missile fire
        if(isFlying){
            //is flying, so invert is flying conditions
            //any normally vulnerable cells is vulnerable to magic missile, only it's red now
            //first all neighbors
            for(int i = 1; i <= 6; i++){
                Cell current = this.getCell().getNeighbor(i);
                if(current != null){
                    if(current.getPiece() == null){
                        //open cells 2 spaces away through the cell
                        for(int j = 1; j <= 6; j++){
                            Cell current2 = current.getNeighbor(j);
                            if(current2 != null){
                                if(current2.getPiece() == null){
                                } else if(current2.getPiece().getBlue() != this.getBlue() && current2.getPiece().isFlying) {
                                    //enemy pieces in the air within 2 spaces with an open path to them can be captured
                                    current2.setFill(Color.RED);
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
                                } else if(current2.getPiece().getBlue() != this.getBlue() && current2.getPiece().isFlying) {
                                    //enemy pieces in the air within 2 spaces with an open path to them can be captured
                                    current2.setFill(Color.RED);
                                    current2.isVulnerable = true;
                                }
                            }
                        }
                    } else if(current.getPiece().getBlue() != this.getBlue()) {
                        //adjacent enemy pieces in the air can be captured
                        current.setFill(Color.RED);
                        current.isVulnerable = true;
                    }


                }
            }
            //all enemy grounded pieces within 2 cells are also vulnerable
            for(int i = 1; i <= 6; i++){
                Cell current = this.getCell().getNeighbor(i);
                if(current != null){
                    for(int k = 1; k <= 6; k++){
                        Cell current2 = current.getNeighbor(k);
                        if(current2 != null && current2.getPiece() != null && current2.getPiece().getBlue() != getBlue() && !current2.getPiece().isFlying){
                            current2.isVulnerable = true;
                            current2.setFill(Color.RED);
                        }
                    }
                }
            }
        } else {
            //is on ground
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

    public Boolean die(){
        //returns true if piece is successfully killed
        if(isPowered){
            //will be depowered instead of dying
            powerDown();
            //sets piece on rest so can't immediately repower
            myBoard.setRestPiece(this);
            myBoard.closeCells();
            return false;
        }  else {
            //dies if unpowered
            myBoard.getPane().getChildren().remove(imageView);
            getCell().setPiece(null);
            myBoard.closeCells();
            isAlive = false;
            myBoard.endGame();
            return true;
        }
    }


}
