package Model;


import View.IView;

/**
 * Created by jotbills on 7/10/17.
 */
public class BoardModel {

    double ruleset = 0.5;
    private CellModel[][] myCells;
    private int height;
    private int width;
    private int currentTurn;
    private PieceModel restPiece;
    private boolean gameOver = false;
    private Initializer init;
    private Player[] players;
    boolean killEmAll = false;


    public BoardModel(Initializer init){
        this(init.data.length, init.data[0].length, init);



    }

    public BoardModel(int w, int h, Initializer I){
        init = I;
        height = h;
        width = w;
        if(init.config.killemall) killEmAll = true;
        //set players
        players = new Player[init.config.playerDirection.length];
        for(int i = 0; i < players.length; i++){
            players[i] = new Player();
            players[i].direction = init.config.playerDirection[i];
            players[i].color = init.config.playerColors[i];
            if(killEmAll) players[i].lives = 0;
            else players[i].lives = 1;
        }

        //set pieces
        myCells = new CellModel[width][height];
        for(int i = 0; i < width; i++){
            for(int k = 0; k < height; k++){
                if(k >= lowerBound(i) && k <= upperBound(i)) {
                    myCells[i][k] = new CellModel(i, k, this);
                }
            }
        }
        //blue starts
        currentTurn = 0;
    }


 //   public void setView(IView view) { this.view = view; }






    public int lowerBound(int n){
        //calculates the minimum y for a given x to keep it hexagonal
        // TODO: 7/22/17 refactor in terms of width and height
        switch (n){
            case 0:
                return 2;
            case 1:
                return 1;
            case 2:
                return 0;
            case 3:
                return 0;
            case 4:
                return 1;
            case 5:
                return 1;
            case 6:
                return 2;
            case 7:
                return 2;
            case 8:
                return 3;
            case 9:
                return 5;
            case 10:
                return 7;
            default:
                return 15;

        }
    }
    public int upperBound(int n){
        // TODO: 7/22/17 refactor in terms of width and height
        //calculates the maximum y for a given x to keep it hexagonal
        return 10 - lowerBound(10 - n);
    }

    public CellModel getCell(int x, int y){
        //returns cell with a given coordinate
        return myCells[x][y];
    }

    public int getTurn() {
        //used to determine whose turn it is
        return currentTurn;
    }

    public PieceModel getRestPiece() {
        //piece recovering from attack
        return restPiece;
    }

    public void setRestPiece(PieceModel restPiece) {
        this.restPiece = restPiece;
    }

    public void changeTurns(){

        if(!gameOver) {
            //piece no longer on rest after their turn is over
            if (restPiece != null && restPiece.getPlayer() == currentTurn) {
                restPiece = null;
            }
            //change turns
            int lastTurn = currentTurn;
            do {
                currentTurn++;
                currentTurn %= players.length;
            } while (players[currentTurn].lives < 1); //loop if player is out

            if(currentTurn == lastTurn){
                //all other players out
                gameOver = true;
            }
        }
           /* //update GUI
            if(isBlueTurn){
                myText.setText("Blue");
            }else{
                myText.setText("White");
            }

        }
        deselect();
        if(isSinglePlayer && isBlueTurn){
            bot.makeMove();
        }
        */

    }

    public void endGame(){
        gameOver = true;
        //deselect();
        /*if(isBlueTurn){
            myText.setText("Blue Wins");
        }else{
            myText.setText("White Wins");
        } */
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;

    }

    public Initializer getInit() {
        return init;
    }


    public Player getPlayer(int i){
        return players[i];
    }
}
