package aa.trusov.consoleClient;

//import java.awt.desktop.SystemEventListener;
import aa.trusov.ServerConst;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main implements ServerConst {
    static Socket socket = null;
    static Scanner in;
    static PrintWriter out;
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            socket = new Socket(SERVER_URL, PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(() -> {
                while(true){
                    if (in.hasNext()){
                        System.out.println("Сервер написал: " + in.nextLine());
                    }
                }
        });
        t1.setDaemon(true);
        t1.start();

        while(true){
            System.out.println("Введите текст сообщения: ");
            String textMsg = scan.nextLine();
            if (textMsg.equals("/exit")){
                try {
                    socket.close();
                    System.out.println("Вы отключены от сервера.");
                    //t1.interrupt();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                out.println(textMsg);
                out.flush();
            }

        }
    }
}
