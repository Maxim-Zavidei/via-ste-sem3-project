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
    //private  String ip;
    private UserController userController;
    private EventController eventController;
    private Gson gson;
    InputStream is;
    OutputStream os;
    public User cashedUser;

    public CommunicationThreadHandler(Socket socket){
        this.socket = socket;
        //this.ip = socket.getInetAddress().getHostAddress();
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
                        try
                        {
                            received = read();
                            User user = gson.fromJson(received, User.class);
                            toSend = userController.logIn(user);
                            send("Successful");
                            cashedUser = userController.getCashedUser();
                            toSend = gson.toJson(cashedUser);
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        finally
                        {
                            send(toSend);
                        }
                    } break;
                    case "addUser":
                    {
                        try
                        {
                            received = read();
                            User user = gson.fromJson(received, User.class);
                            toSend = userController.addUser(user);
                            send(toSend);
                            cashedUser = userController.getCashedUser();
                            toSend = gson.toJson(cashedUser);
                        }
                        catch (Exception e)
                        {
                          toSend = e.getMessage();
                        }
                        finally
                        {
                            send(toSend);
                        }
                    } break;
                    case "fetchUsers":{
                      try
                      {
                        ArrayList<User> users = userController
                            .fetchUsersFromDatabase();
                          toSend = gson.toJson(users);
                      }
                      catch (Exception e)
                      {
                        toSend = e.getMessage();
                      }
                      finally
                      {
                          send(toSend);
                      }
                    }break;
                    case "fetchSharingUsers":{
                        try
                        {
                            ArrayList<User> users = userController.fetchUsersSharingFromDatabase();
                            toSend = gson.toJson(users);
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        finally
                        {
                            send(toSend);
                        }

                    }break;
                    case "deleteUser":{
                        try
                        {
                            received = read();
                            int userId = Integer.parseInt(received);
                            userController.deleteUser(userId);
                            //Better ideas for comm flow?//
                            toSend = "Success";
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        finally
                        {
                            send(toSend);
                        }
                    } break;
                    case "fetchEvents":{
                        try
                        {
                            received = read();
                            ArrayList<Event> events = eventController
                                .FetchUserEventsFromDatabase(Integer.parseInt(received));
                            toSend = gson.toJson(events);
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        finally
                        {
                            send(toSend);
                        }

                    }break;
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
                        finally
                        {
                            send(toSend);
                        }
                    }break;
                    case "getSharingStatus":{
                        try{
                            received = read();
                            int userId = Integer.parseInt(received);
                            boolean sharingStatus = userController.getSharingStatus(userId);
                            send("Successful");
                            toSend = gson.toJson(sharingStatus);
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        finally
                        {
                            send(toSend);
                        }
                    }break;
                    case "addEvent":{
                        try{
                            received = read();
                            Event evt = gson.fromJson(received, Event.class);
                            received = read();
                            int userId = Integer.parseInt(received);
                            evt = eventController.addEvent(userId, evt);
                            send("Successful");
                            toSend = gson.toJson(evt);
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        finally
                        {
                            send(toSend);
                        }
                    }break;
                    case "addSharedEvent":{
                        try{
                            received = read();
                            Event evt = gson.fromJson(received, Event.class);
                            received = read();
                            int userId = Integer.parseInt(received);
                            received = read();
                            int otherUserId = Integer.parseInt(received);
                            evt = eventController.addSharedEvent(userId, evt, otherUserId);
                            toSend = gson.toJson(evt);
                        }
                        catch (Exception e)
                        {
                            toSend = e.getMessage();
                        }
                        finally
                        {
                            send(toSend);
                        }
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
