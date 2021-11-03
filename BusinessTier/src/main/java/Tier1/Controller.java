package Tier1;

import Tier3.ServerCommunicator;

public class Controller {
    protected ServerCommunicator communicator;

    public Controller(){
        try {
            this.communicator= ServerCommunicator.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
