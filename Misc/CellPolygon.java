package Misc;

import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

import Controller.CellController;
import View.BoardView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;

/**
 * Created by jotbills on 7/22/17.
 */
public class CellPolygon extends Polygon {


  private CellController controller;
  BoardView view;

  public CellPolygon(CellController handler, BoardView board){
      super();
      controller = handler;
      view = board;


  }

    public CellController getController() {
        return controller;
    }


    public void init(Double [] points, Color fillcolor, Color strokecolor, int strokewidth){
        this.getPoints().addAll(points);
        this.setFill(fillcolor);
        this.setStroke(strokecolor);
        this.setStrokeWidth(strokewidth);

        view.getPane().getChildren().add(this);

        //setting on click
        this.setOnMouseClicked(clickHandler);
    }

    private EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Cell me = (Cell)mouseEvent.getSource();
            controller.onClicked();


        }
    };
}
