import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class myChatServer {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    public myChatServer() throws IOException {
        // Создаем серверный сокет на порту 1234
        serverSocket = new ServerSocket(1234);
    }

    void sendAll (String message) {
        for(Client client : clients){
            client.receive(message);
        }
    }

    public void Run(){
        while(true){
            System.out.println("Waiting ...");

            try {
                // Ждем клиента из сети
                Socket socket = serverSocket.accept();
                System.out.println("Client " + socket + " connected!");
                // Создаем клиента на своей стороне
/*
                Client client = new Client(socket);
*/
                clients.add(new Client(socket, this));

            } catch (IOException e) {
                e.printStackTrace();
            }

            }
    }

    public static void main(String[] args) throws IOException {
        new myChatServer().Run();
    }
}
