package Tier1;

import Shared.Event;
import Shared.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CommunicationThreadHandler implements Runnable {
    private Socket socket;
    private  String ip;
    private UserController userController;
    private EventController eventController;
    private Gson gson;
    InputStream is;
    OutputStream os;
    public User cashedUser;

    public CommunicationThreadHandler(Socket socket){
        this.socket = socket;
        this.ip = socket.getInetAddress().getHostAddress();
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            userController = new UserController();
            eventController = new EventController();
            gson = new Gson();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void run(){
        while (true){
            try {
                String received = read();
                String toSend = "";
                switch(received)
                {
                    case "login": {
                        received = read();
                        User user = gson.fromJson(received, User.class);
                        toSend = userController.logIn(user);
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
                        toSend = userController.addUser(user);
                        if(toSend==null) toSend = "Could not create user";
                        send(toSend);
                        if(toSend.equals("Successful"))
                        {
                            cashedUser = userController.getCashedUser();
                            toSend = gson.toJson(cashedUser);
                            send(toSend);
                        }

                    } break;
                    case "fetchusers":{
                        ArrayList<User> users = userController
                            .FetchUsersFromDatabase();
                        if(users==null){toSend = "No users in database";
                        }
                        else{
                            toSend = gson.toJson(users);
                        }
                        send(toSend);
                        break;
                    }
                    case "deleteuser":{
                        try
                        {
                            received = read();
                            int userId = Integer.parseInt(received);
                            userController.deleteUser(userId);
                            //Better ideas for comm flow//
                            toSend = "Success";
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        send(toSend);
                    } break;
                    case "getMyEvents":{
                        try
                        {
                            received = read();
                            ArrayList<Event> events = eventController
                                .fetchUserEventsFromDatabase(Integer.parseInt(received));
                            toSend = gson.toJson(events);
                            send(toSend);
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                            send(toSend);
                        }
                        break;
                    }
                    case "changeSharingStatus":{
                        try{
                            received = read();
                            int userId = Integer.parseInt(received);
                            userController.changeSharingStatus(userId);
                            //Better ideas for comm flow//
                            toSend = "Success";
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        send(toSend);
                    }break;
                    case "close": {
                        socket.shutdownInput();
                        socket.shutdownOutput();
                        Thread.currentThread().interrupt();
                    } break;
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
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
            Thread.currentThread().interrupt();
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
