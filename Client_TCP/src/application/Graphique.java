package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	
	public String emplacement = "espaceClient";
	
	public Graphique() {
		try {
			client = new Socket("localhost", 4500);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			ps = new PrintStream(client.getOutputStream());
			// Reception du premier message de bienvenue du serveur
			String lu;
			do{
				lu = in.readLine();
				System.out.println(lu);
			}while(lu.charAt(0) == '1');
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
			System.out.println(lu);
			if(lu.charAt(0) == '2') {
				return false;
			}
		}while(lu.charAt(0) == '1');
		return true;
	}
	
	public boolean connexion(String id, String mdp) throws Exception {
		// commande USER
		if(!this.envoieCommande("user", id)) {
			return false;
		}

		// commande PASS
		if(!this.envoieCommande("pass", mdp)) {
			return false;
		}
		
		return true;
	}
	
	//---------------------COTE SERVER---------------------//
	
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
			String ligne = in.readLine();
			lu = ligne.split(" ");
			
			if(!lu[lu.length-1].equals("pw.txt") && !ligne.equals("0 Aucun fichier n'est présent sur ce repertoire")) {
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
		this.envoieCommande("bye", "");
		this.envoieCommande("login", "");
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
	
	//---------------------COTE CLIENT---------------------//
	
	public Map<String, Boolean> lsClient(){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		if(!this.emplacement.equals("espaceClient")) {//si on est pas a la racine
			map.put("..", true);//on offre la possibilté de remonter
		}
		
		File[] fichiers = new File("." + File.separator + this.emplacement).getAbsoluteFile().listFiles();
		if (fichiers != null && fichiers.length > 0) {
			for(int i=0; i<fichiers.length; i++) {
				if(fichiers[i].isDirectory()) {
					map.put(fichiers[i].getName(), true);
				}else {
					map.put(fichiers[i].getName(), false);
				}
			}
		}
		
		return map;
	}
	
	public String pwdClient() {
		return new File(this.emplacement).getAbsoluteFile().toString();
	}
	
	public boolean cd(String nom) {
		if(nom.equals("..")) {
			String[] chemin  = this.emplacement.split("\\" + File.separator);
			this.emplacement = this.emplacement.substring(0, this.emplacement.length() - (chemin[chemin.length-1].length()+1));
		}else {
			this.emplacement += File.separator + nom;
		}
		return true;
	}

	public boolean stor(String argument) throws IOException {
		File file = new File(this.emplacement + File.separator + argument).getAbsoluteFile();
		// C'est un fichier ? && Ce n'est pas un repertoire ?
		if (file.exists() && !file.isDirectory()) {
			return this.storOneFile(argument, file);
		} /*else if(file.exists() && file.isDirectory()){
			File[] contenu = new File(this.emplacement + File.separator + argument).getAbsoluteFile().listFiles();
			for(int i=0; i<contenu.length; i++) {
				if(!this.storOneFile((argument + File.separator + contenu[i].getName()), contenu[i])) {
					return false;
				}
			}
			return true;
		}*/else {
			System.out.println("Le fichier est introuvable.");
			return false;
		}
	}
	
	public boolean storOneFile(String argument, File file) throws IOException {
		ps.println("stor " + argument);
		while (true) {
			String msg = in.readLine();
			System.out.println(msg);
			if ((msg.charAt(0)) == '2') {
				return false;
			}
			if ((msg.charAt(0)) == '0') {
				Socket transfertStor = new Socket("localhost", Integer.parseInt(msg.substring(2)));
				BufferedReader inTransfert = new BufferedReader(new FileReader(file));
				PrintStream psTransfert = new PrintStream(transfertStor.getOutputStream());

				String ligne;
				while ((ligne = inTransfert.readLine()) != null)
					psTransfert.println("1 " + ligne);
				psTransfert.println("0 " + argument + " : Transfert terminé.");

				inTransfert.close();
				psTransfert.close();
				transfertStor.close();
				return true;
			}
		}
	}
}
