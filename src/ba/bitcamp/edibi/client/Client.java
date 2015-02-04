package ba.bitcamp.edibi.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import ba.bitcamp.edibi.gui.ChatGui;

public class Client {
	public static final int port = 1717;
	public static final String host = "localhost";

	public static void main(String[] args) {

		LoginGui log = new LoginGui(host, port);
	}
}