package Tier1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


public class ClientCommunicator {

    /*@Primary
    @Bean
    void clientCommunicator(){
        try {
            this.welcomeSocket = new ServerSocket(8080);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

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
