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

    }

    // TODO: 7/25/17 Ensure all methods in cell are in the correct class
}
