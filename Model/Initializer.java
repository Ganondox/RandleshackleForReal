package Model;

import Misc.LunarBot;

import java.io.FileReader;
import java.io.InputStream;

/**
 * Created by jotbills on 7/25/17.
 */
public class Initializer {



    PieceMock[][] data;
    LunarBot[] AIs;
    Config config;


    public Initializer(PieceMock[][] data, LunarBot[] AIs, Config config) {
        this.data = data;
        this.AIs = AIs;
        this.config = config;
    }
}
