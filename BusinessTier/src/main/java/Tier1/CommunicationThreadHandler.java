package Tier1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import Shared.User;
import Tier3.ServerCommunicator;

public class CommunicationThreadHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private  String ip;
    private ServerCommunicator communicator;
    private Gson gson;

    public CommunicationThreadHandler(Socket socket){
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(),true);
            try {
                communicator= ServerCommunicator.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            gson = new Gson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        while (true){
            
            try {
                String command = in.readLine();
                switch(command)
                {
                    case "login": {
                        String json = in.readLine();
                        User user = gson.fromJson(json, User.class);
                        out.println(logIn(user));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String logIn(User user)
    {
        ArrayList<User> users = communicator.getUserFromDatabase();
        for(int i = 0; i < users.size(); i++){
            if(user.getUsername().equals(users.get(i).getUsername())){
                if(user.getPassword().equals(users.get(i).getPassword()))
                {
                    return "Successful";
                } else return "Incorrect password";
            }
        }
        return "User not found";

    }
}   
