package Controller;


import Model.AlicornModel;
import Model.CellModel;
import Model.Move;
import View.CellView;
import javafx.scene.paint.Color;

import java.util.HashSet;

/**
 * Created by jotbills on 7/25/17.
 */
public class CellController {

    public boolean isOpen = false;
    public boolean isVulnerable = false;
    public boolean isDrainSpot = false;

    private Color color;

    private CellView view;
    private CellModel model;
    private BoardController controller;

    public CellController(BoardController master){
        controller = master;
    }

    public void setView(CellView view) {
        this.view = view;
    }

    public void setModel(CellModel model) {
        this.model = model;
    }

    public void onClicked(){
    // TODO: 7/26/17  refactor for new design
        System.out.println("CLICKED");
        if(!controller.model.isGameOver()){
            if(model.getPiece() != null) {
                if (model.getPiece().getPlayer() == controller.model.getTurn()) {
                    if (controller.isPieceSelected()) {
                        if(model.getPiece().isFlying() && controller.missile){
                            //special control for descent for alicorns
                            Move move = new Move(controller.getSelectedPiece(),model, Move.Action.DESCEND);
                            controller.makeMove(move);
                        } else {

                            //selected pieces deselect when another piece is clicked
                            controller.deselect();
                        }
                    } else {
                        //clicking one of your pieces when none are selected selects it
                        controller.selectPiece(model.getPiece());
                        view.setColor(Color.AQUA);
                    }
                } else {
                    if (controller.isPieceSelected()) {
                        if (isVulnerable && controller.getSelectedPiece().isActive()) {
                            if(controller.missile){
                                //clicking on an open enemy piece while missile is primed uses the power, attacking them without moving the piece
                                Move move = new Move(controller.getSelectedPiece(),model, Move.Action.MISSILE);
                                controller.makeMove(move);


                            } else {
                                //clicking on an open enemy piece captures it, if they aren't resilient
                                Move move = new Move(controller.getSelectedPiece(),model, Move.Action.CAPTURE);
                                controller.makeMove(move);
                            }
                        }
                        if(isDrainSpot){
                            //flying unites can capture adjacent pieces
                            Move move = new Move(controller.getSelectedPiece(),model, Move.Action.SWOOP);
                            controller.makeMove(move);
                        }
                    }
                }
            } else {
                if(controller.isPieceSelected()){

                    if(isOpen){
                        //clicking on an empty space that's open moves the piece there
                        Move move = new Move(controller.getSelectedPiece(),model, Move.Action.MOVE);
                        controller.makeMove(move);

                    } else if(isDrainSpot){
                        //unicorns and alicorns can teleport to unopen spaces if powered
                        Move move = new Move(controller.getSelectedPiece(),model, Move.Action.TELEPORT);
                        controller.makeMove(move);
                    }

                }
            }
        }
        /*
        if(myPiece != null && !myBoard.isGameOver()){
            if(myPiece.getBlue() == myBoard.getBlueTurn()){
                if(myBoard.isPieceSelected()){
                    //selected pieces deselect when another piece is clicked
                    myBoard.deselect();
                } else {
                    //clicking one of your pieces when none are selected selects it
                    myPiece.selectPiece();
                    setFill(Color.AQUA);
                }
            } else {
                if (myBoard.isPieceSelected()){
                    if(isVulnerable && myBoard.getSelectedPiece().isActive){
                        if(myBoard.missile){
                            //clicking on an open enemy piece while missile is primed uses the power, attacking them without moving the piece
                            myPiece.die();
                            myBoard.getSelectedPiece().powerDown();
                            myBoard.missile = false;
                        }   else    {
                            //clicking on an open enemy piece captures it, if they aren't resilient
                            if(myPiece.die()){
                                myBoard.getSelectedPiece().move(this);
                            }
                        }
                        myBoard.changeTurns();


                    }
                    if(isDrainSpot){
                        if(myPiece.die()){
                            myBoard.getSelectedCell().getPiece().teleport(this);
                        }
                        myBoard.changeTurns();
                    }
                }
            }
        } else {
            if(myBoard.isPieceSelected()){
                if(isOpen){
                    //clicking on an empty space that's open moves the piece there
                    myBoard.getSelectedCell().getPiece().move(this);
                    myBoard.changeTurns();
                }
                if(isDrainSpot){
                    //unicorns and alicorns can teleport to unopen spaces if powered
                    myBoard.getSelectedCell().getPiece().teleport(this);
                    myBoard.changeTurns();
                }
            }
        }

        */
    }

    public void close(){
        isOpen = false;
        isVulnerable = false;
        isDrainSpot = false;
        view.setColor(Color.GRAY);
    }

    public void open(){
        boolean hasPiece = model.getPiece() != null;
        isOpen = !hasPiece;
        isVulnerable = hasPiece;
        view.setColor(Color.YELLOW);
    }

    public void markDrainSpot(){
        isDrainSpot = true;
        if(model.getPiece() == null) {
            view.setColor(Color.PLUM);
        } else {
            view.setColor(Color.LIMEGREEN);
        }
    }

    public void missileColor(HashSet<CellModel> missileSpots){
        if(model.getPiece() != null){
            if(model.getPiece().equals(controller.getSelectedPiece())){
                if(model.getPiece().isFlying()){
                    view.setColor(Color.LIMEGREEN);
                } else {
                    view.setColor(Color.AQUA);
                }
            } else {
                if(missileSpots.contains(this.model)){
                    isVulnerable = true;
                    view.setColor(Color.RED);
                }
            }

        }
    }


}
