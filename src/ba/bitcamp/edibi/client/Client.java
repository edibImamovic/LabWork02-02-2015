package ba.bitcamp.edibi.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import ba.bitcamp.edibi.gui.ChatGui;

public class Client {
	public static final int port = 1717;
	public static final String host = "10.0.82.62";

	public static void main(String[] args) {

		try {

			Socket client = new Socket(host, port);
			System.out.println("Enter your name");
			Scanner sc = new Scanner(System.in);
			String name = sc.nextLine() + "\n";
			client.getOutputStream().write(name.getBytes());
			ChatGui gui = new ChatGui(client);
			new Thread(gui).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}