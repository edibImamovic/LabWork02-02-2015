package ba.bitcamp.edibi.server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ConnectionWriter extends Thread {

	public static HashMap<String, OutputStream> connections = new HashMap<String, OutputStream>();
	private Set<String> set = connections.keySet();

	@Override
	public void run() {
		while (true) {
			if (Message.hasNext()) {
				Message msq = Message.next();
				byte[] msgByte = (msq.getSender() + ":" + msq.getContent())
						.getBytes();
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String current = it.next();
					if (!current.equals(msq.getSender())) {
						OutputStream os = connections.get(current);

						try {
							os.write(msgByte);
							os.flush();

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}

	}
}
