import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		// Initialisation des composantes pour la reception et l'envoie de messages
		Socket client = new Socket("localhost", 4500);
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		OutputStream os = client.getOutputStream();
		PrintStream ps = new PrintStream(os);
		Scanner sc = new Scanner(System.in);
				
		// Reception du premier message de bienvenue du serveur
		lecture(client, in);
		
		// Connexion
		connexion(client, ps, in, sc);
		
		// Lancement du terminal de commande
		choisirCommande(client, ps, in, sc, os);
	}
	
	public static void connexion(Socket client, PrintStream ps, BufferedReader in, Scanner sc) throws IOException {
		// commande USER
		envoieCommande(client, ps, sc, "user", "Veuillez rentrez votre identifiant : ");
		while (!lecture(client, in)) {
			envoieCommande(client, ps, sc, "user", "Veuillez rentrez votre identifiant : ");
		}
		// commande PASS
		envoieCommande(client, ps, sc, "pass", "Veuillez rentrez votre mot de passe : ");
		while (!lecture(client, in)) {
			envoieCommande(client, ps, sc, "pass", "Veuillez rentrez votre mot de passe : ");
		}
		System.out.println();
	}
	
	public static void choisirCommande(Socket client, PrintStream ps, BufferedReader in, Scanner sc, OutputStream os) throws IOException {
		boolean onContinue = true;
		while (onContinue) {
			System.out.print(">> ");
			String cmd = sc.nextLine();
			switch (cmd) {
			case "bye" :
				onContinue = false;
				break;
			case "cd":
				// Pas encore fonctionnel (pas de retour d'erreur)
				envoieCommande(client, ps, sc, "cd", "Veuillez rentrez le chemin (absolu ou relatif) du r�pertoire que vous souhaitez acc�der : ");
				if (!lecture(client, in)) {
					System.out.print("argument incorrect - commandes disponibles : (cd,get,ls,pwd,stor,bye)\n");
				}
				break;
			case "get":
				envoieCommande(client, ps, sc, "get", "Veuillez rentrez le nom (absolu ou relatif) du fichier � lire : ");
				if (!lectureGet(client, in)) {
					System.out.print("argument incorrect - commandes disponibles : (cd,get,ls,pwd,stor,bye)\n");
				}
				break;
			case "ls" :
				envoieCommande(client, ps, null, "ls", null);
				lecture(client, in);
				break;
			case "pwd" :
				envoieCommande(client, ps, null, "pwd", null);
				lecture(client, in);
				break;
			case "stor" :
				// A FAIRE
				break;
			default:
				System.out.print("commande inconnu - commandes disponibles : (cd,get,ls,pwd,stor,bye)\n");
			}
		}
		deconnexion(client, ps, in, sc, os);
	}
	
	public static void deconnexion(Socket client, PrintStream ps, BufferedReader in, Scanner sc, OutputStream os) throws IOException {
		// Fermeture du serveur
		envoieCommande(client, ps, null, "bye", null);
		lecture(client, in);
		
		// Demande de reconnexion
		System.out.print("Souhaitez vous ferm� le client ? commandes disponibles : (quit, login)\n");
		boolean onContinue = true;
		while (onContinue) {
			System.out.print(">> ");
			String cmd = sc.nextLine();
			switch (cmd) {
			// Reconnexion
			case "login" :
				connexion(client, ps, in, sc);
				choisirCommande(client, ps, in, sc, os);
				break;
			// Fermeture du client
			case "quit" :
				onContinue = false;
				break;
			default:
				System.out.print("commande inconnu - commandes disponibles : (quit, login)\n");
			}
		}
		ps.println("bye");
		sc.close();
		os.close();
		client.close();
		System.exit(1);
	}
	
	public static boolean lecture(Socket client, BufferedReader in) throws IOException {
		while (true) {
			String msg = in.readLine();
			System.out.println(msg);
			if ((msg.charAt(0)) == '2') {
				return false; 
			}
			if ((msg.charAt(0)) == '0') {
				return true;
			}
		}
	}
	
	public static boolean lectureGet(Socket client, BufferedReader in) throws IOException {
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
	
	public static void envoieCommande(Socket client, PrintStream ps, Scanner sc, String commande, String directive) throws IOException {
		String cmd = commande + " ";
		if (sc != null || directive != null) { // commande avec argument
			System.out.print(directive);
			cmd += sc.nextLine(); // demande de l'argument
		}
		ps.println(cmd);
	}

}
