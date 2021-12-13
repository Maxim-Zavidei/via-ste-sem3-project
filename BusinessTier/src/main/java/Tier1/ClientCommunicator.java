package Tier1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientCommunicator {
    private ServerSocket welcomeSocket;

    public ClientCommunicator(int port){
        try {
            this.welcomeSocket = new ServerSocket(port);
            execute();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void execute(){
        while(true){
            try {
                System.out.println("Waiting for client....");
                Socket socket = this.welcomeSocket.accept();
                System.out.println("Client connected");

                Thread thread = new Thread(new CommunicationThreadHandler(socket));
                thread.start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
