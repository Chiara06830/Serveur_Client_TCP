import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		// connexion
		Socket client = new Socket("localhost", 4501);
		
		// Initialisation des composantes pour la reception et l'envoie de messages
		BufferedReader in =
		        new BufferedReader(
		            new InputStreamReader(client.getInputStream()));
		OutputStream os = client.getOutputStream();
		PrintStream ps = new PrintStream(os);
		Scanner sc = new Scanner(System.in);
		
		// Reception du message de bienvenue du serveur
		lecture(client, in);
		
		// Identification : commande USER
		envoieCommande(client, ps, sc, "user", "Veuillez rentrez votre identifiant : ");
		lecture(client, in);
		
		// Identification : commande PASS
		envoieCommande(client, ps, sc, "pass", "Veuillez rentrez votre mot de passe : ");
		lecture(client, in);
		
		// Ou suis je ? : commande PWD
		envoieCommande(client, ps, null, "pwd", null);
		lecture(client, in);
		
		// Déconnexion du serveur
		ps.println("bye");
		
		sc.close();
		os.close();
		client.close();
	}
	
	public static void lecture(Socket client, BufferedReader in) throws IOException {
		boolean onContinueALire = true;
		while (onContinueALire) {
			String msg = in.readLine();
			System.out.println(msg);
			if ((msg.charAt(0)) == '2') {
				System.exit(-1); // Problème
			}
			if ((msg.charAt(0)) == '0') {
				onContinueALire = false;
			}
		}
	}
	
	public static void envoieCommande(Socket client, PrintStream ps, Scanner sc, String commande, String directive) throws IOException {
		String cmd = commande + " ";
		if (sc != null || directive != null) { // commande avec argument
			System.out.print(directive);
			cmd += sc.nextLine();
		}
		ps.println(cmd);
	}

}
