package Tier1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.google.gson.Gson;


import Shared.User;

public class CommunicationThreadHandler implements Runnable {
    private Socket socket;
    private  String ip;
    private UserController userController;
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
            userController = new UserController();
            gson = new Gson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        while (true){
            try {
                String received = read();
                switch(received)
                {
                    case "login": {
                        received = read();
                        User user = gson.fromJson(received, User.class);
                        String toSend = userController.logIn(user);
                        send(toSend);
                        if(toSend.equals("Successful"))
                        {
                            cashedUser = userController.getCashedUser();
                            toSend = gson.toJson(cashedUser);
                            send(toSend);
                        }
                    } break;
                    case "adduser": {
                        received = read();
                        User user = gson.fromJson(received, User.class);
                        String toSend = userController.addUser(user);
                        send(toSend);
                        if(toSend.equals("Successful"))
                        {
                            cashedUser = userController.getCashedUser();
                            toSend = gson.toJson(cashedUser);
                            send(toSend);
                        }
                    } break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private  synchronized String read()
    {
        String received = "";
        try{
            byte[] lenBytes = new byte[4];
            is.read(lenBytes, 0, 4);
            int len = (((lenBytes[3] & 0xff) << 24) | ((lenBytes[2] & 0xff) << 16) |
              ((lenBytes[1] & 0xff) << 8) | (lenBytes[0] & 0xff));
            byte[] receivedBytes = new byte[len];
            is.read(receivedBytes, 0, len);
            received = new String(receivedBytes, 0, len);
            return received;
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return received;
    }

    private synchronized void send(String toSend)
    {
        try{
            byte[] toSendBytes = toSend.getBytes();
            int toSendLen = toSendBytes.length;
            byte[] toSendLenBytes = new byte[4];
            toSendLenBytes[0] = (byte)(toSendLen & 0xff);
            toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
            toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
            toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
            os.write(toSendLenBytes);
            os.write(toSendBytes);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

   
}   
