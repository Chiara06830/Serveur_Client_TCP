import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
		System.out.println("\nCommandes disponibles : (cd,get,ls,pwd,stor,bye,man) - utilisez man pour plus de précision sur une commande.");
	}
	
	public static void envoieCommande(Socket client, PrintStream ps, Scanner sc, String commande, String directive) throws IOException {
		String cmd = commande + " ";
		System.out.print(directive);
		cmd += sc.nextLine();
		ps.println(cmd);
	}
	
	public static void choisirCommande(Socket client, PrintStream ps, BufferedReader in, Scanner sc, OutputStream os) throws IOException {
		boolean onContinue = true;
		while (onContinue) {
			System.out.print(">> ");
			String cmd = sc.nextLine();
			switch (cmd.split(" ")[0]) {
			case "bye":
				if (cmd.split(" ").length == 1) {
					ps.println(cmd + " "); // appel la commande bye du serveur -> ce n'est pas la fin du serveur
					onContinue = false;
				} else {
					System.out.println("argument incorrect - bye ne prend aucun argument");
				}
				break;
			case "cd":
				// Pas encore fonctionnel
				if (cmd.split(" ").length == 2) {
					ps.println(cmd);
					if (!lecture(client, in)) {
						System.out.println("argument incorrect - Veuillez rentrez en argument le chemin (absolu ou relatif) du repertoire que vous souhaitez accéder");
					}
				} else {
					System.out.println("argument incorrect - cd prend un argument");
				}
				break;
			case "get":
				if (cmd.split(" ").length == 2) {
					ps.println(cmd);
					if (!lectureGet(client, in)) {
						System.out.println("argument incorrect - Veuillez rentrez en argument le nom (absolu ou relatif) du fichier à télécharger sur le serveur");
					}
				} else {
					System.out.println("argument incorrect - get prend un argument");
				}
				break;
			case "ls":
				if (cmd.split(" ").length == 1) {
					ps.println(cmd);
					lecture(client, in);
				} else {
					System.out.println("argument incorrect - ls ne prend aucun argument");
				}
				break;
			case "pwd":
				if (cmd.split(" ").length == 1) {
					ps.println(cmd);
					lecture(client, in);
				} else {
					System.out.println("argument incorrect - pwd ne prend aucun argument");
				}
				break;
			case "stor":
				if (cmd.split(" ").length == 2) {
					if (!executionEtLectureStor(client, in, ps, sc, cmd.split(" ")[1])) {
						System.out.print("argument incorrect - Veuillez rentrez en argument le nom (absolu ou relatif) du fichier à envoyé au serveur\n");
					}
				} else {
					System.out.println("argument incorrect - stor prend un argument");
				}
				break;
			case "man":
				if (cmd.split(" ").length == 2) {
					switch (cmd.split(" ")[1]) {
					case "bye":
						System.out.println("bye permet de ce déconnecter - aucun argument");
						break;
					case "cd":
						System.out.println("cd permet de ce déplacer sur le serveur - Veuillez rentrez en argument le chemin (absolu ou relatif) du repertoire que vous souhaitez accéder");
						break;
					case "get":
						System.out.println("get permet de télécharger un fichier sur le serveur - Veuillez rentrez en argument le nom (absolu ou relatif) du fichier à télécharger sur le serveur");
						break;
					case "ls":
						System.out.println("ls permet de voir tous les fichiers du répertoire courant sur le serveur - aucun argument");
						break;
					case "pwd":
						System.out.println("pwd renvoie votre position actuel sur le serveur - aucun argument");
						break;
					case "stor":
						System.out.println("stor permet d'envoyer sur le serveur un fichier (crée sur le répertoire courant) - Veuillez rentrez en argument le nom (absolu ou relatif) du fichier à envoyé au serveur");
						break;
					default:
						System.out.println("argument incorrect - arguments disponibles : (cd,get,ls,pwd,stor,bye)");
					}
				} else {
					System.out.println("argument incorrect - man prend un argument");
				}
				break;
			default:
				System.out.println("commande inconnu - commandes disponibles : (cd,get,ls,pwd,stor,bye,man) - utilisez man pour plus de précision sur une commande.");
			}
		}
		deconnexion(client, ps, in, sc, os);
	}
	
	public static void deconnexion(Socket client, PrintStream ps, BufferedReader in, Scanner sc, OutputStream os) throws IOException {
		// Fermeture du serveur
		lecture(client, in);
		// Demande de reconnexion
		System.out.println("Souhaitez vous fermé le client ? commandes disponibles : (quit,login,man)");
		while (true) {
			System.out.print(">> ");
			String cmd = sc.nextLine();
			switch (cmd.split(" ")[0]) {
			// Reconnexion
			case "login" :
				if (cmd.split(" ").length == 1) {
					connexion(client, ps, in, sc);
					choisirCommande(client, ps, in, sc, os);
				} else {
					System.out.println("argument incorrect - login ne prend aucun argument");
				}
				break;
			// Fermeture du client
			case "quit" :
				if (cmd.split(" ").length == 1) {
					ps.println("bye");
					sc.close();
					os.close();
					client.close();
					System.exit(1);
				} else {
					System.out.println("argument incorrect - quit ne prend aucun argument");
				}
			case "man" :
				if (cmd.split(" ").length == 2) {
					switch (cmd.split(" ")[1]) {
					case "quit":
						System.out.println("quit permet de fermer le client - aucun argument");
						break;
					case "login":
						System.out.println("login permet de se connecté sur le serveur - aucun argument");
						break;
					default:
						System.out.println("argument incorrect - arguments disponibles : (quit,login)");
					}
				} else {
					System.out.println("argument incorrect - man prend un argument");
				}
				break;
			default:
				System.out.println("commande inconnu - commandes disponibles : (quit,login,man) - utilisez man pour plus de précision sur une commande.");
			}
		}
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
	
	public static boolean executionEtLectureStor(Socket client, BufferedReader in, PrintStream ps, Scanner sc, String argument) throws IOException {
		File file = new File(argument).getAbsoluteFile();
		// C'est un fichier ? && Ce n'est pas un repertoire ?
		if (file.exists() && !file.isDirectory()) {
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
					psTransfert.println("0 " + "ccc.txt" + " : Transfert terminé.");
					
					inTransfert.close();
					psTransfert.close();
					transfertStor.close();
					return true; 
				}
			}
		} else {
			System.out.println("Le fichier est introuvable.");
			return false;
		}
	}

}
