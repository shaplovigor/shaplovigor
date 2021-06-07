import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


class Client implements Runnable {
    Socket socket;
    Scanner in;
    PrintStream out;
    myChatServer server;

    public Client(Socket socket, myChatServer server) {
        this.socket = socket;
        this.server = server;
        // запускаем поток
        new Thread(this).start();
    }

    void receive(String message) {
        out.println(message);
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобное средство ввода вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            //читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            String input = in.nextLine();
            while (!input.equals("bye")){
                server.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
