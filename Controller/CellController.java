package Controller;


import Model.CellModel;
import View.CellView;
import javafx.scene.paint.Color;

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

}
