package main;
import network.Server;

public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server(12345);
        server.start();
    }
} 