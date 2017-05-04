import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Created with IntelliJ IDEA.
 * User: 36483
 * Date: 1/23/14
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Cell extends Polygon {

    private Board myBoard;
    private Piece myPiece;
    private int xCordinate;
    private int yCordinate;

    //movement calculations
    public boolean isOpen = false;
    public boolean isVulnerable = false;
    public boolean isDrainSpot = false;




    private EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Cell me = (Cell)mouseEvent.getSource();
            me.onClicked();


        }
    };


    public Cell( double x, double y, Board b) {
        super();
        //setting hexagonal coordinates
        xCordinate = (int)x;
        yCordinate = (int)y;
        //drawing and positioning cell
        double yFactor = ((y * 10) - (x * 5)) * Math.sqrt(3) + 100;
        double xFactor = (x * 5 * Math.sqrt(3)) * Math.sqrt(3) + 100;
        Double[] points = {xFactor, yFactor, xFactor + 10, yFactor , xFactor + 15, yFactor + (5 * Math.sqrt(3)), xFactor + 10, yFactor + (10 * Math.sqrt(3)), xFactor, yFactor + (10 * Math.sqrt(3)),  xFactor - 5, yFactor + (5 * Math.sqrt(3))};

        this.getPoints().addAll(points);
        this.setFill(Color.GREY);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
        myBoard = b;

        b.getPane().getChildren().add(this);

        //setting on click
        this.setOnMouseClicked(clickHandler);

        //creating initial piece, if it has one
        myPiece = generatePiece();



    }

    public int getxCordinate() {
        return xCordinate;
    }

    public int getyCordinate() {
        return yCordinate;
    }

    public double getXFactor(){
        return (xCordinate * 5 * Math.sqrt(3)) * Math.sqrt(3) + 100;
    }

    public double getYFactor(){
       return ((yCordinate * 10) - (xCordinate * 5)) * Math.sqrt(3) + 100;
    }

    public Piece getPiece() {
        return myPiece;
    }

    public void setPiece(Piece myPiece) {
        this.myPiece = myPiece;
    }

    private Piece generatePiece(){
        //sets initial piece based on cells location
        Piece newPiece = null;
        switch (xCordinate){
            case 2:
                switch (yCordinate){
                case 0:
                     newPiece = new Mule(xCordinate, yCordinate, myBoard);
                    break;
                 case 1:
                     newPiece = new Mule(xCordinate, yCordinate, myBoard);
                     break;
                 case 6:
                     newPiece = new Mule(xCordinate, yCordinate, myBoard);
                     break;
                 case 7:
                     newPiece = new Mule(xCordinate, yCordinate, myBoard);
                     break;
                }
                break;
            case 3:
                switch (yCordinate){
                    case 0:
                        newPiece = new Pegasus(xCordinate, yCordinate, myBoard);
                        break;
                    case 1:
                        newPiece = new Pegasus(xCordinate, yCordinate, myBoard);
                        break;
                    case 2:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 6:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 7:
                        newPiece = new EarthPony(xCordinate, yCordinate, myBoard);
                        break;
                    case 8:
                        newPiece = new EarthPony(xCordinate, yCordinate, myBoard);
                        break;
                }
                break;
            case 4:
                switch (yCordinate){
                    case 1:
                        newPiece = new Unicorn(xCordinate, yCordinate, myBoard);
                        break;
                    case 2:
                        newPiece = new Pegasus(xCordinate, yCordinate, myBoard);
                        break;
                    case 3:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 6:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 7:
                        newPiece = new EarthPony(xCordinate, yCordinate, myBoard);
                        break;
                    case 8:
                        newPiece = new Unicorn(xCordinate, yCordinate, myBoard);
                        break;
                }
                break;
            case 5:
                switch (yCordinate){
                    case 1:
                        newPiece = new Alicorn(xCordinate, yCordinate, myBoard);
                        break;
                    case 2:
                        newPiece = new Unicorn(xCordinate, yCordinate, myBoard);
                        break;
                    case 3:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 7:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 8:
                        newPiece = new Unicorn(xCordinate, yCordinate, myBoard);
                        break;
                    case 9:
                        newPiece = new Alicorn(xCordinate, yCordinate, myBoard);
                        break;
                }
                break;
            case 6:
                switch (yCordinate){
                    case 2:
                        newPiece = new Unicorn(xCordinate, yCordinate, myBoard);
                        break;
                    case 3:
                        newPiece = new EarthPony(xCordinate, yCordinate, myBoard);
                        break;
                    case 4:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 7:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 8:
                        newPiece = new Pegasus(xCordinate, yCordinate, myBoard);
                        break;
                    case 9:
                        newPiece = new Unicorn(xCordinate, yCordinate, myBoard);
                        break;
                }
                break;
            case 7:
                switch (yCordinate){
                    case 2:
                        newPiece = new EarthPony(xCordinate, yCordinate, myBoard);
                        break;
                    case 3:
                        newPiece = new EarthPony(xCordinate, yCordinate, myBoard);
                        break;
                    case 4:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 8:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 9:
                        newPiece = new Pegasus(xCordinate, yCordinate, myBoard);
                        break;
                    case 10:
                        newPiece = new Pegasus(xCordinate, yCordinate, myBoard);
                        break;
                }
                break;
            case 8:
                switch (yCordinate){
                    case 3:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 4:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 9:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                    case 10:
                        newPiece = new Mule(xCordinate, yCordinate, myBoard);
                        break;
                }
                break;


        }
        return newPiece;
    }
    public void onClicked(){
        /*this.setFill(Color.BLACK);
        for(int i = 1; i <= 6; i++){
            if(this.getNeighbor(i) != null) this.getNeighbor(i).setFill(Color.BEIGE);
        } */
        //return hexagonal cordinates when pressed
        System.out.println(this.getxCordinate() + "|" + this.getyCordinate());

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

    }

    public Cell getNeighbor(int n){
        //calculates which cells is in certain direction of the this cell
        int x;
        int y;
        switch (n){
            case 1:
                 //top left
                 y = yCordinate - 1;
                 x = xCordinate - 1;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case 2:
                 //top
                 y = yCordinate - 1;
                 x = xCordinate + 0;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case 3:
                //top right
                y = yCordinate + 0;
                x = xCordinate + 1;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case 4:
                //bottom right
                y = yCordinate + 1;
                x = xCordinate + 1;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case 5:
                //bottom
                y = yCordinate + 1;
                x = xCordinate - 0;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            case 6:
                //bottom left
                y = yCordinate - 0;
                x = xCordinate - 1;
                if(y >= myBoard.lowerBound(x) && y <= myBoard.upperBound(x)){
                    return myBoard.getCell(x,y);

                } else  return  null;
            default:
                return  null;
        }
    }


}
/* cells with pieces
3|0
4|1
5|1
6|2
7|2
2|0
3|1
4|2
5|2
6|3
7|3
8|3
5|3
2|1
3|2
7|4
8|4
3|8
2|7
2|6
3|7
3|6
4|7
4|3
6|4
4|8
4|6
5|7
5|8
5|9
6|7
6|8
6|9
7|8
7|9
7|10
8|9
8|10

mules
2|0
2|1
3|2
4|3
5|3
6|4
7|4
8|4
8|3
2|7
2|6
3|6
4|6
5|7
6|7
7|8
8|9
8|10

earth ponies
6|3
7|3
7|2
4|7
3|7
3|8

unicorns
4|1
5|2
6|2
4|8
5|8
6|9

pegasi
3|0
3|1
4|2
6|8
7|9
7|10

alicorns
5|1
5|9
*/