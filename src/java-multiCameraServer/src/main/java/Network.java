import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.IntStream;

public class Network {
    public static final int PORT = 3191;
    public static void main(String[] args) throws Exception{
        new Network();
    }
    
    ServerSocket serverSocket = new ServerSocket( PORT);
    Socket socket = serverSocket.accept();

    public Network() throws IOException {
    }

    public void sendPacket(Packet packet) throws IOException {
        ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
        outStream.writeObject(packet.matByte);
    }

    public void closeNetwork() throws IOException {
        socket.close();
        serverSocket.close();
    }
}
