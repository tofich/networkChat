package aa.trusov.client;

import aa.trusov.ServerConst;
import aa.trusov.Server_API;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientConnection implements ServerConst, Server_API {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    Thread clientThread;
    private boolean isAuthorized = false;

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public boolean isAuthorized(){
        return isAuthorized;
    }

    public ClientConnection() {}

    public void init(Controller view){
        try {
            this.socket = new Socket(SERVER_URL,PORT);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.clientThread = new Thread(() -> {
                    try {
                        while (true){
                            String msg = in.readUTF();
                            if(msg.startsWith(AUTH_SUCCESSFUL)){
                                setAuthorized(true);
                                view.switchWindows();
                                break;
                            }
                            view.showMessage(msg);
                        }

                        while(true){
                            String msg = in.readUTF();
                            view.showMessage(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
            clientThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void auth(String login, String password){
        try {
            out.writeUTF(AUTH + " " + login + " " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            out.writeUTF(CLOSE_CONNECTION);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSuccessConnectionTextLabel(){
      return SERVER_SUCCESS_CONNECTION;
    }

}
