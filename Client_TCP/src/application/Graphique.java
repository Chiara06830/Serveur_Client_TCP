package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Graphique {
	Socket client = null;
	BufferedReader in = null;
	PrintStream ps = null;
	
	public Graphique() {
		try {
			client = new Socket("localhost", 4500);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			ps = new PrintStream(client.getOutputStream());
			// Reception du premier message de bienvenue du serveur
			in.readLine();
		} catch (Exception e) {
			System.out.println("Le serveur a rencontrer un probléme et n'est plus disponible.");
			ps.println("bye");
			try {
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(1);
		}
	}
	
	public void envoieCommande(Socket client, PrintStream ps, String commande, String directive)
			throws Exception {
		String cmd = commande + " " + directive;
		ps.println(cmd);
	}
	
	public boolean connexion(String id, String mdp) throws Exception {
		// commande USER
		envoieCommande(client, ps, "user", id);
		String lu;
		do{
			lu = in.readLine();
			if (lu.charAt(0) == '2') {
				return false;
			}
		}while(lu.charAt(0) == '1');

		// commande PASS
		envoieCommande(client, ps,  "pass", mdp);
		do{
			lu = in.readLine();
			if (lu.charAt(0) == '2') {
				return false;
			}
			System.out.println("pass" + lu);
		}while(lu.charAt(0) == '1');
		
		//recup message confirmation
		do{lu = in.readLine();}while(lu.charAt(0) == '1');
		
		return true;
	}
	
	public String pwd() throws Exception {
		ps.println("pwd");
		return in.readLine().split(" ")[1];
	}
	
	public Map<String, Boolean> ls() throws Exception{
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		ps.println("ls");
		
		String lu;
		do{
			lu = in.readLine();
			if(lu.split(" ")[1].equals("#")) {
				map.put(lu.split(" ")[2], true);
			}else {
				map.put(lu.split(" ")[1], false);
			}
		}while(lu.charAt(0) == '1');
		
		return map;
	}
	
	public void deconnexion() throws IOException {
		ps.println("bye");
		in.readLine();
		ps.println("login");
	}
}
