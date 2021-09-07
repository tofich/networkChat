package aa.trusov.server;

import aa.trusov.AuthService;
import aa.trusov.ServerConst;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements ServerConst {
    //НАДО ПРИДУМАТЬ ЧТО-ТО НА ЗАМЕНУ ВЕКТОРУ
    private Vector<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService(){
        return authService;
    }

    public Server() {
        ServerSocket server;
        Socket socket;
        clients = new Vector<>();
        try {
            server = new ServerSocket(PORT);
            authService = new BaseAuthService();
            authService.start();
            System.out.println("Server up, awaiting clients.");
            while(true){
                socket = server.accept();
                clients.add(new ClientHandler(this, socket));
                System.out.println("New client connected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    public void broadcast(String msg){
        for (ClientHandler client : clients) {
            client.sendMessage(msg);
        }
    }

    public void unSubscribe(ClientHandler c){
       clients.remove(c);
        System.out.println("Client connection was closed");
    }

    public boolean isNickBusy(String nick){
        for (ClientHandler client : clients) {
            if(client.getNick().equals(nick)) return true;
        }
        return false;
    }
}
