package fr.dauphine.JavaAvance.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket;

	public Server(int port) {
		int comPort = port > 0 ? port : 1234;
		try {
			this.serverSocket = new ServerSocket(comPort);
		} catch (IOException e) {

			System.out.println("Unable to reserve ports");
			e.printStackTrace();
		}
	}
	public void start() throws Exception {

		boolean stop = false;
		System.out.println("Waiting for a connection request...");
		Socket socket = this.serverSocket.accept();
		System.out.println("connection is established ");

		while(stop) {

		}
		socket.close();
	}

	public static void main(String[] args) {

		Server server = new Server(1234);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
