package Model;

/**
 * Created by jotbills on 7/25/17.
 */
public class PieceMock {

     Type type;
    int playerNumber;
    int hits;

    public enum Type{ MULE, EARTHPONY, UNICORN, PEGASUS, ALICORN
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }
}
