package ba.bitcamp.edibi.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConnectionListener extends Thread {

	private InputStream is;
	private String sender;

	public ConnectionListener(InputStream is, String sender) {
		this.is = is;
		this.sender = sender;
		
	}

	@Override
	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			String str = " ";
			while ((str = br.readLine()) != null) {
				if (!str.equals("")) {
				Message incomming = new Message(str, sender);
				System.out.println(incomming.getSender() + ":" + incomming.getContent());
				System.out.println("Listener:" + Message.hasNext());
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
