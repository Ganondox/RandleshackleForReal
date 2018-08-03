package View;

import Controller.CellController;
import Misc.Old.CellPolygon;
import Model.CellModel;
import javafx.scene.paint.Color;

/**
 * Created by jotbills on 7/25/17.
 */
public class CellView implements IView {

    CellPolygon polygon;
    CellModel model;
    CellController controller;


    Color color;

    double yFactor;
    double xFactor;

    public CellView(CellPolygon polygon, CellModel model, BoardView view) {
        this.polygon = polygon;
        this.model = model;
        controller = polygon.getController();

        view.addCell(this);


        //drawing and positioning cell
        double x = model.getxCordinate();
        double y = model.getyCordinate();
        yFactor = ((y * 10) - (x * 5)) * Math.sqrt(3) + 100;
        xFactor = (x * 5 * Math.sqrt(3)) * Math.sqrt(3) + 100;
        Double[] points = {xFactor, yFactor, xFactor + 10, yFactor , xFactor + 15, yFactor + (5 * Math.sqrt(3)), xFactor + 10, yFactor + (10 * Math.sqrt(3)), xFactor, yFactor + (10 * Math.sqrt(3)),  xFactor - 5, yFactor + (5 * Math.sqrt(3))};

        polygon.init(points, Color.GREY, Color.BLACK, 1);
        color = Color.GREY;



    }

    @Override
    public void draw() {
        polygon.setFill(color);
    }

    public double getXFactor(){
        return xFactor;
    }

    public double getYFactor(){
        return yFactor;
    }

    public void setColor(Color color) {
        this.color = color;
        draw();
    }
}
