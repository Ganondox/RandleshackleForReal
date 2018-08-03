package Model;

import AI.Bot;

/**
 * Created by jotbills on 7/25/17.
 */
public class Initializer {



    PieceMock[][] data;
    Bot[] AIs;
    Config config;


    public Initializer(PieceMock[][] data, Bot[] AIs, Config config) {
        this.data = data;
        this.AIs = AIs;
        this.config = config;
    }

    public Bot getBot(int i){
        return AIs[i];
    }
}
