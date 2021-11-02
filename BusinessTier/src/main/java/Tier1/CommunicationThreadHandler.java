package Tier1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import org.springframework.web.cors.reactive.CorsWebFilter;

import Shared.User;
import Tier3.ServerCommunicator;

public class CommunicationThreadHandler implements Runnable {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    private  String ip;
    private ServerCommunicator communicator;
    private Gson gson;
    InputStream is;
    OutputStream os;
    User cashedUser;

    public CommunicationThreadHandler(Socket socket){
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(),true);
            try {
                communicator= ServerCommunicator.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            gson = new Gson();
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        while (true){
            
            try {
                byte[] lenBytes = new byte[4];
                is.read(lenBytes, 0, 4);
                int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
                  ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
                byte[] receivedBytes = new byte[len];
                is.read(receivedBytes, 0, len);
                String received = new String(receivedBytes, 0, len);

                switch(received)
                {
                    case "login": {
                        lenBytes = new byte[4];
                        is.read(lenBytes, 0, 4);
                            len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
                                ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
                        receivedBytes = new byte[len];
                        is.read(receivedBytes, 0, len);
                        received = new String(receivedBytes, 0, len);
                        User user = gson.fromJson(received, User.class);

                        String toSend = logIn(user);
                        byte[] toSendBytes = toSend.getBytes();
                        int toSendLen = toSendBytes.length;
                        byte[] toSendLenBytes = new byte[4];
                        toSendLenBytes[0] = (byte)(toSendLen & 0xff);
                        toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
                        toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
                        toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
                        os.write(toSendLenBytes);
                        os.write(toSendBytes);

                        if(toSend.equals("Successful"))
                        {
                            toSend = gson.toJson(cashedUser);
                            toSendBytes = toSend.getBytes();
                            toSendLen = toSendBytes.length;
                            toSendLenBytes = new byte[4];
                            toSendLenBytes[0] = (byte)(toSendLen & 0xff);
                            toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
                            toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
                            toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
                            os.write(toSendLenBytes);
                            os.write(toSendBytes);
                        }
                    }
                }

// System.out.println("Server received: " + received);

        // Sending
        

               /* String command = in.readLine();
                switch(command)
                {
                    case "login": {
                        String json = in.readLine();
                        User user = gson.fromJson(json, User.class);
                        out.println(logIn(user));
                    }
                }*/
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
                    cashedUser = users.get(i);
                    System.out.println(users.get(i));
                    return "Successful";
                } else return "Incorrect password";
            }
        }
        return "User not found";

    }
}   
