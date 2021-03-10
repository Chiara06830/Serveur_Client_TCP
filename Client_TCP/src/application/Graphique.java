package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	public boolean envoieCommande(String commande, String nom) throws IOException {
		ps.println(commande+ " " + nom);
		
		String lu;
		do{
			lu = in.readLine();
			if(lu.charAt(0) == '2') {
				return false;
			}
		}while(lu.charAt(0) == '1');
		return true;
	}
	
	public boolean connexion(String id, String mdp) throws Exception {
		// commande USER
		ps.println("user " + id);
		String lu;
		do{
			lu = in.readLine();
			if (lu.charAt(0) == '2') {
				return false;
			}
		}while(lu.charAt(0) == '1');

		// commande PASS
		ps.println("pass " + mdp);
		do{
			lu = in.readLine();
			if (lu.charAt(0) == '2') {
				return false;
			}
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
		
		String[] lu;
		map.put("..", true);
		do{
			lu = in.readLine().split(" ");
			if(!lu[lu.length-1].equals("pw.txt")) {
				if(lu[1].equals("#")) {
					map.put(lu[2], true);
				}else {
					map.put(lu[1], false);
				}
			}
		}while(lu[0].equals("1"));
		
		return map;
	}
	
	public void deconnexion() throws IOException {
		ps.println("bye");
		
		String lu;
		do{lu = in.readLine();}while(lu.charAt(0) == '1');
		
		ps.println("login");
	}
	
	public void mv(String origine, String destination) throws IOException {
		ps.println("mv " + origine + " " + destination);
		
		String lu;
		do{lu = in.readLine();}while(lu.charAt(0) == '1');
	}
	
	public boolean get(String nom) throws IOException {
		ps.println("get " + nom);
		
		while (true) {
			String msg = in.readLine();
			System.out.println(msg);
			if ((msg.charAt(0)) == '2') {
				return false;
			}
			if ((msg.charAt(0)) == '0') {
				Socket transfertGet = new Socket("localhost", Integer.parseInt(msg.substring(2)));
				BufferedReader inTransfert = new BufferedReader(new InputStreamReader(transfertGet.getInputStream()));
				List<String> lignes = new ArrayList<String>();
				while (true) {
					String ligne = inTransfert.readLine();
					if ((ligne.charAt(0)) == '2') {
						inTransfert.close();
						transfertGet.close();
						return false;
					}
					if ((ligne.charAt(0)) == '0') {
						Path fichier = Paths.get(ligne.split(" ")[1]);
						Files.write(fichier, lignes, Charset.forName("UTF-8"));
						inTransfert.close();
						transfertGet.close();
						return true;
					}
					lignes.add(ligne.substring(2));
				}
			}
		}
	}
}
