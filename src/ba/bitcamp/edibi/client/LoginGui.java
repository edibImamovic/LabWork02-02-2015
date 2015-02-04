package ba.bitcamp.edibi.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ba.bitcamp.edibi.gui.ChatGui;

public class LoginGui {

	private JTextField username;
	private JPasswordField password;
	private String host;
	private int port;

	/**
	 * @param username
	 * @param password
	 */
	public LoginGui(String host, int port) {
		this.host = host;
		this.port = port;
		JFrame window = new JFrame("Login window");
		JPanel content = new JPanel();
		JButton buttonLogin = new JButton("Login");
		JButton buttonQuit = new JButton("Quit");

		username = new JTextField("Username");
		password = new JPasswordField();
		username.setPreferredSize(new Dimension(180, 20));

		password.setPreferredSize(new Dimension(180, 20));

		buttonQuit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});

		buttonLogin.addActionListener(new ButtonHandler());

		window.add(content);
		content.add(username);
		content.add(password);
		content.add(buttonLogin);
		content.add(buttonQuit);
		window.setSize(200, 110);
		window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private class ButtonHandler extends KeyAdapter implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String userName = username.getText();
			String passWord = new String(password.getPassword());

			userName = userName.replaceAll("", "");
			passWord = passWord.replaceAll("", "");

			System.out.println(userName);
			System.out.println(passWord);

			if (userName.equals("") || passWord.equals("")) {
				showError("Enter your password and username");
				return;
			}
			Socket client;
			try {
				client = new Socket(host, port);
				OutputStream os =client.getOutputStream();
				InputStream is = client.getInputStream();
				os.write((userName + "\n").getBytes());
				os.write((passWord + "\n").getBytes());
				
				int result =is.read();
				
				if(result ==0){
					ChatGui gui = new ChatGui(client);
					new Thread(gui).start();
				} else {
					showError("Username and password are not valid");
				}
			

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	private void showError(String message) {

		JOptionPane err = new JOptionPane();

		JOptionPane.showMessageDialog(null, message, "ERROR",
				JOptionPane.WARNING_MESSAGE);
	}
}
