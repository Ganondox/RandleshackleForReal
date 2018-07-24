package Controller.OnlineMultiplayer.Server;

import Controller.IBoardController;

/**
 * Created by jotbills on 8/2/17.
 */
public class ServerFacade implements IServer, IBoardController {
    @Override
    public int getOrder() {
        return 0;
    }
}
