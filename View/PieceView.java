package View;

import Model.PieceModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jotbills on 7/29/18.
 */
public class PieceView  {

    Image pieceImage;
    ImageView imageView;

    BoardView view;

    PieceModel daPiece;

    boolean wasPowered;

   public PieceView(PieceModel piece, BoardView view){


       this.view = view;

        daPiece = piece;
        wasPowered = piece.isPowered();

        String imagurl = "res/";

        Color color = daPiece.getMyBoard().getPlayer(daPiece.getPlayer()).getColor();

        switch (color){
            case BLUE:
                imagurl += "blue" + daPiece.getClassname() + ".png";
                break;
            case WHITE:
                imagurl += "white" + daPiece.getClassname() + ".png";
                break;
            default:
                imagurl = "";
        }

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
        view.getPane().getChildren().add(imageView);
        view.addPiece(this);

    }

    public void draw(){

        view.getPane().getChildren().remove(imageView);
        if(daPiece.isAlive()) {

            if( wasPowered != daPiece.isPowered()){
                wasPowered = daPiece.isPowered();
                //update imageview

                String foot;
                if(wasPowered) foot = ".png";
                else foot = "X.png";

                String imagurl = "res/";

                Color color = daPiece.getMyBoard().getPlayer(daPiece.getPlayer()).getColor();

                switch (color){
                    case BLUE:
                        imagurl += "blue" + daPiece.getClassname() + foot;
                        break;
                    case WHITE:
                        imagurl += "white" + daPiece.getClassname() + foot;
                        break;
                    default:
                        imagurl = "";
                }
                System.out.println(imagurl);
                pieceImage = new Image(imagurl);
                imageView = new ImageView(pieceImage);


            }


            imageView.setX(this.getXFactor());
            imageView.setY(this.getYFactor());
            view.getPane().getChildren().add(imageView);
        }
    }

    public double getXFactor(){
        //converts hexagonal coordinate to rectangular one
        return (daPiece.getxCordinate() * 5 * Math.sqrt(3)) * Math.sqrt(3) + 100;
    }

    public double getYFactor(){
        //converts hexagonal coordinate to rectangular one
        return ((daPiece.getyCordinate() * 10) - (daPiece.getxCordinate() * 5)) * Math.sqrt(3) + 100;
    }

}
