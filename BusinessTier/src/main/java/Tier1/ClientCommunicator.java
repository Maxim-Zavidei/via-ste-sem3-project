package Tier1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientCommunicator {

    private ServerSocket welcomeSocket;

    public ClientCommunicator(int port){
        try {
            this.welcomeSocket = new ServerSocket(port);
            execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void execute(){
        while(true){
            try {
                System.out.println("Waiting for client....");
                Socket socket = this.welcomeSocket.accept();
                Thread thread = new Thread(new CommunicationThreadHandler(socket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
