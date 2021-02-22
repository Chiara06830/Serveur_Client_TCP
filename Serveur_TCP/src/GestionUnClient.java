/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class GestionUnClient implements Runnable {
	private Socket socket;
	private String emplacement = ".";
	public boolean userOk = false ;
	public boolean pwOk = false ;
	public int prochainPort = 4501;

	public GestionUnClient(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			System.out.println("Le client c'est connecté.");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream ps = new PrintStream(socket.getOutputStream());
			
			ps.println("1 Bienvenue ! ");
			ps.println("1 Serveur FTP Personnel.");
			ps.println("0 Authentification : ");
			
			String commande = "";
			
			// Attente de reception de commandes et leur execution
			while(!(commande=br.readLine()).equals("bye")) {
				System.out.println(">> "+commande);
				CommandExecutor.executeCommande(ps, commande, this);
			}
			System.out.println("Le client c'est déconnecté.");
			setUserOk(false);
			setPwOk(false);
			setEmplacement(".");
			socket.close();
		} catch (IOException e) {
			System.out.println("Le client c'est déconnecté.");
			setUserOk(false);
			setPwOk(false);
			setEmplacement(".");
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

	public boolean isUserOk() {
		return userOk;
	}

	public void setUserOk(boolean userOk) {
		this.userOk = userOk;
	}

	public boolean isPwOk() {
		return pwOk;
	}

	public void setPwOk(boolean pwOk) {
		this.pwOk = pwOk;
	}

	public int getProchainPort() {
		return prochainPort;
	}

	public void setProchainPort(int prochainPort) {
		this.prochainPort = prochainPort;
	}

}
