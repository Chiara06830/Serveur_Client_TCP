/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		System.out.println("Serveur FTP actif");
		ServerSocket serveurFTP = null;
		try {
			serveurFTP = new ServerSocket(4500);
			 while(true){
				 Socket socket = serveurFTP.accept();
				 GestionUnClient unClient = new GestionUnClient(socket);
				 Thread t = new Thread(unClient);
				 t.start();
			 } 
		} catch (Exception e) {
			try {
				serveurFTP.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
