package aa.trusov.server;

import aa.trusov.Server_API;
import aa.trusov.server.Server;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Server_API {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;

    public String getNick() {
        return nick;
    }
    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            nick = "undefined";
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
                try {
                    //авторизация
                    while(true) {
                        String msg = in.readUTF();
                        if (msg.startsWith(AUTH)) {
                            String[] elements = msg.split(" ");
                            String nick = server.getAuthService().getNickByLoginPass(elements[1], elements[2]);
                            if (nick != null) {
                                if (!server.isNickBusy(nick)) {
                                    sendMessage(AUTH_SUCCESSFUL + " " + nick);
                                    this.nick = nick;
                                    server.broadcast(this.nick + " has joined the chat");
                                    break;
                                } else sendMessage("This account is already in use!");
                            } else sendMessage("Wrong login/password!");
                        } else sendMessage("Your should authorize first!");
                    }
                    //оптравка сообщений
                    while(true){
                        String msg = in.readUTF();
                        if(msg.equalsIgnoreCase(CLOSE_CONNECTION)) break;
                        System.out.println(this.nick + ": " + msg);
                        server.broadcast(this.nick + ": " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    disconnect();
                    try{
                        socket.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
        }).start();
    }

    public void sendMessage(String msg){
        try {
            out.writeUTF(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        sendMessage("You have been disconnected.");
        server.unSubscribe(this);
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
