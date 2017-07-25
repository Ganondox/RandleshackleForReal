package View;

import Controller.CellController;
import Misc.CellPolygon;
import Model.CellModel;
import javafx.scene.paint.Color;

/**
 * Created by jotbills on 7/25/17.
 */
public class CellView implements IView {

    CellPolygon polygon;
    CellModel model;
    CellController controller;


    public CellView(CellPolygon polygon, CellModel model) {
        this.polygon = polygon;
        this.model = model;
        controller = polygon.getController();

        //drawing and positioning cell
        double x = model.getxCordinate();
        double y = model.getyCordinate();
        double yFactor = ((y * 10) - (x * 5)) * Math.sqrt(3) + 100;
        double xFactor = (x * 5 * Math.sqrt(3)) * Math.sqrt(3) + 100;
        Double[] points = {xFactor, yFactor, xFactor + 10, yFactor , xFactor + 15, yFactor + (5 * Math.sqrt(3)), xFactor + 10, yFactor + (10 * Math.sqrt(3)), xFactor, yFactor + (10 * Math.sqrt(3)),  xFactor - 5, yFactor + (5 * Math.sqrt(3))};

        polygon.init(points, Color.GREY, Color.BLACK, 1);


    }

    @Override
    public void draw() {

    }
}
