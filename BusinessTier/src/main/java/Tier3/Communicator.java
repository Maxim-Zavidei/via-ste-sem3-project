package Tier3;

import Shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Communicator {
  private ObjectOutputStream outToServer;
  private ObjectInputStream inFromServer;


  private static Communicator instance;

  public synchronized static Communicator getInstance() throws Exception {
    if (instance == null) {
      instance = new Communicator();
    }
    return instance;
  }

  private Communicator() throws IOException {

    Socket socket = new Socket("localhost", 1098);

    outToServer = new ObjectOutputStream(socket.getOutputStream());
    inFromServer = new ObjectInputStream(socket.getInputStream());

  }

  public User getUserFromDatabase(String username) {
    try {
      Request request = new Request("getUser", username);
      outToServer.writeObject(request);
      UserDTO userDto = (UserDTO) inFromServer.readObject();
      User user = new User();
      if (userDto != null) {
        user.setUserId(userDto.getUserId());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setSecurityLevel(userDto.getSecurityLevel());
        System.out.println(user.getUserId());
        return user;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}