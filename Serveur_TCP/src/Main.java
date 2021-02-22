/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		System.out.println("Le Serveur FTP");
		
		ServerSocket serveurFTP = null;
		Socket socket = null;
		try {
			serveurFTP = new ServerSocket(4500);
			socket = serveurFTP.accept();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream ps = new PrintStream(socket.getOutputStream());
			
			ps.println("1 Bienvenue ! ");
			ps.println("1 Serveur FTP Personnel.");
			ps.println("0 Authentification : ");
			
			String commande = "";
			
			// Attente de reception de commandes et leur execution
			while(!(commande=br.readLine()).equals("bye")) {
				System.out.println(">> "+commande);
				CommandExecutor.executeCommande(ps, commande);
			}
			
			serveurFTP.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Le client c'est déconnecté.");
			try {
				serveurFTP.close();
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			main(args);
		}
	}

}
